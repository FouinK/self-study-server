package self.study.sels.application.book.port.`in`

interface UpdateBookUseCase {
    fun update(command: UpdateBookCommand): String
}
