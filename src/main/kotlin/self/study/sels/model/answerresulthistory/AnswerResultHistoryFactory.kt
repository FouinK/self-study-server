package self.study.sels.model.answerresulthistory

import org.springframework.stereotype.Component
import self.study.sels.model.questionresulthistory.QuestionResultHistory

@Component
class AnswerResultHistoryFactory {
    fun create(command: Command) =
        AnswerResultHistory(
            question = command.question,
            answer = command.answer,
            correctYn = command.correctYn,
            selectedYn = command.selectedYn,
            memberId = command.memberId,
        )

    data class Command(
        val question: QuestionResultHistory,
        val answer: String,
        val correctYn: Boolean = false,
        val selectedYn: Boolean = false,
        val memberId: Int,
    )
}
