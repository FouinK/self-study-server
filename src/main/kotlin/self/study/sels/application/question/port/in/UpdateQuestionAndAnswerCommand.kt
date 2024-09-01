package self.study.sels.application.question.port.`in`

import self.study.sels.controller.dto.UpdateQuestionAndAnswerRequestDto

class UpdateQuestionAndAnswerCommand(
    val questionId: Int,
    val question: String?,
    val answerList: List<UpdateQuestionAndAnswerRequestDto.AnswerItem>,
    val memberId: Int,
)
