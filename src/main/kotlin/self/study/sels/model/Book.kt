@file:Suppress("ktlint:standard:no-wildcard-imports")

package self.study.sels.model

import jakarta.persistence.*

@Entity
@Table(name = "book")
class Book(
    memberId: Int,
    bookCaseId: Int,
    name: String,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0

    @Column(name = "member_id", nullable = false)
    var memberId = memberId
        protected set

    @Column(name = "book_case_id", nullable = false)
    var bookCaseId = bookCaseId
        protected set

    @Column(name = "name", nullable = false, length = 100)
    var name = name
        protected set
}
