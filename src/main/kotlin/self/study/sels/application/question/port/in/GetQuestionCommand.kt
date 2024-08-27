package self.study.sels.application.question.port.`in`

class GetQuestionCommand(
    val memberId: Int,
    val questionId: Int,
    val getAnswerList: Boolean,
    val getCorrectAnswer: Boolean,
)
