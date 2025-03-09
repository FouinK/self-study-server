package self.study.sels.application.bookcase.port.`in`

class UpdateBookcaseCommand(
    val bookcaseId: Int,
    val name: String,
    val memberId: Int,
)
