package self.study.sels.userapi.book.application.action

class UpdateBookRequestDto(
    val bookId: Int,
    val name: String,
)

class UpdateBookResponseDto(
    val name: String,
)
