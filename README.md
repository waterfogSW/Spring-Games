# Spring-Games

## 개요

게임센터 회원관리 시스템 개발

## 사용 기술

**Production**
- Kotlin 1.9.22
- JDK 21
- Spring Boot 3.2.3
- Spring Data JPA
- Thymeleaf
- OpenFeign
- Flyway
- MySQL 8.0.33

**Test**
- TestContainers
- WireMock
- H2
- Kotest

## 실행

### 프로젝트 실행

```bash
./gradlew :bootstrap:ui:bootRun
```

- Java 21
- Docker
- 3306 포트가 사용중이지 않아야 합니다.

### 테스트 실행

```bash
./gradlew test
```

**Required**

- Java 21
- Docker
- 3306 포트가 사용중이지 않아야 합니다.

## 설계

### 모듈 구조

본 프로젝트는 헥사고날 아키텍처를 기반으로한 모듈구조를 사용하고 있습니다.

**Bootstrap**

- 역할
    - 전체 애플리케이션 구성 모듈 조합, 각 모듈 Configuration Import
    - Primary Adapter 구현, 외부 요청 처리 및 Application 모듈 UseCase 호출
- 테스트
    - WebMvcTest기반 컨트롤러 + 예외처리 핸들러 테스트
- 의존성
    - Infrastructure, Application, Domain

**Infrastructure**

- 역할
    - 데이터베이스, 외부 API, 외부 시스템과의 연동을 담당
    - Secondary Adapter 구현, Application 모듈의 Outbound Port 구현체
    - (persistence) JPA, Spring Data JPA를 사용한 데이터베이스 연동
    - (client) OpenFeign을 사용한 외부 API 연동
- 테스트
    - (persistence) TestContianers를 사용한 데이터 베이스 통합 테스트
    - (client) WireMock을 사용한 외부 API 통합 테스트
- 의존성
    - Application, Domain

**Application**

- 역할
    - 비즈니스 로직을 담당
    - Inbound Port, Outbound Port 정의
    - UseCase 구현
- 테스트
    - Port의 경우 별도의 Spy객체를 구현해 테스트
    - 타 도메인 UseCase 호출시 Mocking을 통한 테스트
    - Stub 객체는 실제 인터페이스와 동일한 패키지 경로의 test-fixture 모듈에 구현
- 의존성
    - Domain

**Domain**

- 역할
    - 비즈니스 로직을 담당
    - Entity, Value Object, Aggregate, Repository 정의
    - 도메인 이벤트 정의
- 테스트
    - Entity, Value Object에 대한 단위 테스트 작성

### 데이터 베이스 설계

<img width="400" alt="image" src="https://github.com/Student-Center/weave-server/assets/28651727/e5d5cea8-4ced-459c-a672-6b9a8c4180bd">

**인덱스 설계**

- member
    - name : fulltext ngram index
        - 이름 기반 조회시 사용
        - 1글자 이상 full text index 기반 검색이 가능하도록 ngram token size를 1로 설정
    - level : index
        - 레벨 기반 조회시 사용
    - email : unique index
        - 중복 이메일을 방지하기 위해 유니크 인덱스 설정
- game_card
    - (game_id, serial_number) : unique index
        - 게임 카드의 게임내 고유성을 보장하기 위해 유니크 인덱스 설정
    - member_id : index
        - 회원별 게임 카드 조회시 사용

## 고민한 점

### 1. 회원 가입 및 레벨 변경시 Slack 알림을 보내는 방법

**배경**
회원 도메인 객체가 생성되고, 업데이트 될때 Slack알림을 보낼지, 해당 로직의 트랜잭션이 커밋 될때 Slack 알림을 보낼지 고민했었습니다.

초기에는 회원의 레벨 변경 여부를 확인하고, 알림을 보낼지말지 결정하는 로직을 도메인 객체의 메서드에 다음과 같이 람다식으로 호출을 위임했었습니다.

```kotlin
    fun updateLevelWith(
    memberGameCards: List<GameCard>,
    notifyAction: ((Member) -> Unit)? = null,
): Member {
    val validGameCards: List<GameCard> = memberGameCards.filter { it.isValidCard() }
    val updatedMember: Member = this.copy(level = determineMemberLevel(validGameCards))

    if (this.level != updatedMember.level) {
        notifyAction?.invoke(updatedMember)
    }
    return updatedMember
}
```

```kotlin
companion object {
    fun create(
        name: String,
        email: String,
        registeredDate: LocalDate,
        notifyAction: ((Member) -> Unit)? = null,
    ): Member {
        return Member(
            name = Name.create(name),
            email = Email.create(email),
            registeredDate = RegisteredDate.create(registeredDate),
            level = Level.BRONZE,
            gameCardTotalCount = GameCardTotalCount(0),
            gameCardTotalPrice = GameCardTotalPrice(0.toBigDecimal())
        ).also { notifyAction?.invoke(it) }
    }
}
```

영속화는 Service에서 진행하고, 알림 메서드 호출은 영속화 이전에 도메인이 생성되거나 레벨 업데이트시 호출되었습니다.

**문제점**

