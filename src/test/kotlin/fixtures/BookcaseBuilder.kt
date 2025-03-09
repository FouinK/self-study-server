package fixtures

import self.study.sels.model.bookcase.Bookcase

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
