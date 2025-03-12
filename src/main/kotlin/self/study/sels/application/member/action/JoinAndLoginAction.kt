package self.study.sels.application.member.action

import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.transaction.annotation.Transactional
import self.study.sels.annotation.Action
import self.study.sels.application.member.port.`in`.JoinAndLoginUseCase
import self.study.sels.application.member.port.`in`.JoinMemberResponseDto
import self.study.sels.controller.dto.JoinMemberRequestDto
import self.study.sels.exception.NotFoundException
import self.study.sels.model.member.MemberAuthenticationRedisRepository
import self.study.sels.model.member.MemberFactory
import self.study.sels.model.member.MemberRepository
import self.study.sels.util.AuthCodeUtil

@Action
class JoinAndLoginAction(
    private val environment: Environment,
    private val memberAuthenticationRedisRepository: MemberAuthenticationRedisRepository,
    private val memberFactory: MemberFactory,
    private val memberRepository: MemberRepository,
) : JoinAndLoginUseCase {
    @Transactional
    override fun execute(
        command: JoinMemberRequestDto
    ): JoinMemberResponseDto {
        if (environment.acceptsProfiles(Profiles.of("production")) || environment.acceptsProfiles(Profiles.of("test"))) {
            val memberAuthenticationCode = memberAuthenticationRedisRepository.getMemberAuthenticationCode(command.phone)
                ?: throw NotFoundException("인증 번호 요청을 먼저 진행해주세요.")
            if (memberAuthenticationCode != command.authenticationCode) {
                throw Exception("인증번호가 일치하지 않습니다.")
            }
        }

        var member = memberRepository.findByPhone(command.phone)

        member = if (member != null) {
            member.refreshToken()
            member
        } else {
            memberRepository.save(
                memberFactory.create(
                    MemberFactory.Command(
                        authToken = AuthCodeUtil.generateAuthToken(),
                        pushYn = false,
                        marketingYn = false,
                        phone = command.phone,
                    ),
                ),
            )
        }

        memberAuthenticationRedisRepository.deleteMemberAuthenticationCode(command.phone)

        return JoinMemberResponseDto(
            memberId = member.id,
            authToken = member.authToken!!,
        )
    }
}
