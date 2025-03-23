package self.study.sels.application.answer.application

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import self.study.sels.IntegrationTest
import self.study.sels.application.answer.port.`in`.SolveQuestionCommand
import self.study.sels.application.answer.port.`in`.SolveQuestionUseCase
import self.study.sels.fixture.step.CreateQuestionStep
import self.study.sels.model.answer.Answer
import self.study.sels.model.member.Member
import self.study.sels.model.question.Question
import self.study.sels.model.questionresulthistory.QuestionResultHistoryRepository

class SolveQuestionActionTest(
    private val createQuestionStep: CreateQuestionStep,
    private val solveQuestionUseCase: SolveQuestionUseCase,
    private val questionResultHistoryRepository: QuestionResultHistoryRepository,
) : IntegrationTest() {
    lateinit var member: Member
    lateinit var question: Question
    lateinit var questionString: String
    lateinit var answer1: Answer
    lateinit var answer2: Answer
    lateinit var answer3: Answer
    lateinit var answer4: Answer
    lateinit var answer5: Answer

    @BeforeEach
    fun setUp() {
        questionString = "문제"
        val createQuestionStepResponse = createQuestionStep.create(
            questionString = questionString,
            answerListSize = 5,
        )

        question = createQuestionStepResponse.question
        member = createQuestionStepResponse.member

        question.answerList.forEachIndexed { index, answer ->
            when (index) {
                0 -> answer1 = answer
                1 -> answer2 = answer
                2 -> answer3 = answer
                3 -> answer4 = answer
                4 -> answer5 = answer
            }
        }
    }

    @Test
    fun `문제를 풀 때 결과 테이블에 정상 저장된다`() {
        //given
        val command = SolveQuestionCommand(
            questionId = question.id,
            answerId = answer1.id,
            answer = answer1.answer,
            memberId = member.id,
        )

        //when
        solveQuestionUseCase.execute(command)

        //then
        val questionResultHistory = questionResultHistoryRepository.findByQuestionId(questionId = question.id)!!

        assertThat(questionResultHistory.answerResultHistoryList).hasSize(5)
        assertThat(questionResultHistory.question).isEqualTo(question.question)
        assertThat(questionResultHistory.answerResultHistoryList).hasSize(5)
        assertThat(
            questionResultHistory.answerResultHistoryList.first { it.correctYn }.id,
        ).isEqualTo(question.answerList.first { it.correctYn }.id)
    }
}
