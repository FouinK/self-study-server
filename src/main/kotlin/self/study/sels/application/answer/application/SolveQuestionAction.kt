package self.study.sels.application.answer.application

import org.springframework.transaction.annotation.Transactional
import self.study.sels.annotation.Action
import self.study.sels.application.answer.port.`in`.SolveQuestionCommand
import self.study.sels.application.answer.port.`in`.SolveQuestionUseCase
import self.study.sels.exception.NotFoundException
import self.study.sels.model.answerresulthistory.AnswerResultHistoryFactory
import self.study.sels.model.question.Question
import self.study.sels.model.question.QuestionRepository
import self.study.sels.model.questionresulthistory.QuestionResultHistoryFactory
import self.study.sels.model.questionresulthistory.QuestionResultHistoryRepository

@Action
class SolveQuestionAction(
    private val questionRepository: QuestionRepository,
    private val questionResultHistoryFactory: QuestionResultHistoryFactory,
    private val answerResultHistoryFactory: AnswerResultHistoryFactory,
    private val questionResultHistoryRepository: QuestionResultHistoryRepository,
) : SolveQuestionUseCase {

    @Transactional
    override fun execute(command: SolveQuestionCommand) {
        val question = questionRepository.findByIdAndMemberId(
            command.questionId,
            command.memberId,
        ) ?: throw NotFoundException("질문을 찾을 수 없습니다.")

        saveResultHistory(question, command)

        if (question.isShort()) {
            // TODO : 디코로 전송
            question.answerList.firstOrNull() ?: throw NotFoundException("정답이 없습니다.")
        } else {
        }
    }

    private fun saveResultHistory(
        question: Question,
        command: SolveQuestionCommand,
    ) {
        val questionResultHistory = questionResultHistoryFactory.create(
            QuestionResultHistoryFactory.Command(
                questionId = question.id,
                memberId = question.memberId,
                bookId = question.bookId,
                question = question.question,
                answerId = question.answerId,
                questionType = question.questionType,
            ),
        )

        val answerResultHistories = question.answerList.map {
            answerResultHistoryFactory.create(
                AnswerResultHistoryFactory.Command(
                    question = questionResultHistory,
                    answer = it.answer,
                    correctYn = it.correctYn,
                    selectedYn = it.id == command.answerId,
                    memberId = it.memberId,
                ),
            )
        }

        questionResultHistory.updateAnswerResultHistoryList(answerResultHistories)

        questionResultHistoryRepository.save(questionResultHistory)
    }
}
