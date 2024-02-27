package com.splab.springgames.domain.member.vo

@JvmInline
value class GameCardTotalCount(val value: Int) {

    init {
        require(value >= 0) {
            "게임 카드 총 개수는 0 이상으로 입력해주세요."
        }
    }

}
