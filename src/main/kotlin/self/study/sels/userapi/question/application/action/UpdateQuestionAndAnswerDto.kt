package self.study.sels.userapi.question.application.action

import jakarta.validation.constraints.NotNull

class UpdateQuestionAndAnswerRequestDto(
    @field:NotNull
    val questionId: Int,
    val question: String?,
    val answerList: List<AnswerItem>,
) {
    class AnswerItem(
        val answer: String,
        val correctYn: Boolean,
    )
}

class UpdateQuestionAndAnswerResponseDto(
    val questionId: Int,
)
