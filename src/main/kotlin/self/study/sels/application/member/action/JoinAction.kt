package self.study.sels.application.member.action

import org.springframework.transaction.annotation.Transactional
import self.study.sels.annotation.Action
import self.study.sels.application.member.port.`in`.JoinMemberResponseDto
import self.study.sels.application.member.port.`in`.JoinUseCase
import self.study.sels.controller.dto.JoinMemberRequestDto
import self.study.sels.exception.NotFoundException
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberAuthenticationRedisRepository
import self.study.sels.model.member.MemberRepository
import java.util.*

@Action
class JoinAction(
    private val memberAuthenticationRedisRepository: MemberAuthenticationRedisRepository,
    private val memberRepository: MemberRepository,
) : JoinUseCase {
    @Transactional
    override fun execute(
        command: JoinMemberRequestDto
    ): JoinMemberResponseDto {
        val memberAuthenticationCode = memberAuthenticationRedisRepository.getMemberAuthenticationCode(command.phone)
            ?: throw NotFoundException("인증 번호 요청을 먼저 진행해주세요.")

        if (memberAuthenticationCode != command.authenticationCode) {
            throw Exception("인증번호가 일치하지 않습니다.")
        }

        val member = memberRepository.save(
            Member(
                authToken = UUID.randomUUID().toString(),
                pushYn = false,
                marketingYn = false,
                phone = command.phone,
            ),
        )

        memberAuthenticationRedisRepository.deleteMemberAuthenticationCode(command.phone)

        return JoinMemberResponseDto(
            memberId = member.id,
            authToken = member.authToken!!,
        )
    }
}
