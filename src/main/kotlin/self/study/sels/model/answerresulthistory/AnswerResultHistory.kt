package self.study.sels.model.answerresulthistory

import jakarta.persistence.*
import self.study.sels.model.BaseTimeEntity
import self.study.sels.model.questionresulthistory.QuestionResultHistory

@Entity
@Table(name = "answer_result_history")
class AnswerResultHistory(
    question: QuestionResultHistory,
    answer: String,
    correctYn: Boolean = false,
    selectedYn: Boolean = false,
    memberId: Int,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "question_id",
        referencedColumnName = "question_id",
        nullable = false,
    )
    var question = question

    @Column(name = "answer", nullable = false, length = 255)
    var answer = answer
        protected set

    @Column(name = "correct_yn", nullable = false)
    var correctYn = correctYn
        protected set

    @Column(name = "selected_yn", nullable = false)
    var selectedYn = selectedYn
        protected set

    @Column(name = "member_id", nullable = false)
    var memberId = memberId
        protected set
}
