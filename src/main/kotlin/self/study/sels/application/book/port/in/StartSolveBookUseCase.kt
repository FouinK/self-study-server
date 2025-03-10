package self.study.sels.application.book.port.`in`

interface StartSolveBookUseCase {
    fun execute(command: StartSolveBookCommand): Int
}

data class StartSolveBookCommand(
    val bookId: Int,
    val memberId: Int,
)
