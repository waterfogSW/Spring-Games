package com.splab.springgames.domain.game.vo

@JvmInline
value class GameName(val value: String) {

    init {
        require(value.length in 1..100) {
            "게임 이름은 1자 이상 100자 이하로 입력해 주세요."
        }
    }

}
