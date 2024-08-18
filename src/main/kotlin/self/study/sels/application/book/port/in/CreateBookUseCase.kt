package self.study.sels.application.book.port.`in`

interface CreateBookUseCase {
    fun create(command: CreateBookCommand): Int
}
