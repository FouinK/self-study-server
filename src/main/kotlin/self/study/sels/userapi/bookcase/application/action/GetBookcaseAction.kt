package self.study.sels.userapi.bookcase.application.action

import self.study.sels.annotation.Action
import self.study.sels.exception.NotFoundException
import self.study.sels.model.book.BookRepository
import self.study.sels.model.bookcase.BookcaseRepository
import self.study.sels.userapi.bookcase.application.port.`in`.GetBookcaseCommand
import self.study.sels.userapi.bookcase.application.port.`in`.GetBookcaseUseCase

@Action
class GetBookcaseAction(
    private val bookRepository: BookRepository,
    private val bookcaseRepository: BookcaseRepository,
) : GetBookcaseUseCase {
    override fun detail(
        command: GetBookcaseCommand
    ): GetBookcaseResponseDto {
        val bookList = bookRepository.findAllByBookcaseIdAndMemberId(command.bookcaseId, command.memberId)

        val bookcaseName = bookcaseRepository
            .findById(command.bookcaseId)
            .orElseThrow { throw NotFoundException("책장을 찾을 수 없습니다.") }
            .name

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
