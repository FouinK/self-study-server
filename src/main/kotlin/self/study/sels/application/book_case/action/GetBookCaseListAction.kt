package self.study.sels.application.book_case.action

import org.springframework.stereotype.Component
import self.study.sels.application.book_case.port.`in`.GetBookCaseListUseCase

@Component
class GetBookCaseListAction : GetBookCaseListUseCase {
    override fun list(memberId: Int) {
    }
}
