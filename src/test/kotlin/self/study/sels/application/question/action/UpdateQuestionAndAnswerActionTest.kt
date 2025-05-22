package self.study.sels.application.question.action

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import self.study.sels.IntegrationTest
import self.study.sels.fixture.step.CreateQuestionStep
import self.study.sels.model.book.Book
import self.study.sels.model.member.Member
import self.study.sels.model.question.Question
import self.study.sels.model.question.QuestionRepository
import self.study.sels.userapi.question.application.action.UpdateQuestionAndAnswerRequestDto
import self.study.sels.userapi.question.application.port.`in`.UpdateQuestionAndAnswerCommand
import self.study.sels.userapi.question.application.port.`in`.UpdateQuestionAndAnswerUseCase
import kotlin.jvm.optionals.getOrNull

class UpdateQuestionAndAnswerActionTest(
    private val updateQuestionAndAnswerUseCase: UpdateQuestionAndAnswerUseCase,
    private val questionRepository: QuestionRepository,
    private val createQuestionStep: CreateQuestionStep,
) : IntegrationTest() {
    lateinit var member: Member
    lateinit var question: Question
    lateinit var book: Book
    lateinit var questionString: String

    @BeforeEach
    fun setUp() {
        questionString = "문제"

        val createQuestionStepResponse = createQuestionStep.create(
            questionString = questionString,
            answerListSize = 5,
        )

        question = createQuestionStepResponse.question
        member = createQuestionStepResponse.member
        book = createQuestionStepResponse.book
    }

    @Test
    fun `문제의 이름이 정상 업데이트 된다`() {
        //given
        val updatedName = "수정된 문제 이름"

        val command = UpdateQuestionAndAnswerCommand(
            questionId = question.id,
            question = updatedName,
            answerList = question.answerList.map {
                UpdateQuestionAndAnswerRequestDto.AnswerItem(
                    answer = it.answer,
                    correctYn = it.correctYn,
                )
            },
            memberId = member.id,
        )

        //when
        val result = updateQuestionAndAnswerUseCase.update(command)

        //then
        val question = questionRepository.findById(result.questionId).getOrNull()!!

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
        val updatedCorrectYn = true

        val updateTargetAnswer = question.answerList.first()

        val command = UpdateQuestionAndAnswerCommand(
            questionId = question.id,
            question = null,
            answerList = question.answerList.map {
                if (updateTargetAnswer.id == it.id) {
                    UpdateQuestionAndAnswerRequestDto.AnswerItem(
                        answer = updatedName,
                        correctYn = updatedCorrectYn,
                    )
                } else {
                    UpdateQuestionAndAnswerRequestDto.AnswerItem(
                        answer = it.answer,
                        correctYn = it.correctYn,
                    )
                }
            },
            memberId = member.id,
        )

        //when
        val result = updateQuestionAndAnswerUseCase.update(command)

        //then
        val findQuestion = questionRepository.findById(result.questionId).getOrNull()!!

        assertThat(findQuestion.question).isEqualTo(questionString)

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
            updatedCorrectYn,
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
            answerList = question.answerList.map {
                if (it.id == firstAnswer.id) {
                    UpdateQuestionAndAnswerRequestDto.AnswerItem(
                        answer = updatedAnswer,
                        correctYn = updateCorrectYn1,
                    )
                } else if (it.id == lastAnswer.id) {
                    UpdateQuestionAndAnswerRequestDto.AnswerItem(
                        answer = updatedAnswer2,
                        correctYn = updateCorrectYn2,
                    )
                } else {
                    UpdateQuestionAndAnswerRequestDto.AnswerItem(
                        answer = it.answer,
                        correctYn = it.correctYn,
                    )
                }
            },
            memberId = member.id,
        )

        //when
        val result = updateQuestionAndAnswerUseCase.update(command)

        //then
        val findQuestion = questionRepository.findById(result.questionId).getOrNull()!!

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
            answerList = question.answerList.map {
                if (it.id == firstAnswer.id) {
                    UpdateQuestionAndAnswerRequestDto.AnswerItem(
                        answer = updatedAnswer,
                        correctYn = updateCorrectYn1,
                    )
                } else if (it.id == lastAnswer.id) {
                    UpdateQuestionAndAnswerRequestDto.AnswerItem(
                        answer = updatedAnswer2,
                        correctYn = updateCorrectYn2,
                    )
                } else {
                    UpdateQuestionAndAnswerRequestDto.AnswerItem(
                        answer = it.answer,
                        correctYn = it.correctYn,
                    )
                }
            } + listOf(
                UpdateQuestionAndAnswerRequestDto.AnswerItem(
                    answer = addAnswer,
                    correctYn = addCorrectYn3,
                ),
            ),
            memberId = member.id,
        )

        //when
        val result = updateQuestionAndAnswerUseCase.update(command)

        //then
        val findQuestion = questionRepository.findById(result.questionId).getOrNull()!!

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
