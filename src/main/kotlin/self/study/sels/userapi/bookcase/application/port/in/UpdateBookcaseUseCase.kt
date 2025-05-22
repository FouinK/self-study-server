package self.study.sels.userapi.bookcase.application.port.`in`

interface UpdateBookcaseUseCase {
    fun update(command: UpdateBookcaseCommand): String
}

class UpdateBookcaseCommand(
    val bookcaseId: Int,
    val name: String,
    val memberId: Int,
)
