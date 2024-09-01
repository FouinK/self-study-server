package self.study.sels.controller.dto

import jakarta.validation.constraints.NotNull

class UpdateQuestionAndAnswerRequestDto(
    @field:NotNull
    val questionId: Int,
    val question: String?,
    val answerList: List<AnswerItem>,
) {
    class AnswerItem(
        val answerId: Int?,
        val answer: String?,
        val correctYn: Boolean?,
    )
}
