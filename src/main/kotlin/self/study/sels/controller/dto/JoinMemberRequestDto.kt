package self.study.sels.controller.dto

class JoinMemberRequestDto(
    val platForm: PlatForm,
    val redirectUri: String,
)

enum class PlatForm {
    KAKAO,
    NAVER,
    GOOGLE,
}
