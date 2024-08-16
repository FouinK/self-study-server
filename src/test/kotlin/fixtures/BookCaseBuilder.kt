package fixtures

import self.study.sels.model.book_case.BookCase

class BookCaseBuilder(
    val name: String,
    val memberId: Int,
) {
    fun build() =
        BookCase(
            memberId = memberId,
            name = name,
        )
}
