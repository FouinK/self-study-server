package self.study.sels.application.book_case.action

import org.springframework.stereotype.Component
import self.study.sels.application.book_case.port.`in`.GetBookcaseListCommand
import self.study.sels.application.book_case.port.`in`.GetBookcaseListUseCase
import self.study.sels.controller.dto.GetBookcaseListResponseDto
import self.study.sels.model.book_case.BookcaseRepository

@Component
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
