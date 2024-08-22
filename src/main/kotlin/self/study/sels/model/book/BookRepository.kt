package self.study.sels.model.book

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface BookRepository : JpaRepository<Book, Int> {
    fun findAllByBookcaseIdAndMemberId(
        bookcaseId: Int,
        memberId: Int,
    ): List<Book>

    @EntityGraph(attributePaths = ["questionList"])
    override fun findById(bookId: Int): Optional<Book>

    fun findByIdOrderByIdDesc(bookId: Int): Book?
}
