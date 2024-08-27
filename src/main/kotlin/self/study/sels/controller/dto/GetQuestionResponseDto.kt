package self.study.sels.controller.dto

class GetQuestionResponseDto(
    val questionId: Int,
    val multipleChoiceYn: Boolean,
    val answerId: Int?,
    val answerList: List<Item>
) {
    class Item(
        answerId: Int,
        answer: String,
        correctYn: Boolean,
    )
}
