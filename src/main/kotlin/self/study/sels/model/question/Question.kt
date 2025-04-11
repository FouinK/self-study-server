package self.study.sels.model.question

import jakarta.persistence.*
import self.study.sels.model.BaseTimeEntity
import self.study.sels.model.answer.Answer

@Entity
@Table(name = "question")
class Question(
    memberId: Int,
    bookId: Int,
    question: String,
    answerId: Int? = null,
    answerList: List<Answer> = listOf(),
    questionType: QuestionType = QuestionType.CHOICE,
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

    @Column(name = "answer_id", nullable = true)
    var answerId = answerId
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = [CascadeType.ALL], orphanRemoval = true)
    var answerList: MutableList<Answer> = answerList.toMutableList()
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false, columnDefinition = "enum('SHORT','CHOICE')")
    var questionType: QuestionType = questionType
        protected set

    fun updateQuestion(question: String) {
        this.question = question
    }

    fun updateAnswerList(
        changeAnswerList: List<Answer>
    ) {
        changeAnswerList.forEach { it.question = this }

        this.answerList.clear()
        this.answerList.addAll(changeAnswerList)

        check(this.answerList.isNotEmpty()) { "질문에 대한 답 리스트를 작성해주세요." }
        check(changeAnswerList.any { it.correctYn }) { "질문에 대한 답 리스트가 존재하는데 정답이 없습니다." }

        this.answerId = changeAnswerList.find { it.correctYn }!!.id
        this.questionType = if (this.answerList.size > 1) {
            QuestionType.CHOICE
        } else {
            QuestionType.SHORT
        }
    }

    fun isShort() = this.questionType == QuestionType.SHORT
}
