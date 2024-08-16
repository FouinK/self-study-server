package self.study.sels.controller.dto

class GetBookcaseListResponseDto(
    val totalElement: Long,
    val page: Int,
    val bookcaseList: List<Item>,
) {
    class Item(
        val bookCaseId: Int,
        val bookcaseName: String,
    )
}
