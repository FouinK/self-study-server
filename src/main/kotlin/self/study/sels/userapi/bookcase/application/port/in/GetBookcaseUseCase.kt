package self.study.sels.userapi.bookcase.application.port.`in`

import self.study.sels.userapi.bookcase.application.action.GetBookcaseResponseDto

interface GetBookcaseUseCase {
    fun detail(command: GetBookcaseCommand): GetBookcaseResponseDto
}

class GetBookcaseCommand(
    val bookcaseId: Int,
    val memberId: Int,
)
