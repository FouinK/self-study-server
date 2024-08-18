package self.study.sels.application.book.port.`in`

class CreateBookCommand(
    val bookcaseId: Int,
    val name: String,
    val memberId: Int,
)
