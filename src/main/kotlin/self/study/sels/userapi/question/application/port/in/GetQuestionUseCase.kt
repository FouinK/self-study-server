package self.study.sels.userapi.question.application.port.`in`

import self.study.sels.userapi.question.application.action.GetQuestionResponseDto

interface GetQuestionUseCase {
    fun detail(command: GetQuestionCommand): GetQuestionResponseDto
}

class GetQuestionCommand(
    val memberId: Int,
    val questionId: Int,
    val getAnswerList: Boolean,
    val getCorrectAnswer: Boolean,
)
