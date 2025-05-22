package self.study.sels.userapi.member.application.port.`in`

import self.study.sels.userapi.member.application.action.AuthenticationMemberRequestDto

interface AuthenticationUseCase {
    fun execute(command: AuthenticationMemberRequestDto): String
}
