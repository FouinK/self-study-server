package self.study.sels.userapi.book.application.action

import self.study.sels.annotation.Action
import self.study.sels.model.book.BookRepository
import self.study.sels.userapi.book.application.port.`in`.GetBookCommand
import self.study.sels.userapi.book.application.port.`in`.GetBookUseCase

@Action
class GetBookAction(
    private val bookRepository: BookRepository,
) : GetBookUseCase {
    override fun detail(
        command: GetBookCommand
    ): GetBookResponseDto = bookRepository.findByBookIdAndMemberId(command.bookId, command.memberId)
        ?: throw Exception("책이 없습니다.")
}
