package self.study.sels.userapi.answer.application.port.`in`

interface SolveQuestionUseCase {
    fun execute(command: SolveQuestionCommand)
}

data class SolveQuestionCommand(
    val questionId: Int,
    val answerId: Int?,
    val answer: String?,
    val memberId: Int,
)
