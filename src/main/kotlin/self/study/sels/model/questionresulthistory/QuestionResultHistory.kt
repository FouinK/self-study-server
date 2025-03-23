package self.study.sels.model.questionresulthistory

import jakarta.persistence.*
import self.study.sels.model.BaseTimeEntity
import self.study.sels.model.answerresulthistory.AnswerResultHistory
import self.study.sels.model.question.QuestionType

@Entity
@Table(name = "question_result_history")
class QuestionResultHistory(
    questionId: Int,
    memberId: Int,
    bookId: Int,
    question: String,
    answerId: Int? = null,
    answerList: List<AnswerResultHistory> = listOf(),
    questionType: QuestionType = QuestionType.CHOICE,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0

    @Column(name = "question_id", nullable = false)
    var questionId = questionId
        protected set

    @Column(name = "member_id", nullable = false)
    var memberId = memberId
        protected set

    @Column(name = "book_id", nullable = false)
    var bookId = bookId
        protected set

    @Column(name = "question", nullable = false, length = 255)
    var question = question
        protected set

    @Column(name = "answer_id", nullable = true)
    var answerId = answerId
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = [CascadeType.ALL], orphanRemoval = true)
    var answerResultHistoryList: MutableList<AnswerResultHistory> = answerList.toMutableList()
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false, columnDefinition = "enum('SHORT','CHOICE')")
    var questionType: QuestionType = questionType
        protected set

    fun updateAnswerResultHistoryList(answerResultHistories: List<AnswerResultHistory>) {
        this.answerResultHistoryList = answerResultHistories.toMutableList()
    }
}
