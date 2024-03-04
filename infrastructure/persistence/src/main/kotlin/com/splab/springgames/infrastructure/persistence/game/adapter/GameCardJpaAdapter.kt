package com.splab.springgames.infrastructure.persistence.game.adapter

import com.splab.springgames.application.member.port.outbound.GameCardRepository
import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.domain.member.exception.GameCardExceptionType
import com.splab.springgames.domain.member.vo.GameCardSerialNumber
import com.splab.springgames.infrastructure.persistence.game.entity.GameCardJpaEntity.Companion.toJpaEntity
import com.splab.springgames.infrastructure.persistence.game.repository.GameCardJpaRepository
import com.splab.springgames.support.common.exception.CustomException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Component
class GameCardJpaAdapter(
    private val gameCardJpaRepository: GameCardJpaRepository,
) : GameCardRepository {

    override fun save(gameCard: GameCard) {
        runCatching {
            gameCard
                .toJpaEntity()
                .also { gameCardJpaRepository.saveAndFlush(it) }
        }.onFailure {
            if (it is DataIntegrityViolationException) {
                throw CustomException(
                    type = GameCardExceptionType.DUPLICATED_ENTITY,
                    message = "이미 존재하는 게임 카드입니다."
                )
            } else {
                throw it
            }
        }
    }

    override fun deleteById(gameCardId: UUID) {
        gameCardJpaRepository.deleteById(gameCardId)
    }

    @Transactional
    override fun deleteAllByMemberId(memberId: UUID) {
        gameCardJpaRepository.deleteAllByMemberId(memberId)
    }

    override fun findAllByMemberId(memberId: UUID): List<GameCard> {
        return gameCardJpaRepository
            .findAllByMemberId(memberId)
            .map { it.toDomain() }
    }

    override fun existsByGameIdAndSerialNumber(
        gameId: UUID,
        serialNumber: GameCardSerialNumber
    ): Boolean {
        return gameCardJpaRepository.existsByGameIdAndSerialNumber(
            gameId = gameId,
            serialNumber = serialNumber.value
        )
    }

    override fun findById(gameCardId: UUID): GameCard? {
        return gameCardJpaRepository
            .findById(gameCardId)
            .map { it.toDomain() }
            .getOrNull()
    }

}
