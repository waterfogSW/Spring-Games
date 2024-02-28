package com.splab.springgames.domain.member.exception

import com.splab.springgames.support.common.exception.CustomExceptionType

enum class MemberExceptionType(override val code: String) : CustomExceptionType {
    ENROLLMENT_INVALID_EMAIL_INPUT("MEMBER-001"),
    ENROLLMENT_INVALID_REGISTER_DATE_INPUT("MEMBER-002"),
    ENROLLMENT_INVALID_NAME_INPUT("MEMBER-003"),
    ENROLLMENT_DUPLICATED_EMAIL_INPUT("MEMBER-004"),
}
