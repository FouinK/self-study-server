package self.study.sels.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

class AuthenticationMemberRequestDto(
    @NotBlank
    @field:Pattern(regexp = "^010\\d{8}$", message = "전화번호 형식이 올바르지 않습니다.")
    val phone: String
)
