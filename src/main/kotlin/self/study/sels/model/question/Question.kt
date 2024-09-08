package self.study.sels.model.question

import jakarta.persistence.*
import self.study.sels.exception.NotFoundException
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
        set(list) {
            list.forEach { it.question = this }

            field.clear()
            field.addAll(list)

            val correctAnswer = list.find { it.correctYn }

            if (correctAnswer != null) {
                this.answerId = correctAnswer.id
            } else {
                throw NotFoundException("질문에 대한 답 리스트가 존재하는데 정답이 없습니다.")
            }
        }

    fun updateQuestion(question: String) {
        this.question = question
    }

    fun updateAnswerList(
        changeAnswerList: List<Answer>
    ) {
        val changeAnswerIdList = changeAnswerList.map { it.id }
        this.answerList.removeIf { changeAnswerIdList.contains(it.id) }
        this.answerList.addAll(changeAnswerList)
    }

    fun addAnswerList(
        addAnswerList: List<Answer>
    ) {
        this.answerList.addAll(addAnswerList)
    }
}
