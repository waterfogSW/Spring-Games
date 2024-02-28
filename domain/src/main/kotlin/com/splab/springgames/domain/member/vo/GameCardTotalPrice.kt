package com.splab.springgames.domain.member.vo

import java.math.BigDecimal

@JvmInline
value class GameCardTotalPrice(val value: BigDecimal) {

    init {
        require(value >= BigDecimal.ZERO){
            "게임 카드 총 가격은 0 이상으로 입력해주세요."
        }
    }

}
