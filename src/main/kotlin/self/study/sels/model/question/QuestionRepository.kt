package self.study.sels.model.question

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, Int>, QuestionRepositoryCustom {
    fun existsByQuestionAndBookId(
        question: String,
        bookId: Int,
    ): Boolean

    @EntityGraph(attributePaths = ["answerList"])
    fun findByIdAndMemberId(questionId: Int, memberId: Int): Question?

    fun findAllByMemberId(memberId: Int): List<Question>
}
