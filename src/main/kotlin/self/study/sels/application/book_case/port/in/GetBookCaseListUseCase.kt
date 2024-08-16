package self.study.sels.application.book_case.port.`in`

import self.study.sels.controller.dto.GetBookCaseListResponseDto

interface GetBookCaseListUseCase {
    fun list(command: GetBookCaseListCommand): GetBookCaseListResponseDto
}
