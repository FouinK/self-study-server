package self.study.sels.fixture

import self.study.sels.model.book.Book

class BookBuilder(
    val name: String,
    val color: String,
    val memberId: Int,
    val bookcaseId: Int,
) {
    fun build() =
        Book(
            name = name,
            color = color,
            memberId = memberId,
            bookcaseId = bookcaseId,
        )
}
