package self.study.sels.model.answer

import jakarta.persistence.*
import self.study.sels.model.BaseTimeEntity
import self.study.sels.model.question.Question

@Entity
@Table(name = "answer")
class Answer(
    question: Question,
    answer: String,
    correctYn: Boolean = false,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    var question = question

    @Column(name = "answer", nullable = false, length = 255)
    var answer = answer
        protected set

    @Column(name = "correct_yn", nullable = false)
    var correctYn = correctYn
        protected set
}
