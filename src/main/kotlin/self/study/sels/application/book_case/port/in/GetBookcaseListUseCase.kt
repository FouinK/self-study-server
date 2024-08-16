package self.study.sels.application.book_case.port.`in`

import self.study.sels.controller.dto.GetBookcaseListResponseDto

interface GetBookcaseListUseCase {
    fun list(command: GetBookcaseListCommand): GetBookcaseListResponseDto
}
