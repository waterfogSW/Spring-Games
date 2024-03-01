package com.splab.springgames.application.gameCard.service

import com.splab.springgames.application.gameCard.port.inbound.AddGameCardUseCase
import com.splab.springgames.application.gameCard.port.outbound.GameCardRepository
import com.splab.springgames.domain.gameCard.domain.GameCard
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddGameCardService(
    private val gameCardRepository: GameCardRepository,
) : AddGameCardUseCase {

    @Transactional
    override fun invoke(command: AddGameCardUseCase.Command) {
        GameCard.create(
            gameId = command.gameId,
            userId = command.memberId,
            title = command.title,
            serialNumber = command.serialNumber,
            price = command.price,
        ).also {
            // TODO: 카드 추가 이벤트 발행
            gameCardRepository.save(it)
        }
    }

}
