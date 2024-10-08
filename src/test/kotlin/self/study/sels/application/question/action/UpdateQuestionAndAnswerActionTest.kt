package self.study.sels.application.question.action

import fixtures.BookBuilder
import fixtures.MemberBuilder
import fixtures.step.CreateQuestionStep
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
        val question = questionRepository.findById(result.questionId)
            .orElseThrow { throw NotFoundException("테스트 실패") }

        assertThat(question.question).isEqualTo(updatedName)
        val answerList = question.answerList.map { it.answer }
        val correctYnList = question.answerList.map { it.correctYn }

        assertThat(answerList).containsExactly(
            "보기1",
            "보기2",
            "보기3",
            "보기4",
            "보기5",
        )

        assertThat(correctYnList).containsExactly(
            false,
            false,
            false,
            false,
            true,
        )
    }

    @Test
    fun `보기의 설명이 정상 업데이트 된다`() {
        //given
        val updatedName = "수정된 보기 설명"
        val updatedCorrecYn = true

        val answer = question.answerList.first()

        val command = UpdateQuestionAndAnswerCommand(
            questionId = question.id,
            question = null,
            answerList = listOf(
                UpdateQuestionAndAnswerRequestDto.AnswerItem(
                    answerId = answer.id,
                    answer = updatedName,
                    correctYn = updatedCorrecYn,
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

        assertThat(findQuestion.question).isEqualTo(questionString)
        assertThat(questionAnswer.answer).isEqualTo(updatedName)
        assertThat(questionAnswer.correctYn).isTrue()

        val answerList = findQuestion.answerList.map { it.answer }
        val correctYnList = findQuestion.answerList.map { it.correctYn }

        assertThat(answerList).containsExactly(
            updatedName,
            "보기2",
            "보기3",
            "보기4",
            "보기5",
        )
        assertThat(correctYnList).containsExactly(
            updatedCorrecYn,
            false,
            false,
            false,
            true,
        )
    }

    @Test
    fun `문제의 이름과 보기의 설명이 정상 업데이트 된다`() {
        //given
        val updatedQuestion = "수정된 문제"
        val updatedAnswer = "수정된 보기"
        val updatedAnswer2 = "수정된 보기2"
        val updateCorrectYn1 = true
        val updateCorrectYn2 = false

        val firstAnswer = question.answerList.first()
        val lastAnswer = question.answerList.last()

        val command = UpdateQuestionAndAnswerCommand(
            questionId = question.id,
            question = updatedQuestion,
            answerList = listOf(
                UpdateQuestionAndAnswerRequestDto.AnswerItem(
                    answerId = firstAnswer.id,
                    answer = updatedAnswer,
                    correctYn = updateCorrectYn1,
                ),
                UpdateQuestionAndAnswerRequestDto.AnswerItem(
                    answerId = lastAnswer.id,
                    answer = updatedAnswer2,
                    correctYn = updateCorrectYn2,
                ),
            ),
            memberId = member.id,
        )

        //when
        val result = updateQuestionAndAnswerUseCase.update(command)

        //then
        val findQuestion = questionRepository.findById(result.questionId)
            .orElseThrow { throw NotFoundException("테스트 실패") }

        val answerList = findQuestion.answerList.map { it.answer }
        val correctYnList = findQuestion.answerList.map { it.correctYn }

        assertThat(findQuestion.question).isEqualTo(updatedQuestion)
        assertThat(answerList).containsExactly(
            updatedAnswer,
            "보기2",
            "보기3",
            "보기4",
            updatedAnswer2,
        )

        assertThat(correctYnList).containsExactly(
            updateCorrectYn1,
            false,
            false,
            false,
            updateCorrectYn2,
        )
    }

    @Test
    fun `보기가 기존보다 더 추가되는경우 정상적으로 추가 된다`() {
        //given
        val updatedQuestion = "수정된 문제"
        val updatedAnswer = "수정된 보기"
        val updatedAnswer2 = "수정된 보기2"

        val updateCorrectYn1 = true
        val updateCorrectYn2 = false

        val addAnswer = "추가된 보기"
        val addCorrectYn3 = false

        val firstAnswer = question.answerList.first()
        val lastAnswer = question.answerList.last()

        val command = UpdateQuestionAndAnswerCommand(
            questionId = question.id,
            question = updatedQuestion,
            answerList = listOf(
                UpdateQuestionAndAnswerRequestDto.AnswerItem(
                    answerId = firstAnswer.id,
                    answer = updatedAnswer,
                    correctYn = updateCorrectYn1,
                ),
                UpdateQuestionAndAnswerRequestDto.AnswerItem(
                    answerId = lastAnswer.id,
                    answer = updatedAnswer2,
                    correctYn = updateCorrectYn2,
                ),
                UpdateQuestionAndAnswerRequestDto.AnswerItem(
                    answerId = null,
                    answer = addAnswer,
                    correctYn = addCorrectYn3,
                ),
            ),
            memberId = member.id,
        )

        //when
        val result = updateQuestionAndAnswerUseCase.update(command)

        //then
        val findQuestion = questionRepository.findById(result.questionId)
            .orElseThrow { throw NotFoundException("테스트 실패") }

        val answerList = findQuestion.answerList.map { it.answer }
        val correctYnList = findQuestion.answerList.map { it.correctYn }

        assertThat(findQuestion.question).isEqualTo(updatedQuestion)
        assertThat(answerList).containsExactly(
            updatedAnswer,
            "보기2",
            "보기3",
            "보기4",
            updatedAnswer2,
            addAnswer,
        )

        assertThat(correctYnList).containsExactly(
            updateCorrectYn1,
            false,
            false,
            false,
            updateCorrectYn2,
            addCorrectYn3,
        )
    }
}
