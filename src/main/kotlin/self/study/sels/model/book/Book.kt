@file:Suppress("ktlint:standard:no-wildcard-imports")

package self.study.sels.model.book

import jakarta.persistence.*
import self.study.sels.model.BaseTimeEntity
import self.study.sels.model.question.Question

@Entity
@Table(name = "book")
class Book(
    memberId: Int,
    bookcaseId: Int,
    name: String,
    questionList: List<Question> = listOf(),
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0

    @Column(name = "member_id", nullable = false)
    var memberId = memberId
        protected set

    @Column(name = "bookcase_id", nullable = false)
    var bookcaseId = bookcaseId
        protected set

    @Column(name = "name", nullable = false, length = 100)
    var name = name
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = [CascadeType.ALL], orphanRemoval = true)
    var questionList: MutableList<Question> = questionList.toMutableList()
        set(list) {
            list.forEach { it.book }
            field.clear()
            field.addAll(list)
        }
}
