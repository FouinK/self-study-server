package self.study.sels.userapi.book.application.port.`in`

interface StartSolveBookUseCase {
    fun execute(command: StartSolveBookCommand): Int
}

data class StartSolveBookCommand(
    val bookId: Int,
    val memberId: Int,
)
