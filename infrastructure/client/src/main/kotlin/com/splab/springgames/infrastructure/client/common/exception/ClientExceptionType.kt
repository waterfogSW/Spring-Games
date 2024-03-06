package com.splab.springgames.infrastructure.client.common.exception

import com.splab.springgames.support.common.exception.CustomExceptionType

enum class ClientExceptionType(override val code: String): CustomExceptionType {
    CLIENT_ERROR("CLIENT-001"),
    SERVER_ERROR("CLIENT-002"),
    UNKNOWN_ERROR("CLIENT-003"),
}
