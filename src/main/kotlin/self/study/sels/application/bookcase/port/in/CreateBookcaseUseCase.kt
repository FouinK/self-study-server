package self.study.sels.application.bookcase.port.`in`

interface CreateBookcaseUseCase {
    fun create(command: CreateBookcaseCommand): Int
}

class CreateBookcaseCommand(
    val name: String,
    val color: String,
    val memberId: Int,
)
