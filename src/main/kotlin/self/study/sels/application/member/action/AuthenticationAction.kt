package self.study.sels.application.member.action

import self.study.sels.annotation.Action
import self.study.sels.application.member.port.`in`.AuthenticationUseCase
import self.study.sels.controller.dto.AuthenticationMemberRequestDto
import self.study.sels.model.member.MemberAuthenticationRedisRepository
import self.study.sels.model.member.MemberRepository
import self.study.sels.util.AuthCodeUtil

@Action
class AuthenticationAction(
    private val memberAuthenticationRedisRepository: MemberAuthenticationRedisRepository,
    private val memberRepository: MemberRepository,
) : AuthenticationUseCase {
    override fun execute(command: AuthenticationMemberRequestDto): String {
        if (memberRepository.existsByPhone(command.phone)) {
            throw Exception("이미 존재하는 회원 전화번호 입니다.")
        }

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
