package self.study.sels.application.bookcase.port.`in`

import self.study.sels.controller.dto.GetBookcaseListResponseDto

interface GetBookcaseListUseCase {
    fun list(command: GetBookcaseListCommand): GetBookcaseListResponseDto
}
