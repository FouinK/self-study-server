package self.study.sels.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import self.study.sels.auth.MemberInfo
import self.study.sels.model.Member
import self.study.sels.repository.MemberRepository

@Component
class UpdateAppInfoInterceptor(
    val memberRepository: MemberRepository,
//    val userSettingFactory: UserSettingFactory,
    private val memberInfo: MemberInfo,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        if (memberInfo.authToken != null) {
            updateUserInfo()
        }

        return true
    }

    private fun updateUserInfo() {
//        val setting: UserSetting = userSettingFactory[memberInfo.os] ?: throw InvalidStatusException()

//        if (memberInfo.appVersion != 1) {
//            if (memberInfo.appVersion < setting.version_code_critical) {
//                throw AppUpdateNeededException()
//            }
//        }

        val member: Member? = memberRepository.findById(memberInfo.memberId).orElse(null)

        if (member != null) {
            memberInfo.appVersionUpdated = memberInfo.appVersion != member.appVersion
            member.updateMemberInfo(
                os = memberInfo.os,
                osVersion = memberInfo.osVersion,
                appVersion = memberInfo.appVersion,
            )
            memberRepository.save(member)
        }
    }

    private fun updateManagerInfo() {
    }
}
