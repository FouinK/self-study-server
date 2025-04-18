package self.study.sels.controller.dto

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
