package self.study.sels.application.book_case.action

import org.springframework.stereotype.Component
import self.study.sels.application.book_case.port.`in`.GetBookCaseCommand
import self.study.sels.application.book_case.port.`in`.GetBookCaseUseCase
import self.study.sels.model.book.BookRepository

@Component
class GetBookcaseAction(
    private val bookRepository: BookRepository,
) : GetBookCaseUseCase {
    override fun detail(command: GetBookCaseCommand) {
        val bookCase =
            bookRepository.findAllByBookcaseIdAndMemberId(command.bookcaseId, command.memberId)
    }
}
