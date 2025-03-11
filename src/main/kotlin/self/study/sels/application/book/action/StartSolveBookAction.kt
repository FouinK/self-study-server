package self.study.sels.application.book.action

import org.springframework.transaction.annotation.Transactional
import self.study.sels.annotation.Action
import self.study.sels.application.book.port.`in`.StartSolveBookCommand
import self.study.sels.application.book.port.`in`.StartSolveBookUseCase
import self.study.sels.exception.NotFoundException
import self.study.sels.model.book.BookRepository
import self.study.sels.model.bookresulthistory.BookResultHistoryFactory
import self.study.sels.model.bookresulthistory.BookResultHistoryRepository

@Action
class StartSolveBookAction(
    private val bookRepository: BookRepository,
    private val bookResultHistoryFactory: BookResultHistoryFactory,
    private val bookResultHistoryRepository: BookResultHistoryRepository,
) : StartSolveBookUseCase {
    @Transactional
    override fun execute(
        command: StartSolveBookCommand
    ): Int {
        val book = bookRepository.findByIdAndMemberId(command.bookId, command.memberId)
            ?: throw NotFoundException("책을 찾을 수 없습니다.")

        return bookResultHistoryRepository.save(
            bookResultHistoryFactory.create(
                BookResultHistoryFactory.Command(
                    bookId = book.id,
                    memberId = command.memberId,
                    bookcaseId = book.bookcaseId,
                    name = book.name,
                ),
            ),
        ).id
    }
}
