package self.study.sels.application.question.port.`in`

import self.study.sels.controller.dto.CreateQuestionAndAnswerRequestDto

class CreateQuestionAndAnswerCommand(
    val bookId: Int,
    val question: String,
    val memberId: Int,
    // TODO : size 1 이상인지 검증하기
    val answerList: List<CreateQuestionAndAnswerRequestDto.AnswerItem>,
)
