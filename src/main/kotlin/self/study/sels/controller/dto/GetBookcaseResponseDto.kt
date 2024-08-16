package self.study.sels.controller.dto

class GetBookcaseResponseDto(
    val bookList: List<Item>,
) {
    class Item(
        val bookId: Int,
        val bookcaseId: Int,
        val bookName: String,
    )
}
