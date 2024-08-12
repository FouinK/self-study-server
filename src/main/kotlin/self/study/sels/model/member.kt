@file:Suppress("ktlint:standard:no-wildcard-imports")

package self.study.sels.model

import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(
    authToken: String? = null,
    pushYn: Boolean,
    marketingYn: Boolean,
    appVersion: Int? = null,
    os: String? = null,
    osVersion: String? = null,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0

    @Column(name = "auth_token", nullable = true, length = 255)
    var authToken = authToken
        protected set

    @Column(name = "push_yn", nullable = false)
    var pushYn = pushYn
        protected set

    @Column(name = "marketing_yn", nullable = false)
    var marketingYn = marketingYn
        protected set

    @Column(name = "app_version", nullable = false)
    var appVersion = appVersion
        protected set

    @Column(name = "os", nullable = false, length = 10)
    var os = os
        protected set

    @Column(name = "os_version", nullable = false, length = 10)
    var osVersion = osVersion
        protected set

    fun updateMemberInfo(
        os: String,
        osVersion: String?,
        appVersion: Int?,
    ) {
        this.os = os
        this.osVersion = osVersion
        this.appVersion = appVersion
    }
}