이후 해당 도메인의 영속화과정에서 예외가 발생해 영속화 되지 않을경우 알림은 보내졌으나, 영속화는 되지 않아 알림은 받았는데, 반영되지 않는 상황이 발생했습니다.

**해결**

알림의 경우 Service에서 직접 호출하도록 변경했습니다. 알림 호출 여부를 도메인이 아닌 Service에서 결정해야 한다는 단점은 있지만, 데이터의 영속화가 성공적으로 이루어진
후에만 알림이 발송되도록 할 수 있어, 데이터와 알림의 일관성을 보장할 수 있었습니다.

### 2. 회원 등록, 게임카드 추가 중복 요청 처리 방법

**문제점**

회원 등록 요청이 중복으로 요청되거나, 게임 카드 요청이 중복으로 요청 되는 경우 처리하는 방법을 고민했습니다.

**해결방법**

1. Unique Key + SaveAndFlush

Database의 제약조건으로 회원의 경우 email unique키가 걸려있고, 게임 카드의 경우 (game_id, serial_number) unique키가 걸려있어 중복 요청에
대해 DataIntegrityViolationException이 발생합니다.

DataIntegrityViolationException은 flush되는 시점에 발생하기 때문에 flsuh 메서드를 호출하지 않으면 Transaction밖에서 예외를 컨트롤 해야
했습니다.

이는 Controller 또는 ControllerAdvice에서 예외를 처리해야한다는것을 의미하는데, 중복 검증이라는 비즈니스 로직이 Service에서 이루어지지 않고,
Controller에서 이루어지게 된다는 점에서 설계원칙에 어긋난다고 판단했습니다.

이러한 문제점을 해결하기 위해 repository의 save 메서드의 구현을 JpaRepository의 saveAndFlush로 변경해 쓰기지연을 사용하지 않고 즉시 DB에 반영해
제약 조건 위배를 Controller 이전에 처리할 수 있도록 할 수 있습니다.

2. MySQL Named Lock

MySQL의 Named Lock을 사용해 중복 요청을 방지할 수도 있습니다. Named Lock은 MySQL의 트랜잭션 내에서만 유효한 락으로, 동일한 이름의 락을 획득한
트랜잭션이 완료될때까지 다른 트랜잭션이 해당 락을 획득하지 못하도록 합니다.

분산락 구현을 위해 Redis등 외부 인프라를 사용할 수도있지만, 별도의 인프라 구축비용이 발생한다는 점과 이미 MySQL을 사용하고 있어 Named Lock을 사용하는 것이
좋을것 같다고 판단했습니다.

**결론**

Named Lock의 경우 별도의 락을 구현하는 비용이 들고, 트랜잭션의 범위 만큼 락을 잡아야 해서 불필요한 성능저하가 우려됩니다. Named Lock을 사용하지 않고,
Unique Key + SaveAndFlush를 사용해 중복 요청을 방지했습니다.

### 기술 도입 관련

1. TestContainers
    - 데이터베이스 테스트시 h2에서는 full text index기반 조회를 지원하지 않아 TestContainers를 사용해 MySQL을 직접 실행해 테스트 했습니다.
2. WireMock
    - 외부 API 호출을 위한 테스트를 위해서 직접 외부 API를 호출하지 않고, WireMock을 사용해 외부 API의 응답을 Mocking해 테스트 했습니다.
3. UUID v7
     - DB의 채번을 줄이고, 시간 기반으로 생성되어 정렬이 가능하다는 장점이 있어 UUID v7를 사용했습니다.
4. java-test-fixtures
    - 테스트에서 사용하는 Stub 객체를 별도의 패키지에 구현해 테스트해 java-test-fixtures 플러그인을 활용했습니다.


### 예외 처리 관련

- 예외의 경우 별도의 CustomException을 정의하고, 속성으로 CustomExceptionType을 가지도록 했습니다. CustomExceptionType은 도메인 별로 정의되어 code를 통해 뷰에서 예외를 구분하여 처리할 수 있도록 구현하였습니다.

```kotlin
class CustomException(
    val type: CustomExceptionType,
    override val message: String
) : RuntimeException(message)


interface CustomExceptionType {
    val code: String
}

enum class MemberExceptionType(override val code: String) : CustomExceptionType {
    INVALID_EMAIL_INPUT("MEMBER-001"),
    INVALID_REGISTER_DATE_INPUT("MEMBER-002"),
    INVALID_NAME_INPUT("MEMBER-003"),
    DUPLICATED_EMAIL_INPUT("MEMBER-004"),
    INVALID_ID_INPUT("MEMBER-005"),
    MEMBER_NOT_FOUND("MEMBER-006"),
    DUPLICATED_ENTITY("MEMBER-007"),
}

enum class GameCardExceptionType(override val code: String) : CustomExceptionType {
    INVALID_TITLE_INPUT("GAME-CARD-001"),
    INVALID_SERIAL_NUMBER_INPUT("GAME-CARD-002"),
    INVALID_PRICE_INPUT("GAME-CARD-003"),
    INVALID_GAME_ID_INPUT("GAME-CARD-004"),
    DUPLICATED_ENTITY("GAME-CARD-005"),
}

```


### 
