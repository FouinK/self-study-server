@file:Suppress("ktlint:standard:no-wildcard-imports")

package self.study.sels.model

import jakarta.persistence.*

@Entity
@Table(name = "book_case")
class BookCase(
    memberId: Int,
    name: String,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0

    @Column(name = "member_id", nullable = false)
    var memberId = memberId
        protected set

    @Column(name = "name", nullable = false, length = 100)
    var name = name
        protected set
}
