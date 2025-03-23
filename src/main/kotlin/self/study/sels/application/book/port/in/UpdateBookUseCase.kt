package self.study.sels.application.book.port.`in`

interface UpdateBookUseCase {
    fun update(command: UpdateBookCommand): String
}

class UpdateBookCommand(
    val bookId: Int,
    val name: String,
    val memberId: Int,
)
