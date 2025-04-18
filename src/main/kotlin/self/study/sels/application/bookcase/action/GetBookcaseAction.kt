package self.study.sels.application.bookcase.action

import self.study.sels.annotation.Action
import self.study.sels.application.bookcase.port.`in`.GetBookcaseCommand
import self.study.sels.application.bookcase.port.`in`.GetBookcaseUseCase
import self.study.sels.controller.dto.GetBookcaseResponseDto
import self.study.sels.exception.NotFoundException
import self.study.sels.model.book.BookRepository
import self.study.sels.model.bookcase.BookcaseRepository

@Action
class GetBookcaseAction(
    private val bookRepository: BookRepository,
    private val bookcaseRepository: BookcaseRepository,
) : GetBookcaseUseCase {
    override fun detail(
        command: GetBookcaseCommand
    ): GetBookcaseResponseDto {
        val bookList = bookRepository.findAllByBookcaseIdAndMemberId(command.bookcaseId, command.memberId)

        val bookcaseName = bookcaseRepository.findById(command.bookcaseId)
            .orElseThrow { throw NotFoundException("책장을 찾을 수 없습니다.") }.name

        return GetBookcaseResponseDto(
            bookList = bookList.map {
                GetBookcaseResponseDto.Item(
                    it.id,
                    it.bookcaseId,
                    bookName = it.name,
                    bookColor = it.color,
                )
            },
            bookcaseName = bookcaseName,
        )
    }
}
