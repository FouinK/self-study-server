package self.study.sels.userapi.question.application.port.`in`

import self.study.sels.userapi.question.application.action.UpdateQuestionAndAnswerRequestDto
import self.study.sels.userapi.question.application.action.UpdateQuestionAndAnswerResponseDto

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
