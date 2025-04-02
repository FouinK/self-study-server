package self.study.sels.application.member.action

import self.study.sels.annotation.Action
import self.study.sels.application.member.port.`in`.AuthenticationUseCase
import self.study.sels.controller.dto.AuthenticationMemberRequestDto
import self.study.sels.model.member.MemberAuthenticationRedisRepository
import self.study.sels.util.AuthCodeUtil

@Action
class AuthenticationAction(
    private val memberAuthenticationRedisRepository: MemberAuthenticationRedisRepository,
) : AuthenticationUseCase {
    override fun execute(command: AuthenticationMemberRequestDto): String {
        val incrementMemberAuthenticationRequestCount =
            memberAuthenticationRedisRepository.incrementMemberAuthenticationRequestCount(command.phone)

        if (incrementMemberAuthenticationRequestCount > 5) {
            throw Exception("24시간 후에 다시 시도해주세요.")
        }

        val authenticationCode = AuthCodeUtil.generateAuthenticationCode()

        memberAuthenticationRedisRepository.setMemberAuthenticationCode(
            phone = command.phone,
            authenticationCode = authenticationCode,
        )

        // TODO : sms 전송

        return authenticationCode
    }
}
