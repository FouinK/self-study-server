package self.study.sels.application.book.port.`in`

class UpdateBookCommand(
    val bookId: Int,
    val name: String,
    val memberId: Int,
)
