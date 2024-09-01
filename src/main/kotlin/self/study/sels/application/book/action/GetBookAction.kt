package self.study.sels.application.book.action

import self.study.sels.annotation.Action
import self.study.sels.application.book.port.`in`.GetBookCommand
import self.study.sels.application.book.port.`in`.GetBookUseCase
import self.study.sels.controller.dto.GetBookResponseDto
import self.study.sels.model.book.BookRepository

@Action
class GetBookAction(
    private val bookRepository: BookRepository,
) : GetBookUseCase {
    override fun detail(
        command: GetBookCommand
    ): GetBookResponseDto {
        return bookRepository.findByBookIdAndMemberId(command.bookId, command.memberId)
            ?: throw Exception("책이 없습니다.")
    }
}
