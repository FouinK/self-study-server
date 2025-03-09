package self.study.sels.application.bookcase.port.`in`

interface CreateBookcaseUseCase {
    fun create(command: CreateBookcaseCommand): Int
}
