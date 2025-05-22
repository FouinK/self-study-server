package self.study.sels.userapi.bookcase.application.action

import self.study.sels.annotation.Action
import self.study.sels.model.bookcase.BookcaseRepository
import self.study.sels.userapi.bookcase.application.port.`in`.GetBookcaseListCommand
import self.study.sels.userapi.bookcase.application.port.`in`.GetBookcaseListUseCase

@Action
class GetBookcaseListAction(
    private val bookcaseRepository: BookcaseRepository,
) : GetBookcaseListUseCase {
    override fun list(
        command: GetBookcaseListCommand,
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
                    bookcaseId = it.id,
                    bookcaseName = it.name,
                    bookcaseColor = it.color,
                )
            },
        )
    }
}
