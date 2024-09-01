package self.study.sels.controller.dto

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
