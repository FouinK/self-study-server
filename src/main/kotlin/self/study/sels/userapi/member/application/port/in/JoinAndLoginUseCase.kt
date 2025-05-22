package self.study.sels.userapi.member.application.port.`in`

import self.study.sels.userapi.member.application.action.JoinMemberRequestDto

interface JoinAndLoginUseCase {
    fun execute(
        command: JoinMemberRequestDto
    ): JoinMemberResponseDto
}

data class JoinMemberResponseDto(
    val memberId: Int,
    val authToken: String,
)
