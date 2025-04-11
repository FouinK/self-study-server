package self.study.sels.application.member.action

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.repository.findByIdOrNull
import self.study.sels.IntegrationTest
import self.study.sels.application.member.port.`in`.JoinAndLoginUseCase
import self.study.sels.controller.dto.JoinMemberRequestDto
import self.study.sels.model.member.MemberAuthenticationRedisRepository
import self.study.sels.model.member.MemberRepository
import self.study.sels.util.AuthCodeUtil

internal class JoinAndLoginActionTest(
    private val sut: JoinAndLoginUseCase,
    private val memberAuthenticationRedisRepository: MemberAuthenticationRedisRepository,
    private val memberRepository: MemberRepository,
    private val stringRedisTemplate: StringRedisTemplate,
) : IntegrationTest() {
    val phone = "01011111111"
    lateinit var authenticationCode: String

    @BeforeEach
    fun setUp() {
        stringRedisTemplate.delete(listOf(phone))
        authenticationCode = AuthCodeUtil.generateAuthenticationCode()
        memberAuthenticationRedisRepository.setMemberAuthenticationCode(phone, authenticationCode)
    }

    @Test
    fun `정상 인증 되어 회원가입이 완료된다`() {
        //given
        val command = JoinMemberRequestDto(
            phone = phone,
            authenticationCode = authenticationCode,
        )

        //when
        val result = sut.execute(command)

        //then
        assertThat(result.memberId).isNotNull()
        assertThat(result.authToken).isNotNull()

        val member = memberRepository.findByIdOrNull(result.memberId)!!

        assertThat(memberAuthenticationRedisRepository.getMemberAuthenticationCode(command.phone)).isNull()
        assertThat(member.authToken).isEqualTo(result.authToken)
        assertThat(member.marketingYn).isFalse()
        assertThat(member.pushYn).isFalse()
        assertThat(member.phone).isEqualTo(command.phone)
    }

    @Test
    fun `인증번호가 일치하지 않을경우 예외가 발생한다`() {
        //given
        authenticationCode.toInt() + 1
        val command = JoinMemberRequestDto(
            phone = phone,
            authenticationCode = (authenticationCode.toInt() + 1).toString(),
        )

        //when & then
        assertThrows<Exception> {
            sut.execute(command)
        }
    }

    @Test
    fun `인증번호 먼저 발급받지 않고 회원가입 진행시 예외가 발생한다`() {
        //given
        val command = JoinMemberRequestDto(
            phone = (phone.toInt() + 1).toString(),
            authenticationCode = authenticationCode,
        )

        //when & then
        assertThrows<Exception> {
            sut.execute(command)
        }
    }
}
