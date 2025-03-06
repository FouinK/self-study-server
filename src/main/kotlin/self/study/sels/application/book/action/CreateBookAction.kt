package self.study.sels.application.book.action

import self.study.sels.annotation.Action
import self.study.sels.application.book.port.`in`.CreateBookCommand
import self.study.sels.application.book.port.`in`.CreateBookUseCase
import self.study.sels.model.book.BookFactory
import self.study.sels.model.book.BookRepository
import self.study.sels.model.book_case.BookcaseRepository

@Action
class CreateBookAction(
    private val bookcaseRepository: BookcaseRepository,
    private val bookRepository: BookRepository,
    private val bookFactory: BookFactory,
) : CreateBookUseCase {
    override fun create(
        command: CreateBookCommand
    ): Int {
        val bookcase = bookcaseRepository.findById(command.bookcaseId)
            .orElseThrow { throw Exception("책장이 존재하지 않습니다.") }

        val book = bookRepository.save(
            bookFactory.create(
                BookFactory.Command(
                    bookcaseId = bookcase.id,
                    name = command.name,
                    memberId = command.memberId,
                ),
            ),
        )

        return book.id
    }
}
