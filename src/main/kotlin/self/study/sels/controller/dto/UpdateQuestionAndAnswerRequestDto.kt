package self.study.sels.controller.dto

import jakarta.validation.constraints.NotNull

class UpdateQuestionAndAnswerRequestDto(
    @field:NotNull
    val questionId: Int,
    val answerList: List<CreateQuestionAndAnswerRequestDto.AnswerItem>,
)
