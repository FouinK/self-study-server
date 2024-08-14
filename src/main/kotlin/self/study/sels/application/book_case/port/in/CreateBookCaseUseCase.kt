package self.study.sels.application.book_case.port.`in`

interface CreateBookCaseUseCase {
    fun create(command: CreateBookCaseCommand): Int
}
