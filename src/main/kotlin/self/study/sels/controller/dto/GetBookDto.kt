package self.study.sels.controller.dto

class GetBookResponseDto(
    val bookId: Int,
    val bookName: String,
    val questionList: List<QuestionItem>,
) {
    class QuestionItem(
        val questionId: Int,
        val question: String,
    )
}
