package self.study.sels.application.book_case.port.`in`

import self.study.sels.controller.dto.GetBookcaseResponseDto

interface GetBookcaseUseCase {
    fun detail(command: GetBookcaseCommand): GetBookcaseResponseDto
}
