package self.study.sels.controller.dto

class UpdateBookRequestDto(
    val bookId: Int,
    val name: String,
)

class UpdateBookResponseDto(
    val name: String,
)
