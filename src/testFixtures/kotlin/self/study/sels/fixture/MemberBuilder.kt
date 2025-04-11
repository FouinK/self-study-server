package self.study.sels.fixture

import self.study.sels.model.member.Member

class MemberBuilder(
    val authToken: String? = "auth_token",
    val pushYn: Boolean = true,
    val marketingYn: Boolean = true,
    val appVersion: Int? = 1,
    val os: String? = "ios",
    val osVersion: String? = "11.7",
    val phone: String = "01099999999"
) {
    fun build() =
        Member(
            authToken = authToken,
            pushYn = pushYn,
            marketingYn = marketingYn,
            appVersion = appVersion,
            os = os,
            osVersion = osVersion,
            phone = phone,
        )
}
