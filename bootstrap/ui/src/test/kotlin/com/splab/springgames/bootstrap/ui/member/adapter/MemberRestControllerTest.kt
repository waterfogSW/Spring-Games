package com.splab.springgames.bootstrap.ui.member.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.splab.springgames.application.member.port.inbound.AddGameCardUseCase
import com.splab.springgames.application.member.port.inbound.DeleteGameCardUseCase
import com.splab.springgames.application.member.port.inbound.DeleteMemberUseCase
import com.splab.springgames.application.member.port.inbound.EditMemberUseCase
import com.splab.springgames.application.member.port.inbound.EnrollMemberUseCase
import com.splab.springgames.bootstrap.ui.common.exception.GlobalExceptionHandler
import com.splab.springgames.bootstrap.ui.member.dto.AddGameCardRequest
import com.splab.springgames.bootstrap.ui.member.dto.EditMemberRequest
import com.splab.springgames.bootstrap.ui.member.dto.EnrollMemberRequest
import com.splab.springgames.domain.member.exception.GameCardExceptionType
import com.splab.springgames.domain.member.exception.MemberExceptionType
import com.splab.springgames.support.common.uuid.UuidGenerator
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.just
import io.mockk.runs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("[Bootstrap][UI] 회원 API")
class MemberRestControllerTest(
    @InjectMockKs private val memberRestController: MemberRestController,
    @MockkBean private val enrollMemberUseCase: EnrollMemberUseCase,
    @MockkBean private val editMemberUseCase: EditMemberUseCase,
    @MockkBean private val deleteMemberUseCase: DeleteMemberUseCase,
    @MockkBean private val addGameCardUseCase: AddGameCardUseCase,
    @MockkBean private val deleteGameCardUseCase: DeleteGameCardUseCase,
    private val globalExceptionHandler: GlobalExceptionHandler,
    private val objectMapper: ObjectMapper,
) : DescribeSpec({

    lateinit var mockMvc: MockMvc


    beforeTest {
        mockMvc = MockMvcBuilders
            .standaloneSetup(memberRestController)
            .setControllerAdvice(globalExceptionHandler)
            .build()

        every { enrollMemberUseCase.invoke(any()) } just runs
        every { editMemberUseCase.invoke(any()) } just runs
        every { deleteMemberUseCase.invoke(any()) } just runs
        every { addGameCardUseCase.invoke(any()) } just runs
        every { deleteGameCardUseCase.invoke(any()) } just runs
    }

    afterTest {
        clearAllMocks()
    }

    describe("[POST] /api/members") {

        val path = "/api/members"

        context("[성공] 요청값이 올바른 경우") {
            it("CREATED 상태코드를 응답한다") {
                // arrange
                val request = EnrollMemberRequest(
                    name = "홍길동",
                    email = "test@naver.com",
                    registeredDate = LocalDate.now()
                )
                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result.andExpect(status().isCreated)
            }
        }

        context("[실패] name이 누락된 경우") {
            it("BAD_REQUEST, MEMBER-003 에러 코드를 응답한다") {
                // arrange
                val request = EnrollMemberRequest(
                    name = null,
                    email = "test@naver.com",
                    registeredDate = LocalDate.now()
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$.code").value("MEMBER-003"))
            }
        }

        context("[실패] email이 누락된 경우") {
            it("BAD_REQUEST, MEMBER-001 에러 코드를 응답한다") {
                // arrange
                val request = EnrollMemberRequest(
                    name = "홍길동",
                    email = null,
                    registeredDate = LocalDate.now()
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$.code").value(MemberExceptionType.INVALID_EMAIL_INPUT.code))
            }
        }

        context("[실패] registeredDate가 누락된 경우") {
            it("BAD_REQUEST, MEMBER-002 에러 코드를 응답한다") {
                // arrange
                val request = EnrollMemberRequest(
                    name = "홍길동",
                    email = "test@naver.com",
                    registeredDate = null
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$.code").value(MemberExceptionType.INVALID_REGISTER_DATE_INPUT.code))
            }
        }
    }

    describe("[PUT] /api/members/{memberId}") {
        val path = "/api/members/{memberId}"

        context("[성공] 요청값이 올바른 경우") {
            it("NO_CONTENT 상태코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()
                val request = EditMemberRequest(
                    name = "홍길동",
                    email = "test@naver.com",
                    registeredDate = LocalDate.now()
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    put(path, memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result.andExpect(status().isNoContent)
            }
        }

        context("[실패] name이 없는 경우") {
            it("BAD_REQUEST, MEMBER-003 에러 코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()
                val request = EditMemberRequest(
                    name = null,
                    email = "test@naver.com",
                    registeredDate = LocalDate.now(),
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    put(path, memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$.code").value(MemberExceptionType.INVALID_NAME_INPUT.code))
            }
        }

        context("[실패] email이 없는 경우") {
            it("BAD_REQUEST, MEMBER-001 에러 코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()
                val request = EditMemberRequest(
                    name = "홍길동",
                    email = null,
                    registeredDate = LocalDate.now(),
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    put(path, memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$.code").value(MemberExceptionType.INVALID_EMAIL_INPUT.code))
            }
        }

        context("[실패] registeredDate가 없는 경우") {
            it("BAD_REQUEST, MEMBER-002 에러 코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()
                val request = EditMemberRequest(
                    name = "홍길동",
                    email = "test@naver.com",
                    registeredDate = null,
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    put(path, memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$.code").value(MemberExceptionType.INVALID_REGISTER_DATE_INPUT.code))
            }
        }
    }

    describe("[DELETE] /api/members/{memberId}") {
        val path = "/api/members/{memberId}"

        context("[성공] 요청값이 올바른 경우") {
            it("NO_CONTENT 상태코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()

                // act
                val result: ResultActions = mockMvc.perform(
                    MockMvcRequestBuilders.delete(path, memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                )

                // assert
                result.andExpect(status().isNoContent)
            }
        }
    }

    describe("[POST] /api/members/{memberId}/game-cards") {
        val path = "/api/members/{memberId}/game-cards"

        context("[성공] 요청값이 올바른 경우") {
            it("CREATED 상태코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()
                val request = AddGameCardRequest(
                    gameId = UuidGenerator.create(),
                    title = "게임 타이틀",
                    serialNumber = 1234567890,
                    price = 10000.toBigDecimal()
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    post(path, memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result.andExpect(status().isCreated)
            }
        }

        context("[실패] gameId가 누락된 경우") {
            it("BAD_REQUEST, GAME-CARD-004 에러 코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()
                val request = AddGameCardRequest(
                    gameId = null,
                    title = "게임 타이틀",
                    serialNumber = 1234567890,
                    price = 10000.toBigDecimal()
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    post(path, memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$.code").value(GameCardExceptionType.INVALID_GAME_ID_INPUT.code))
            }
        }

        context("[실패] title이 누락된 경우") {
            it("BAD_REQUEST, GAME-CARD-001  에러 코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()
                val request = AddGameCardRequest(
                    gameId = UuidGenerator.create(),
                    title = null,
                    serialNumber = 1234567890,
                    price = 10000.toBigDecimal()
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    post(path, memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$.code").value(GameCardExceptionType.INVALID_TITLE_INPUT.code))
            }
        }

        context("[실패] serialNumber가 누락된 경우") {
            it("BAD_REQUEST, GAME-CARD-002 에러 코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()
                val request = AddGameCardRequest(
                    gameId = UuidGenerator.create(),
                    title = "게임 타이틀",
                    serialNumber = null,
                    price = 10000.toBigDecimal()
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    post(path, memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$.code").value(GameCardExceptionType.INVALID_SERIAL_NUMBER_INPUT.code))
            }
        }

        context("[실패] price가 누락된 경우") {
            it("BAD_REQUEST, GAME-CARD-003 에러 코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()
                val request = AddGameCardRequest(
                    gameId = UuidGenerator.create(),
                    title = "게임 타이틀",
                    serialNumber = 1234567890,
                    price = null
                )

                val requestBody: String = objectMapper.writeValueAsString(request)

                // act
                val result: ResultActions = mockMvc.perform(
                    post(path, memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )

                // assert
                result
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$.code").value(GameCardExceptionType.INVALID_PRICE_INPUT.code))
            }
        }
    }

    describe("[DELETE] /api/members/{memberId}/game-cards/{gameCardId}") {
        val path = "/api/members/{memberId}/game-cards/{gameCardId}"

        context("[성공] 요청값이 올바른 경우") {
            it("OK 상태코드를 응답한다") {
                // arrange
                val memberId: UUID = UuidGenerator.create()
                val gameCardId: UUID = UuidGenerator.create()

                // act
                val result: ResultActions = mockMvc.perform(
                    MockMvcRequestBuilders.delete(path, memberId, gameCardId)
                        .contentType(MediaType.APPLICATION_JSON)
                )

                // assert
                result.andExpect(status().isOk)
            }
        }
    }

})
