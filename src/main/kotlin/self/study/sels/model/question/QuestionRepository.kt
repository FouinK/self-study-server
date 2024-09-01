package self.study.sels.model.question

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface QuestionRepository : JpaRepository<Question, Int>, QuestionRepositoryCustom {
    @EntityGraph(attributePaths = ["answerList"])
    override fun findById(questionId: Int): Optional<Question>

    fun existsByQuestionAndBookId(
        question: String,
        bookId: Int,
    ): Boolean

    @EntityGraph(attributePaths = ["answerList"])
    fun findByIdAndMemberId(questionId: Int, memberId: Int): Question?
}
