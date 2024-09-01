package fixtures

import self.study.sels.model.answer.Answer
import self.study.sels.model.question.Question

class AnswerBuilder(
    val question: Question,
    val answer: String,
    val correctYn: Boolean = false,
    val memberId: Int,
) {
    fun build(): Answer =
        Answer(
            question = question,
            answer = answer,
            correctYn = correctYn,
            memberId = memberId,
        )
}
