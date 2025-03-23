package self.study.sels.model.questionresulthistory

import org.springframework.stereotype.Component
import self.study.sels.model.question.QuestionType

@Component
class QuestionResultHistoryFactory {
    fun create(command: Command) =
        QuestionResultHistory(
            questionId = command.questionId,
            memberId = command.memberId,
            bookId = command.bookId,
            question = command.question,
            answerId = command.answerId,
            questionType = command.questionType,
        )

    data class Command(
        val questionId: Int,
        val memberId: Int,
        val bookId: Int,
        val question: String,
        val answerId: Int?,
        val questionType: QuestionType,
    )
}
