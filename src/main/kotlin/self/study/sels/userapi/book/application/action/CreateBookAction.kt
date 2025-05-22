package self.study.sels.userapi.book.application.action

import self.study.sels.annotation.Action
import self.study.sels.model.book.BookFactory
import self.study.sels.model.book.BookRepository
import self.study.sels.model.bookcase.BookcaseRepository
import self.study.sels.userapi.book.application.port.`in`.CreateBookCommand
import self.study.sels.userapi.book.application.port.`in`.CreateBookUseCase

@Action
class CreateBookAction(
    private val bookcaseRepository: BookcaseRepository,
    private val bookRepository: BookRepository,
    private val bookFactory: BookFactory,
) : CreateBookUseCase {
    override fun create(
        command: CreateBookCommand
    ): Int {
        val bookcase = bookcaseRepository
            .findById(command.bookcaseId)
            .orElseThrow { throw Exception("책장이 존재하지 않습니다.") }

        val book = bookRepository.save(
            bookFactory.create(
                BookFactory.Command(
                    bookcaseId = bookcase.id,
                    name = command.name,
                    color = command.color,
                    memberId = command.memberId,
                ),
            ),
        )

        return book.id
    }
}
