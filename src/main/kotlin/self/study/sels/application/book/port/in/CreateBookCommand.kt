package self.study.sels.application.book.port.`in`

import self.study.sels.model.book.Book

class CreateBookCommand(
    val bookcaseId: Int,
    val name: String,
    val memberId: Int,
) {
    fun toEntity(): Book {
        return Book(
            bookcaseId = this.bookcaseId,
            name = this.name,
            memberId = this.memberId,
        )
    }
}
