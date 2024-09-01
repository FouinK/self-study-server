package fixtures

import self.study.sels.model.answer.Answer
import self.study.sels.model.question.Question

class AnswerListBuilder(
    val size: Int = 1,
    val question: Question,
    val answerList: List<String>,
    val correctYnList: List<Boolean>,
) {
    fun build(): List<Answer> =
        List(size) { index ->
            AnswerBuilder(
                question = question,
                answer = answerList[index],
                correctYn = correctYnList[index],
                memberId = question.memberId,
            ).build()
        }
}
