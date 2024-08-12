@file:Suppress("ktlint:standard:no-wildcard-imports")

package self.study.sels.model

import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member() : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0
}
