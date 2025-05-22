package self.study.sels.userapi.bookcase.application.action

import jakarta.validation.constraints.NotBlank

class CreateBookcaseRequestDto(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val color: String,
)

class CreateBookcaseResponseDto(
    val bookcaseId: Int,
)
