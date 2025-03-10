package self.study.sels.model.bookresulthistory

import jakarta.persistence.*
import self.study.sels.model.BaseTimeEntity

@Entity
@Table(name = "book_result_history")
class BookResultHistory(
    bookId: Int,
    memberId: Int,
    bookcaseId: Int,
    name: String,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0

    @Column(name = "book_id", nullable = false)
    var bookId = bookId
        protected set

    @Column(name = "member_id", nullable = false)
    var memberId = memberId
        protected set

    @Column(name = "bookcase_id", nullable = false)
    var bookcaseId = bookcaseId
        protected set

    @Column(name = "name", nullable = false, length = 100)
    var name = name
        protected set
}
