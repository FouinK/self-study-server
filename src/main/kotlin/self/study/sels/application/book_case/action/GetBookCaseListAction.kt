package self.study.sels.application.book_case.action

import org.springframework.stereotype.Component
import self.study.sels.application.book_case.port.`in`.GetBookCaseListCommand
import self.study.sels.application.book_case.port.`in`.GetBookCaseListUseCase
import self.study.sels.controller.dto.GetBookCaseListResponseDto
import self.study.sels.model.book_case.BookCaseRepository

@Component
class GetBookCaseListAction(
    private val bookCaseRepository: BookCaseRepository,
) : GetBookCaseListUseCase {
    override fun list(command: GetBookCaseListCommand): GetBookCaseListResponseDto {
        val bookCasePage =
            bookCaseRepository.findAllByMemberId(
                memberId = command.memberId,
                pageable = command.pageable,
            )

        return GetBookCaseListResponseDto(
            totalElement = bookCasePage.totalElements,
            page = bookCasePage.number,
            bookCaseList =
                bookCasePage.content.map {
                    GetBookCaseListResponseDto.Item(
                        bookCaseId = it.id,
                        bookCaseName = it.name,
                    )
                },
        )
    }
}
