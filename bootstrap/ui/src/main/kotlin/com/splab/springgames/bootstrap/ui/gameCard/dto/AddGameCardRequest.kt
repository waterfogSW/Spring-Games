package com.splab.springgames.bootstrap.ui.gameCard.dto

import com.splab.springgames.application.gameCard.port.inbound.AddGameCardUseCase
import com.splab.springgames.domain.gameCard.exception.GameCardExceptionType
import com.splab.springgames.support.common.exception.CustomException
import java.math.BigDecimal
import java.util.*


data class AddGameCardRequest(
    val gameId: UUID?,
    val title: String?,
    val serialNumber: Long?,
    val price: BigDecimal?,
) {

    fun toCommandWith(memberId: UUID): AddGameCardUseCase.Command {
        return AddGameCardUseCase.Command(
            memberId = memberId,
            gameId = getGameIdOrThrow(gameId),
            title = getTitleOrThrow(title),
            serialNumber = getSerialNumberOrThrow(serialNumber),
            price = getPriceOrThrow(price)
        )
    }

    private fun getGameIdOrThrow(gameId: UUID?): UUID {
        if (gameId == null) {
            throw CustomException(
                type = GameCardExceptionType.INVALID_GAME_ID_INPUT,
                message = "게임 ID는 필수 입력값입니다."
            )
        }
        return gameId
    }

    private fun getTitleOrThrow(title: String?): String {
        if (title.isNullOrBlank()) {
            throw CustomException(
                type = GameCardExceptionType.INVALID_TITLE_INPUT,
                message = "카드 타이틀은 필수 입력값입니다."
            )
        }
        return title
    }

    private fun getSerialNumberOrThrow(serialNumber: Long?): Long {
        if (serialNumber == null) {
            throw CustomException(
                type = GameCardExceptionType.INVALID_SERIAL_NUMBER_INPUT,
                message = "게임 시리얼 번호는 필수 입력값입니다."
            )
        }
        return serialNumber
    }

    private fun getPriceOrThrow(price: BigDecimal?): BigDecimal {
        if (price == null) {
            throw CustomException(
                type = GameCardExceptionType.INVALID_PRICE_INPUT,
                message = "게임 가격은 필수 입력값입니다."
            )
        }
        return price
    }

}
