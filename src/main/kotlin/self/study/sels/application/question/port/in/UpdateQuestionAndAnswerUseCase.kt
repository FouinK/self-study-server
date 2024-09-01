package self.study.sels.application.question.port.`in`

import self.study.sels.controller.dto.UpdateQuestionAndAnswerResponseDto

interface UpdateQuestionAndAnswerUseCase {
    fun update(
        command: UpdateQuestionAndAnswerCommand
    ): UpdateQuestionAndAnswerResponseDto
}
