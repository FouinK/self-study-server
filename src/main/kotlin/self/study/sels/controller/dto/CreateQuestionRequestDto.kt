package self.study.sels.controller.dto

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull

class CreateQuestionAndAnswerRequestDto(
    @field:NotNull
    val bookId: Int,
    @field:NotBlank
    val question: String,
    val answerList: List<AnswerItem>,
) {
    class AnswerItem(
        val answer: String,
        val correctYn: Boolean,
    )
}
