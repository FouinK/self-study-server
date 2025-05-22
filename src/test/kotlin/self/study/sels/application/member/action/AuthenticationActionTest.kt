package self.study.sels.application.member.action

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.redis.core.StringRedisTemplate
import self.study.sels.IntegrationTest
import self.study.sels.userapi.member.application.action.AuthenticationMemberRequestDto
import self.study.sels.userapi.member.application.port.`in`.AuthenticationUseCase

internal class AuthenticationActionTest(
    private val sut: AuthenticationUseCase,
    private val stringRedisTemplate: StringRedisTemplate,
) : IntegrationTest() {
    var phone = "01012341234"

    @BeforeEach
    fun setUp() {
        stringRedisTemplate.delete(listOf(phone, "${phone}_count"))
    }

    @Test
    fun `인증번호가 정상 생성된다`() {
        //given
        val command = AuthenticationMemberRequestDto(
            phone = phone,
        )

        //when
        val result = sut.execute(command)

        //then
        assertThat(result.length).isEqualTo(4)
    }

    @Test
    fun `6회 이상 요청했을 경우 예외가 발생한다`() {
        //given
        val command = AuthenticationMemberRequestDto(
            phone = phone,
        )

        //when
        (1..5).forEach {
            sut.execute(command)
        }

        //then
        assertThrows<Exception> {
            sut.execute(command)
        }
    }

    @Test
    fun `5회 인증까지는 괜찮다`() {
        //given
        val command = AuthenticationMemberRequestDto(
            phone = phone,
        )

        //when
        (1..4).forEach {
            sut.execute(command)
        }

        commonThen(sut.execute(command))
    }

    @Test
    fun `24시간 지났을때는 다시 요청 가능하다`() {
        //given

        //when

        //then
    }

    private fun commonThen(result: String) {
        assertThat(result.length).isEqualTo(4)
        assertThatCode {
            result.toInt()
        }.doesNotThrowAnyException()
    }
}
