package self.study.sels.model.book

import self.study.sels.userapi.book.application.action.GetBookResponseDto

interface BookRepositoryCustom {
    fun findByBookIdAndMemberId(
        bookId: Int,
        memberId: Int
    ): GetBookResponseDto?
}
