package self.study.sels.application.member.port.`in`

import self.study.sels.controller.dto.AuthenticationMemberRequestDto

interface AuthenticationUseCase {
    fun execute(command: AuthenticationMemberRequestDto): String
}
