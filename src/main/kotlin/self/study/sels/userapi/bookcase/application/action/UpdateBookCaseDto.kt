package self.study.sels.userapi.bookcase.application.action

class UpdateBookcaseRequestDto(
    val bookcaseId: Int,
    val name: String,
)

class UpdateBookcaseResponseDto(
    val name: String,
)
