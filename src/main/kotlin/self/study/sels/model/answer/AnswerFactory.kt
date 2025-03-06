package self.study.sels.model.answer

import org.springframework.stereotype.Component
import self.study.sels.model.question.Question

@Component
class AnswerFactory {
    fun create(command: Command): Answer =
        Answer(
            question = command.question,
            answer = command.answer,
            correctYn = command.correctYn,
            memberId = command.memberId,
        )

    data class Command(
        val question: Question,
        val answer: String,
        val correctYn: Boolean,
        val memberId: Int,
    )
}
