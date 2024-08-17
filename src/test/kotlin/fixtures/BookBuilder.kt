package fixtures

import self.study.sels.model.book.Book

class BookBuilder(
    val name: String,
    val memberId: Int,
    val bookcaseId: Int,
) {
    fun build() =
        Book(
            name = name,
            memberId = memberId,
            bookcaseId = bookcaseId,
        )
}
