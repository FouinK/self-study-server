package self.study.sels.application.book.port.`in`

import self.study.sels.model.book.Book

interface CreateBookUseCase {
    fun create(command: CreateBookCommand): Int
}

class CreateBookCommand(
    val bookcaseId: Int,
    val name: String,
    val color: String,
    val memberId: Int,
) {
    fun toEntity(): Book {
        return Book(
            bookcaseId = this.bookcaseId,
            name = this.name,
            color = this.color,
            memberId = this.memberId,
        )
    }
}
