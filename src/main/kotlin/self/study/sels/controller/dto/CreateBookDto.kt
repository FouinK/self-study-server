package self.study.sels.controller.dto

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull

class CreateBookRequestDto(
    @field:NotNull
    val bookcaseId: Int,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val color: String,
)

class CreateBookResponseDto(
    val bookId: Int,
)
