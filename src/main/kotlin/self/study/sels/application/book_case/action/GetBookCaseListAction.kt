package self.study.sels.application.book_case.action

import org.springframework.stereotype.Component
import self.study.sels.application.book_case.port.`in`.GetBookCaseListCommand
import self.study.sels.application.book_case.port.`in`.GetBookCaseListUseCase
import self.study.sels.model.book_case.BookCaseRepository

@Component
class GetBookCaseListAction(
    private val bookCaseRepository: BookCaseRepository,
) : GetBookCaseListUseCase {
    override fun list(command: GetBookCaseListCommand) {
        val bookCasePage =
            bookCaseRepository.findAllByMemberId(
                memberId = command.memberId,
                pageable = command.pageable,
            )

        println(bookCasePage)
    }
}
