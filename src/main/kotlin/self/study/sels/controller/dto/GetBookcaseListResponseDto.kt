package self.study.sels.controller.dto

class GetBookcaseListResponseDto(
    val totalElement: Long,
    val page: Int,
    val bookCaseList: List<Item>,
) {
    class Item(
        val bookCaseId: Int,
        val bookCaseName: String,
    )
}
