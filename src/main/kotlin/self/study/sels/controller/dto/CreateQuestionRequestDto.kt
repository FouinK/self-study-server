package self.study.sels.controller.dto

class CreateQuestionAnswerRequestDto(
    val bookId: Int,
    val question: String,
    val multipleChoiceYn: Boolean,
    val answerList: List<AnswerItem>,
) {
    class AnswerItem(
        val answer: String,
        val correctYn: Boolean,
    )
}
