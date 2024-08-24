package self.study.sels.application.book.port.`in`

import self.study.sels.controller.dto.GetBookResponseDto

interface GetBookUseCase {
    fun detail(command: GetBookCommand): GetBookResponseDto
}
