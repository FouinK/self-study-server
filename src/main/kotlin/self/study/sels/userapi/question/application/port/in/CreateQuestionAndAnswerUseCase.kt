package self.study.sels.userapi.question.application.port.`in`

import self.study.sels.userapi.question.application.action.CreateQuestionAndAnswerRequestDto

interface CreateQuestionAndAnswerUseCase {
    fun createQuestionAndAnswer(command: CreateQuestionAndAnswerCommand): Int
}

class CreateQuestionAndAnswerCommand(
    val bookId: Int,
    val question: String,
    val memberId: Int,
    // TODO : size 1 이상인지 검증하기
    val answerList: List<CreateQuestionAndAnswerRequestDto.AnswerItem>,
)
