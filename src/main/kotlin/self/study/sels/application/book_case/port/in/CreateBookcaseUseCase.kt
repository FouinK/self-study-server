package self.study.sels.application.book_case.port.`in`

interface CreateBookcaseUseCase {
    fun create(command: CreateBookcaseCommand): Int
}
