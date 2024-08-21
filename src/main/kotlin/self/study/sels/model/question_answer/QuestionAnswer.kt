package self.study.sels.model.question_answer

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import self.study.sels.model.BaseTimeEntity

@Entity
@Table(name = "question_answer")
class QuestionAnswer(
    memberId: Int,
    bookId: Int,
    question: String,
    answer: String? = null,
    multipleChoiceYn: Boolean,
    multipleChoiceAnswerId: Int? = null,
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

    @Column(name = "answer", nullable = true, length = 255)
    var answer = answer
        protected set

    @Column(name = "multiple_choice_yn", nullable = false)
    var multipleChoiceYn = multipleChoiceYn
        protected set

    @Column(name = "multiple_choice_answer_id", nullable = true)
    var multipleChoiceAnswerId = multipleChoiceAnswerId
        protected set
}
