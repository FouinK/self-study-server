package self.study.sels.application.book_case.port.`in`

class UpdateBookcaseCommand(
    val bookcaseId: Int,
    val name: String,
    val memberId: Int,
)
