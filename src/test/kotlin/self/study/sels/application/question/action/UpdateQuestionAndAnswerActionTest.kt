package self.study.sels.application.question.action

import fixtures.BookBuilder
import fixtures.MemberBuilder
import fixtures.step.CreateQuestionStep
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import self.study.sels.application.question.port.`in`.UpdateQuestionAndAnswerCommand
import self.study.sels.application.question.port.`in`.UpdateQuestionAndAnswerUseCase
import self.study.sels.controller.dto.UpdateQuestionAndAnswerRequestDto
import self.study.sels.exception.NotFoundException
import self.study.sels.model.answer.AnswerRepository
import self.study.sels.model.book.Book
import self.study.sels.model.book.BookRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import self.study.sels.model.question.Question
import self.study.sels.model.question.QuestionRepository

@SpringBootTest
class UpdateQuestionAndAnswerActionTest(
    @Autowired val questionRepository: QuestionRepository,
    @Autowired val answerRepository: AnswerRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val bookRepository: BookRepository,
) {
    lateinit var updateQuestionAndAnswerUseCase: UpdateQuestionAndAnswerUseCase
    lateinit var member: Member
    lateinit var question: Question
    lateinit var book: Book
    lateinit var questionString: String

    @BeforeEach
    fun setUp() {
        //useCase = Action
        updateQuestionAndAnswerUseCase = UpdateQuestionAndAnswerAction(
            questionRepository,
        )

        val createQuestionStep = CreateQuestionStep(
            questionRepository,
            answerRepository,
        )

        member = memberRepository.save(MemberBuilder().build())
        book = bookRepository.save(
            BookBuilder(
                name = "영어",
                memberId = member.id,
                bookcaseId = 1,
            ).build(),
        )

        questionString = "문제"

        question = createQuestionStep.create(
            member = member,
            book = book,
            questionString = questionString,
            answerListSize = 5,
        )
    }

    @Test
    fun `문제의 이름이 정상 업데이트 된다`() {
        //given
        val updatedName = "수정된 문제 이름"

        val command = UpdateQuestionAndAnswerCommand(
            questionId = question.id,
            question = updatedName,
            answerList = listOf(),
            memberId = member.id,
        )

        //when
        val result = updateQuestionAndAnswerUseCase.update(command)

        //then
        val question = questionRepository.findByIdOrNull(result.questionId)!!
        assertThat(question.question).isEqualTo(updatedName)
    }

    @Test
    fun `보기의 설명이 정상 업데이트 된다`() {
        //given
        val updatedName = "수정된 보기 설명"

        val answer = question.answerList.first()

        val command = UpdateQuestionAndAnswerCommand(
            questionId = question.id,
            question = null,
            answerList = listOf(
                UpdateQuestionAndAnswerRequestDto.AnswerItem(
                    answerId = answer.id,
                    answer = updatedName,
                    correctYn = true,
                ),
            ),
            memberId = member.id,
        )

        //when
        val result = updateQuestionAndAnswerUseCase.update(command)

        //then
        val findQuestion = questionRepository.findById(result.questionId)
            .orElseThrow { throw NotFoundException("테스트 실패") }

        val questionAnswer = findQuestion.answerList.find { it.id == answer.id }!!

        assertThat(questionAnswer.answer).isEqualTo(updatedName)
        assertThat(questionAnswer.correctYn).isTrue()
    }
}
