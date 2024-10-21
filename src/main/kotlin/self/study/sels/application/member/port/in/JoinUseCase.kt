package self.study.sels.application.member.port.`in`

import self.study.sels.controller.dto.JoinMemberRequestDto

interface JoinUseCase {
    fun execute(
        command: JoinMemberRequestDto
    ): JoinMemberResponseDto
}

data class JoinMemberResponseDto(
    val memberId: Int,
    val authToken: String,
)
