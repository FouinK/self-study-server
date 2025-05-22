package self.study.sels.userapi.question.application.action

class GetQuestionResponseDto(
    val questionId: Int,
    val question: String,
    val answerId: Int?,
    val answerList: List<Item>
) {
    class Item(
        val answerId: Int,
        val answer: String,
        val correctYn: Boolean,
    )
}
