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

    @EntityGraph(attributePaths = ["questionList"])
    fun findByIdAndMemberId(
        bookId: Int,
        memberId: Int,
    ): Book?

    fun findByIdOrderByIdDesc(bookId: Int): Book?

    fun existsByMemberIdAndName(
        memberId: Int,
        name: String,
    ): Boolean
}
