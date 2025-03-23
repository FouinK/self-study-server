package self.study.sels.application.answer.port.`in`

interface SolveQuestionUseCase {
    fun execute(command: SolveQuestionCommand)
}

data class SolveQuestionCommand(
    val questionId: Int,
    val answerId: Int?,
    val answer: String?,
    val memberId: Int,
)
