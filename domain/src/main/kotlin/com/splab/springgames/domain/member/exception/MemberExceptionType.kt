package com.splab.springgames.domain.member.exception

import com.splab.springgames.support.common.exception.CustomExceptionType

enum class MemberExceptionType(override val code: String) : CustomExceptionType {
    INVALID_EMAIL_INPUT("MEMBER-001"),
    INVALID_REGISTER_DATE_INPUT("MEMBER-002"),
    INVALID_NAME_INPUT("MEMBER-003"),
    DUPLICATED_EMAIL_INPUT("MEMBER-004"),
    INVALID_ID_INPUT("MEMBER-005"),
}
