package self.study.sels.application.book_case.port.`in`

import org.springframework.data.domain.Pageable

class GetBookCaseListCommand(
    val memberId: Int,
    val pageable: Pageable,
)
