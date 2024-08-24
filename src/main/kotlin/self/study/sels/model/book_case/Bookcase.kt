@file:Suppress("ktlint:standard:no-wildcard-imports")

package self.study.sels.model.book_case

import jakarta.persistence.*
import self.study.sels.model.BaseTimeEntity

@Entity
@Table(name = "bookcase")
class Bookcase(
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

    fun updateName(name: String) {
        this.name = name
    }
}
