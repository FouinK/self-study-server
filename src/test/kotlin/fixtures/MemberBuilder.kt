package fixtures

import self.study.sels.model.member.Member

class MemberBuilder(
    val authToken: String? = "auth_token",
    val pushYn: Boolean = true,
    val marketingYn: Boolean = true,
    val appVersion: Int? = 1,
    val os: String? = "ios",
    val osVersion: String? = "11.7",
) {
    fun build() =
        Member(
            authToken = authToken,
            pushYn = pushYn,
            marketingYn = marketingYn,
            appVersion = appVersion,
            os = os,
            osVersion = osVersion,
        )
}
