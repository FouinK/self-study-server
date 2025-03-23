package self.study.sels.application.question.port.`in`

import self.study.sels.controller.dto.GetQuestionResponseDto

interface GetQuestionUseCase {
    fun detail(command: GetQuestionCommand): GetQuestionResponseDto
}

class GetQuestionCommand(
    val memberId: Int,
    val questionId: Int,
    val getAnswerList: Boolean,
    val getCorrectAnswer: Boolean,
)
