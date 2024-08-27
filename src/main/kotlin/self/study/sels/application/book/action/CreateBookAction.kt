package self.study.sels.application.book.action

import self.study.sels.annotation.Action
import self.study.sels.application.book.port.`in`.CreateBookCommand
import self.study.sels.application.book.port.`in`.CreateBookUseCase
import self.study.sels.model.book.BookRepository
import self.study.sels.model.book_case.BookcaseRepository

@Action
class CreateBookAction(
    private val bookcaseRepository: BookcaseRepository,
    private val bookRepository: BookRepository,
) : CreateBookUseCase {
    override fun create(
        command: CreateBookCommand
    ): Int {
        val bookcase = bookcaseRepository.findById(command.bookcaseId)
            .orElseThrow { throw Exception("책장이 존재하지 않습니다.") }

        val book = command.toEntity()

        val newBook = bookRepository.save(book)

        return newBook.id
    }
}
