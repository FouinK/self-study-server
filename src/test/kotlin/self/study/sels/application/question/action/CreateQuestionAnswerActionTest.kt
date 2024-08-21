package self.study.sels.application.question.action

import fixtures.BookBuilder
import fixtures.BookcaseBuilder
import fixtures.MemberBuilder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerCommand
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerUseCase
import self.study.sels.controller.dto.CreateQuestionAnswerRequestDto
import self.study.sels.model.book.Book
import self.study.sels.model.book.BookRepository
import self.study.sels.model.book_case.Bookcase
import self.study.sels.model.book_case.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import self.study.sels.model.question.QuestionRepository

@SpringBootTest
class CreateQuestionAnswerActionTest(
    @Autowired val questionRepository: QuestionRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val bookcaseRepository: BookcaseRepository,
    @Autowired val bookRepository: BookRepository,
) {
    lateinit var createQuestionAnswerUseCase: CreateQuestionAnswerUseCase
    lateinit var member: Member
    lateinit var bookcase: Bookcase
    lateinit var book: Book

    @BeforeEach
    fun setUp() {
        createQuestionAnswerUseCase =
            CreateQuestionAnswerAction(
                questionRepository,
            )

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
    }

    @Test
    fun `질문과 답이 정상 저장된다`() {
        // given
        val questionString = "한글을 창조한 사람은?"
        val answerString = "세종대왕"

        val command =
            CreateQuestionAnswerCommand(
                bookId = book.id,
                question = questionString,
                multipleChoiceYn = false,
                memberId = member.id,
                answerList =
                    listOf(
                        CreateQuestionAnswerRequestDto.AnswerItem(
                            answer = answerString,
                            correctYn = true,
                        ),
                    ),
            )

        // when
        val questionId = createQuestionAnswerUseCase.createQuestionAnswer(command)

        // then
        val question =
            questionRepository.findById(questionId)
                .orElseThrow { throw Exception("테스트 실패") }

        assertThat(question.question).isEqualTo(questionString)
        assertThat(question.answerId).isNotNull()
        assertThat(question.answerList.size).isEqualTo(1)
        assertThat(question.answerList[0].answer).isEqualTo(answerString)
    }
}
