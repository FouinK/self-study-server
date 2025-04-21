package self.study.sels.application.member.action

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.Pageable
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.repository.findByIdOrNull
import self.study.sels.IntegrationTest
import self.study.sels.application.member.port.`in`.JoinAndLoginUseCase
import self.study.sels.controller.dto.JoinMemberRequestDto
import self.study.sels.fixture.MemberBuilder
import self.study.sels.model.answer.AnswerRepository
import self.study.sels.model.book.BookRepository
import self.study.sels.model.bookcase.BookcaseRepository
import self.study.sels.model.member.MemberAuthenticationRedisRepository
import self.study.sels.model.member.MemberRepository
import self.study.sels.model.question.QuestionRepository
import self.study.sels.service.BookPOJO
import self.study.sels.service.BookcasePOJO
import self.study.sels.util.AuthCodeUtil

internal class JoinAndLoginActionTest(
    private val sut: JoinAndLoginUseCase,
    private val memberAuthenticationRedisRepository: MemberAuthenticationRedisRepository,
    private val memberRepository: MemberRepository,
    private val stringRedisTemplate: StringRedisTemplate,
    private val bookcaseRepository: BookcaseRepository,
    private val bookRepository: BookRepository,
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
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

    @Test
    fun `회원가입 시 책장, 책, 질문, 답변이 모두 생성된다`() {
        // given
        val command = JoinMemberRequestDto(
            phone = "01012345678",
            authenticationCode = "1234",
        )
        memberAuthenticationRedisRepository.setMemberAuthenticationCode(
            command.phone,
            command.authenticationCode,
        )

        // when
        val response = sut.execute(command)

        // then
        val memberId = response.memberId
        val bookcases = bookcaseRepository.findAllByMemberId(memberId, Pageable.unpaged())
        val books = bookRepository.findAllByMemberId(memberId)
        val questions = questionRepository.findAllByMemberId(memberId)
        val answers = answerRepository.findAllByMemberId(memberId)

        assertThat(bookcases).hasSize(1)
        assertThat(bookcases.content[0].name).isEqualTo(BookcasePOJO().name)

        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo(BookPOJO().name)
        assertThat(books[0].bookcaseId).isEqualTo(bookcases.content[0].id)

        assertThat(questions).hasSize(3)
        questions.forEach { q ->
            assertThat(q.answerList.size).isEqualTo(5)
            assertThat(q.answerId).isNotNull()
            assertThat(q.answerList.any { it.correctYn }).isTrue()
        }

        assertThat(answers.size).isEqualTo(3 * 5)
    }

    @Test
    fun `로그인 시에는 뉴비키트가 생성되지 않는다 (빌더로 생성해서 뉴비키트 없는게 맞음)`() {
        //given
        val member = memberRepository.save(MemberBuilder().build())
        val command = JoinMemberRequestDto(
            phone = member.phone,
            authenticationCode = "1234",
        )
        memberAuthenticationRedisRepository.setMemberAuthenticationCode(
            command.phone,
            command.authenticationCode,
        )

        // when
        sut.execute(command)

        //then
        val bookcases = bookcaseRepository.findAllByMemberId(member.id, Pageable.unpaged())
        val books = bookRepository.findAllByMemberId(member.id)
        val questions = questionRepository.findAllByMemberId(member.id)
        val answers = answerRepository.findAllByMemberId(member.id)

        assertThat(bookcases).hasSize(0)
        assertThat(books).hasSize(0)
        assertThat(questions).hasSize(0)
        assertThat(answers).hasSize(0)
    }
}
