package self.study.sels.application.book_case.action

import self.study.sels.annotation.Action
import self.study.sels.application.book_case.port.`in`.GetBookcaseListCommand
import self.study.sels.application.book_case.port.`in`.GetBookcaseListUseCase
import self.study.sels.controller.dto.GetBookcaseListResponseDto
import self.study.sels.model.book_case.BookcaseRepository

@Action
class GetBookcaseListAction(
    private val bookcaseRepository: BookcaseRepository,
) : GetBookcaseListUseCase {
    override fun list(
        command: GetBookcaseListCommand
    ): GetBookcaseListResponseDto {
        val bookcasePage = bookcaseRepository.findAllByMemberId(
            memberId = command.memberId,
            pageable = command.pageable,
        )

        return GetBookcaseListResponseDto(
            totalElement = bookcasePage.totalElements,
            page = bookcasePage.number,
            bookcaseList = bookcasePage.content.map {
                GetBookcaseListResponseDto.Item(
                    bookCaseId = it.id,
                    bookcaseName = it.name,
                )
            },
        )
    }
}
