package com.splab.springgames.support.common.exception

enum class CommonExceptionType(override val code: String) : CustomExceptionType {
    INTERNAL_SERVER_ERROR("COMMON-001"),
}
