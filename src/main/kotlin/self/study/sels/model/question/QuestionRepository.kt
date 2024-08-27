package self.study.sels.model.question

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import self.study.sels.model.book.Book
import java.util.Optional

interface QuestionRepository : JpaRepository<Question, Int>, QuestionRepositoryCustom {
    @EntityGraph(attributePaths = ["answerList"])
    override fun findById(questionId: Int): Optional<Question>

    fun existsByQuestionAndBook(
        question: String,
        book: Book,
    ): Boolean

    @EntityGraph(attributePaths = ["answerList"])
    fun findByIdAndMemberId(questionId: Int, memberId: Int): Question?
}
