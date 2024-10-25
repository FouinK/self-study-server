package self.study.sels.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.member.port.`in`.AuthenticationUseCase
import self.study.sels.application.member.port.`in`.JoinMemberResponseDto
import self.study.sels.application.member.port.`in`.JoinUseCase
import self.study.sels.controller.dto.AuthenticationMemberRequestDto
import self.study.sels.controller.dto.JoinMemberRequestDto

@RestController
@RequestMapping("/sels/api/u/member")
class MemberController(
    private val authenticationUseCase: AuthenticationUseCase,
    private val joinUseCase: JoinUseCase,
) {
    @PostMapping("/authentication")
    fun authenticate(
        @Valid @RequestBody request: AuthenticationMemberRequestDto
    ): ResponseEntity<Any> {
        authenticationUseCase.execute(command = request)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PostMapping
    fun join(
        @Valid @RequestBody request: JoinMemberRequestDto
    ): ResponseEntity<Any> {
        val result = joinUseCase.execute(command = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            JoinMemberResponseDto(
                memberId = result.memberId,
                authToken = result.authToken,
            ),
        )
    }
}
