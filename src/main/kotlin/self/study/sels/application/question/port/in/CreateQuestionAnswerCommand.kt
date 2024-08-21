package self.study.sels.application.question.port.`in`

import self.study.sels.controller.dto.CreateQuestionAnswerRequestDto
import self.study.sels.model.answer.Answer
import self.study.sels.model.question.Question

class CreateQuestionAnswerCommand(
    val bookId: Int,
    val question: String,
    val multipleChoiceYn: Boolean,
    val memberId: Int,
    val answerList: List<CreateQuestionAnswerRequestDto.AnswerItem>,
) {
    fun toAnswerEntityList(question: Question): List<Answer> {
        return answerList.map {
            Answer(
                question = question,
                answer = it.answer,
                correctYn = it.correctYn,
            )
        }
    }
}
