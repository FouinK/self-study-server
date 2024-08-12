package self.study.sels.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import self.study.sels.auth.MemberInfo
import self.study.sels.model.Member
import self.study.sels.repository.MemberRepository

@Component
class UserAuthInterceptor(
    private val memberRepository: MemberRepository,
    private val memberInfo: MemberInfo,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        if (request.method == HttpMethod.OPTIONS.name()) {
            return true
        }

        val authToken: String? = request.getHeader("auth-token") ?: request.getParameter("temp_str")
        val os = request.getHeader("os") ?: request.getParameter("os")
        val appVersion: Int =
            ((request.getHeader("app-version") ?: request.getParameter("app_version")) ?: "0").toInt()
        val osVersion: String? =
            request.getHeader("os-version") ?: request.getParameter("os_version")
        val settingRevision: Int =
            ((request.getHeader("setting-revision") ?: request.getParameter("setting_revision")) ?: "0").toInt()

        if (os.isNullOrEmpty()) {
            throw Exception() // TODO : 여기 kona에 있는거 그대로 정의하기
        }
        if (authToken.isNullOrEmpty()) {
            throw Exception() // TODO : 여기 kona에 있는거 그대로 정의하기
        }
        val member: Member =
            memberRepository.findByAuthToken(authToken)
                ?: throw Exception() // TODO : 여기 kona에 있는거 그대로 정의하기

        memberInfo.init(
            userId = member.id,
            settingRevision = settingRevision,
            appVersion = appVersion,
            authToken = authToken,
            os = os,
            osVersion = osVersion,
            appVersionUpdated = false,
            createdAt = member.createdAt,
            pushYn = member.pushYn,
            marketingYn = member.marketingYn,
        )
        MDC.put("userId", member.id.toString())
        return true
    }
}
