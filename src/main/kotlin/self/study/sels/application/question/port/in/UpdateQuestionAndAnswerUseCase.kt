package self.study.sels.application.question.port.`in`

import self.study.sels.controller.dto.UpdateQuestionAndAnswerRequestDto
import self.study.sels.controller.dto.UpdateQuestionAndAnswerResponseDto

interface UpdateQuestionAndAnswerUseCase {
    fun update(
        command: UpdateQuestionAndAnswerCommand
    ): UpdateQuestionAndAnswerResponseDto
}

class UpdateQuestionAndAnswerCommand(
    val questionId: Int,
    val question: String?,
    val answerList: List<UpdateQuestionAndAnswerRequestDto.AnswerItem>,
    val memberId: Int,
)
