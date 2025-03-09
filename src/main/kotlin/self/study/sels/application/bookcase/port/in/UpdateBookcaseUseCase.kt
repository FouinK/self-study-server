package self.study.sels.application.bookcase.port.`in`

interface UpdateBookcaseUseCase {
    fun update(command: UpdateBookcaseCommand): String
}
