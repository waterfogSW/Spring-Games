package com.splab.springgames.infrastructure.client.common.config.slack

import com.splab.springgames.infrastructure.client.common.exception.ClientExceptionType
import com.splab.springgames.support.common.exception.CustomException
import feign.Response
import feign.RetryableException
import feign.codec.ErrorDecoder
import io.github.oshai.kotlinlogging.KotlinLogging


class SlackErrorDecoder : ErrorDecoder {

    private val logger = KotlinLogging.logger { this::class.java }

    override fun decode(
        methodKey: String,
        response: Response
    ): Exception {
        val code: Int = response.status()

        return when (code) {
            400, 403, 404, 410 -> handleClientError(response)
            500 -> handleServerError(response)
            else -> handleUnknownError(response)
        }
    }

    private fun handleClientError(response: Response): CustomException {
        logger.error { "Slack Request Client Error - ${response.reason()}" }

        return CustomException(
            type = ClientExceptionType.CLIENT_ERROR,
            message = "Slack Request Client Error - ${response.reason()}"
        )
    }

    private fun handleServerError(response: Response): RetryableException {
        logger.error { "Slack Request Server Error - ${response.reason()}" }

        val exception = CustomException(
            type = ClientExceptionType.SERVER_ERROR,
            message = "Slack Request Server Error - ${response.reason()}"
        )

        return RetryableException(
            response.status(),
            response.reason(),
            response.request().httpMethod(),
            exception,
            1,
            response.request()
        )
    }

    private fun handleUnknownError(response: Response): CustomException {
        logger.error { "Slack Request Unknown Error - ${response.reason()}" }
        return CustomException(
            type = ClientExceptionType.UNKNOWN_ERROR,
            message = "Slack Request Unknown Error - ${response.reason()}"
        )
    }


}
