package self.study.sels.application.book_case.action

import org.springframework.stereotype.Component
import self.study.sels.application.book_case.port.`in`.GetBookcaseCommand
import self.study.sels.application.book_case.port.`in`.GetBookcaseUseCase
import self.study.sels.controller.dto.GetBookcaseResponseDto
import self.study.sels.model.book.BookRepository

@Component
class GetBookcaseAction(
    private val bookRepository: BookRepository,
) : GetBookcaseUseCase {
    override fun detail(
        command: GetBookcaseCommand
    ): GetBookcaseResponseDto {
        val bookList = bookRepository.findAllByBookcaseIdAndMemberId(command.bookcaseId, command.memberId)

        return GetBookcaseResponseDto(
            bookList = bookList.map {
                GetBookcaseResponseDto.Item(
                    it.id,
                    it.bookcaseId,
                    bookName = it.name,
                )
            },
        )
    }
}
