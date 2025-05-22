package self.study.sels.userapi.bookcase.application.action

class GetBookcaseListResponseDto(
    val totalElement: Long,
    val page: Int,
    val bookcaseList: List<Item>,
) {
    class Item(
        val bookcaseId: Int,
        val bookcaseName: String,
        val bookcaseColor: String,
    )
}

class GetBookcaseResponseDto(
    val bookList: List<Item>,
    val bookcaseName: String?,
) {
    class Item(
        val bookId: Int,
        val bookcaseId: Int,
        val bookName: String,
        val bookColor: String,
    )
}
