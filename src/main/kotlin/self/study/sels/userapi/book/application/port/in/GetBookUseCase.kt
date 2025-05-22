package self.study.sels.userapi.book.application.port.`in`

import self.study.sels.userapi.book.application.action.GetBookResponseDto

interface GetBookUseCase {
    fun detail(command: GetBookCommand): GetBookResponseDto
}

class GetBookCommand(
    val bookId: Int,
    val memberId: Int,
)
