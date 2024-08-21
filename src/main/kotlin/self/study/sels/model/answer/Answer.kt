package self.study.sels.model.answer

import jakarta.persistence.*
import self.study.sels.model.BaseTimeEntity

@Entity
@Table(name = "answer")
class Answer(
    questionId: Int,
    answer: String,
    correctYn: Boolean = false,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0

    @Column(name = "question_id", nullable = false)
    var questionId = questionId
        protected set

    @Column(name = "answer", nullable = false, length = 255)
    var answer = answer
        protected set

    @Column(name = "correct_yn", nullable = false)
    var correctYn = correctYn
        protected set
}
