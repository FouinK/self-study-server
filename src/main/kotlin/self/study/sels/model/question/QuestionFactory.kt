package self.study.sels.model.question

import org.springframework.stereotype.Component

@Component
class QuestionFactory {
    fun create(command: Command): Question =
        Question(
            memberId = command.memberId,
            bookId = command.bookId,
            question = command.question,
        )

    data class Command(
        val memberId: Int,
        val bookId: Int,
        val question: String,
    )
}
