package self.study.sels.application.book_case.port.`in`

interface UpdateBookcaseUseCase {
    fun update(command: UpdateBookcaseCommand): String
}
