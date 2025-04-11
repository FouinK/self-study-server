package self.study.sels.application.bookcase.port.`in`

import org.springframework.data.domain.Pageable
import self.study.sels.controller.dto.GetBookcaseListResponseDto

interface GetBookcaseListUseCase {
    fun list(command: GetBookcaseListCommand): GetBookcaseListResponseDto
}

class GetBookcaseListCommand(
    val memberId: Int,
    val pageable: Pageable,
)
