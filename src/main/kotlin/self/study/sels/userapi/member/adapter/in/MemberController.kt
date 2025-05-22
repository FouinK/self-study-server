package self.study.sels.userapi.member.adapter.`in`

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import self.study.sels.userapi.member.application.action.AuthenticationMemberRequestDto
import self.study.sels.userapi.member.application.action.JoinMemberRequestDto
import self.study.sels.userapi.member.application.port.`in`.AuthenticationUseCase
import self.study.sels.userapi.member.application.port.`in`.JoinAndLoginUseCase
import self.study.sels.userapi.member.application.port.`in`.JoinMemberResponseDto

@RestController
@RequestMapping("/sels/api/u/member")
class MemberController(
    private val authenticationUseCase: AuthenticationUseCase,
    private val joinAndLoginUseCase: JoinAndLoginUseCase,
) {
    @PostMapping("/authentication")
    fun authenticate(
        @Valid @RequestBody request: AuthenticationMemberRequestDto
    ): ResponseEntity<Any> {
        authenticationUseCase.execute(command = request)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PostMapping
    fun joinAndLogin(
        @Valid @RequestBody request: JoinMemberRequestDto
    ): ResponseEntity<Any> {
        val result = joinAndLoginUseCase.execute(command = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            JoinMemberResponseDto(
                memberId = result.memberId,
                authToken = result.authToken,
            ),
        )
    }
}
