package self.study.sels.model.book

import self.study.sels.controller.dto.GetBookResponseDto

interface BookRepositoryCustom {
    fun findByBookIdAndMemberId(
        bookId: Int,
        memberId: Int
    ): GetBookResponseDto?
}
