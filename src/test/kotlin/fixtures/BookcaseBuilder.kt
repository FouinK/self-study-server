package fixtures

import self.study.sels.model.book_case.Bookcase

class BookcaseBuilder(
    val name: String,
    val memberId: Int,
) {
    fun build() =
        Bookcase(
            memberId = memberId,
            name = name,
        )
}
