package self.study.sels.model.question

import jakarta.persistence.*
import self.study.sels.model.BaseTimeEntity

@Entity
@Table(name = "question")
class Question(
    memberId: Int,
    bookId: Int,
    question: String,
    multipleChoiceYn: Boolean,
    answerId: Int? = null,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0

    @Column(name = "member_id", nullable = false)
    var memberId = memberId
        protected set

    @Column(name = "book_id", nullable = false)
    var bookId = bookId
        protected set

    @Column(name = "question", nullable = false, length = 255)
    var question = question
        protected set

    @Column(name = "multiple_choice_yn", nullable = false)
    var multipleChoiceYn = multipleChoiceYn
        protected set

    @Column(name = "answer_id", nullable = true)
    var answerId = answerId
        protected set
}
