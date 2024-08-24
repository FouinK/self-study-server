package self.study.sels.application.book_case.port.`in`

import self.study.sels.controller.dto.UpdateBookcaseRequestDto

interface UpdateBookcaseUseCase {
    fun update(command: UpdateBookcaseRequestDto): String
}
