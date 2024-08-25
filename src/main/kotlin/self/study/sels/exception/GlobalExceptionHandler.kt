package self.study.sels.exception

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import self.study.sels.feignclient.ErrorMsg
import self.study.sels.service.WatchTowerService
import self.study.sels.util.logger
import java.net.InetAddress
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class GlobalErrorResponseDto(
    val code: Int,
    val msg: String? = null,
    val data: Any? = null,
)

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class GlobalExceptionHandler(
    private val watchTowerService: WatchTowerService,
) {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(
        request: HttpServletRequest,
        ex: Exception,
    ): GlobalErrorResponseDto {
        val currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val msg =
            """
            ```diff
            [sels server error]
            [$currentDateTime]
            [${InetAddress.getLocalHost().hostName}]
            - [${request.method}] ${request.requestURI}
            - [msg] $ex
            - [trace-0] ${ex.stackTrace[0]}
            - [trace-1] ${ex.stackTrace[1]}
            ```
            """.trimIndent()
        val errorMsg =
            ErrorMsg(
                content = msg,
            )
        watchTowerService.sendErrorNotification(errorMsg)
        return GlobalErrorResponseDto(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            msg = ex.message,
        )
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ExistsNameException::class)
    fun handleExistsNameException(ex: ExistsNameException): GlobalErrorResponseDto =
        GlobalErrorResponseDto(
            code = HttpStatus.CONFLICT.value(),
            msg = ex.message,
        )

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun notFoundHandler(ex: NotFoundException): GlobalErrorResponseDto =
        GlobalErrorResponseDto(
            code = HttpStatus.NOT_FOUND.value(),
            msg = ex.message,
        )

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableException(ex: Exception): Error? {
        when (ex.cause) {
            is MissingKotlinParameterException -> return createMissingKotlinParameterViolation(ex.cause as MissingKotlinParameterException)
        }
        throw ex
    }

    private fun createMissingKotlinParameterViolation(ex: MissingKotlinParameterException): Error {
        val error = Error(HttpStatus.BAD_REQUEST.value(), "validation error")
        val errorFieldRegex = Regex("\\.([^.]*)\\[\\\"(.*)\"\\]\$")
        val errorMatch = errorFieldRegex.find(ex.path[0].description)!!
        val (objectName, field) = errorMatch.destructured

        error.addFieldError(objectName = objectName, field = field, message = "필드가 누락되었습니다.")
        logger().error("BAD_REQUEST", ex)
        return error
    }

    data class Error(
        val status: Int,
        var message: String,
        val fieldErrors: MutableList<CustomFieldError> = mutableListOf(),
    ) {
        fun addFieldError(
            objectName: String,
            field: String,
            message: String,
        ) {
            val error = CustomFieldError(objectName, field, message)
            fieldErrors.add(error)
        }
    }

    data class CustomFieldError(
        val objectName: String,
        val field: String,
        val message: String,
    )
}
