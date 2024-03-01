package com.splab.springgames.bootstrap.ui.common.exception

import com.splab.springgames.support.common.exception.CommonExceptionType
import com.splab.springgames.support.common.exception.CustomException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCustomException(
        e: CustomException,
    ): ErrorResponse {
        return ErrorResponse(
            code = e.type.code,
            message = e.message,
        )
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMissingServletRequestParameterException(
        e: MissingServletRequestParameterException,
    ): ErrorResponse {
        return ErrorResponse(
            code = "MISSING_REQUEST_PARAMETER",
            message = e.message,
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(
        e: HttpMessageNotReadableException,
    ): ErrorResponse {
        return ErrorResponse(
            code = CommonExceptionType.INVALID_INPUT.code,
            message = "잘못된 입력값입니다.",
        )
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleRuntimeException(
        e: RuntimeException,
    ): ErrorResponse {
        return ErrorResponse(
            code = CommonExceptionType.INTERNAL_SERVER_ERROR.code,
            message = e.message ?: "알 수 없는 오류가 발생했습니다.",
        )
    }

}
