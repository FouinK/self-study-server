package self.study.sels.application.book_case.port.`in`

import org.springframework.data.domain.Pageable

class GetBookcaseListCommand(
    val memberId: Int,
    val pageable: Pageable,
)
