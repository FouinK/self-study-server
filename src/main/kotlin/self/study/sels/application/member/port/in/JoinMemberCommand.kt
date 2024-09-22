package self.study.sels.application.member.port.`in`

class JoinMemberCommand(
    val platForm: PlatForm,
    val code: String,
)

enum class PlatForm {
    KAKAO,
    NAVER,
    GOOGLE,
}
