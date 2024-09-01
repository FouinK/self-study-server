package self.study.sels.application.question.port.`in`

import self.study.sels.controller.dto.CreateQuestionAndAnswerRequestDto
import self.study.sels.model.answer.Answer
import self.study.sels.model.question.Question

class CreateQuestionAndAnswerCommand(
    val bookId: Int,
    val question: String,
    val memberId: Int,
    val answerList: List<CreateQuestionAndAnswerRequestDto.AnswerItem>,
) {
    fun toAnswerEntityList(question: Question): List<Answer> {
        return answerList.map {
            Answer(
                question = question,
                answer = it.answer,
                correctYn = it.correctYn,
                memberId = memberId,
            )
        }
    }
}
