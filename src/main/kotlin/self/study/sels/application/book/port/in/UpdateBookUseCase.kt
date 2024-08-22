package self.study.sels.application.book.port.`in`

import self.study.sels.controller.dto.UpdateBookRequestDto

interface UpdateBookUseCase {
    fun update(command: UpdateBookRequestDto): String
}
