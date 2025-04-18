package self.study.sels.fixture

import self.study.sels.model.bookcase.Bookcase

class BookcaseBuilder(
    val name: String,
    val color: String,
    val memberId: Int,
) {
    fun build() =
        Bookcase(
            memberId = memberId,
            name = name,
            color = color,
        )
}
