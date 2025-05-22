package self.study.sels.userapi.book.application.action

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
