package self.study.sels.application.member.port.`in`

import self.study.sels.controller.dto.JoinMemberRequestDto

interface JoinAndLoginUseCase {
    fun execute(
        command: JoinMemberRequestDto
    ): JoinMemberResponseDto
}

data class JoinMemberResponseDto(
    val memberId: Int,
    val authToken: String,
)
