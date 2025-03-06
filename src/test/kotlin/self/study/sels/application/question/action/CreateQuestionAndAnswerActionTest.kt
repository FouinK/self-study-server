package self.study.sels.application.question.action

import fixtures.BookBuilder
import fixtures.BookcaseBuilder
import fixtures.MemberBuilder
import fixtures.QuestionBuilder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.IntegrationTest
import self.study.sels.application.question.port.`in`.CreateQuestionAndAnswerCommand
import self.study.sels.application.question.port.`in`.CreateQuestionAndAnswerUseCase
import self.study.sels.controller.dto.CreateQuestionAndAnswerRequestDto
import self.study.sels.exception.ExistsNameException
import self.study.sels.exception.NotFoundException
import self.study.sels.model.book.Book
import self.study.sels.model.book.BookRepository
import self.study.sels.model.book_case.Bookcase
import self.study.sels.model.book_case.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import self.study.sels.model.question.QuestionRepository
import kotlin.jvm.optionals.getOrNull

@SpringBootTest
class CreateQuestionAndAnswerActionTest(
    private val createQuestionAndAnswerUseCase: CreateQuestionAndAnswerUseCase,
    private val questionRepository: QuestionRepository,
    private val memberRepository: MemberRepository,
    private val bookcaseRepository: BookcaseRepository,
    private val bookRepository: BookRepository,
) : IntegrationTest() {
    lateinit var member: Member
    lateinit var bookcase: Bookcase
    lateinit var book: Book
    lateinit var existsQuestion: String

    @BeforeEach
    fun setUp() {
        member = memberRepository.save(MemberBuilder().build())

        bookcase =
            bookcaseRepository.save(
                BookcaseBuilder(
                    name = "역사",
                    memberId = member.id,
                ).build(),
            )

        book =
            bookRepository.save(
                BookBuilder(
                    name = "1단원",
                    memberId = member.id,
                    bookcaseId = bookcase.id,
                ).build(),
            )

        existsQuestion = "이미 있는 질문"
        questionRepository.save(
            QuestionBuilder(
                memberId = member.id,
                bookId = book.id,
                question = existsQuestion,
            ).build(),
        )
    }

    @Test
    fun `질문과 답이 정상 저장된다`() {
        // given
        val questionString = "한글을 창조한 사람은?"
        val answerString = "세종대왕"

        val command =
            CreateQuestionAndAnswerCommand(
                bookId = book.id,
                question = questionString,
                memberId = member.id,
                answerList =
                    listOf(
                        CreateQuestionAndAnswerRequestDto.AnswerItem(
                            answer = answerString,
                            correctYn = true,
                        ),
                    ),
            )

        // when
        val questionId = createQuestionAndAnswerUseCase.createQuestionAndAnswer(command)

        // then
        val question = questionRepository.findById(questionId).getOrNull()!!

        assertThat(question.question).isEqualTo(questionString)
        assertThat(question.answerId).isNotNull()
        assertThat(question.answerList.size).isEqualTo(1)
        assertThat(question.answerList[0].answer).isEqualTo(answerString)
        assertThat(question.answerList[0].id).isEqualTo(question.answerId)
        assertThat(question.answerList[0].memberId).isEqualTo(question.memberId)
    }

    @Test
    fun `동일한 이름이 이미 존재할 시 예외가 발생한다`() {
        // given
        val answerString = "답"
        val command =
            CreateQuestionAndAnswerCommand(
                bookId = book.id,
                question = existsQuestion,
                answerList =
                    listOf(
                        CreateQuestionAndAnswerRequestDto.AnswerItem(
                            answer = answerString,
                            correctYn = true,
                        ),
                    ),
                memberId = member.id,
            )

        // when & then
        assertThrows<ExistsNameException> {
            createQuestionAndAnswerUseCase.createQuestionAndAnswer(command)
        }.message.apply { assertThat(this).isEqualTo("동일한 질문이 이미 존재합니다.") }
    }

    @Test
    fun `질문의 대한 대답이 없을 경우에는 예외가 발생한다`() {
        // given
        val questionString = "한글을 창조한 사람은?"
        val command =
            CreateQuestionAndAnswerCommand(
                bookId = book.id,
                question = questionString,
                answerList = listOf(),
                memberId = member.id,
            )

        // when & then
        assertThrows<IllegalStateException> {
            createQuestionAndAnswerUseCase.createQuestionAndAnswer(command)
        }
    }

    @Test
    fun `질문에 대한 대답이 있지만 정답이 한개라도 존재하지 않는 경우 예외가 발생한다`() {
        // given
        val questionString = "한글을 창조한 사람은?"
        val answerString = "세종대왕"

        val command =
            CreateQuestionAndAnswerCommand(
                bookId = book.id,
                question = questionString,
                memberId = member.id,
                answerList =
                    listOf(
                        CreateQuestionAndAnswerRequestDto.AnswerItem(
                            answer = answerString,
                            correctYn = false,
                        ),
                    ),
            )

        // when & then
        assertThrows<NotFoundException> {
            createQuestionAndAnswerUseCase.createQuestionAndAnswer(command)
        }.message.apply { assertThat(this).isEqualTo("질문에 대한 답 리스트가 존재하는데 정답이 없습니다.") }
    }
}
