package self.study.sels.userapi.bookcase.application.port.`in`

import org.springframework.data.domain.Pageable
import self.study.sels.userapi.bookcase.application.action.GetBookcaseListResponseDto

interface GetBookcaseListUseCase {
    fun list(command: GetBookcaseListCommand): GetBookcaseListResponseDto
}

class GetBookcaseListCommand(
    val memberId: Int,
    val pageable: Pageable,
)
