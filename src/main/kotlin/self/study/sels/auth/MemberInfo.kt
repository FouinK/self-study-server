package self.study.sels.auth

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class MemberInfo {
    var memberId: Int = 0
    var authToken: String? = null
    var os: String = ""
    var appVersion: Int = 0
    var osVersion: String? = null
    var appVersionUpdated: Boolean = false
    var newbieYn: Boolean = false
    var createdAt: LocalDateTime = LocalDateTime.now()
    var pushYn: Boolean = false
    var marketingYn: Boolean = false

    fun init(
        userId: Int,
        authToken: String?,
        os: String,
        appVersion: Int,
        osVersion: String?,
        appVersionUpdated: Boolean = false,
        newbieYn: Boolean = false,
        createdAt: LocalDateTime,
        pushYn: Boolean,
        marketingYn: Boolean,
    ) {
        this.memberId = userId
        this.authToken = authToken
        this.os = os
        this.appVersion = appVersion
        this.osVersion = osVersion
        this.appVersionUpdated = appVersionUpdated
        this.newbieYn = newbieYn
        this.createdAt = createdAt
        this.pushYn = pushYn
        this.marketingYn = marketingYn
    }
}
