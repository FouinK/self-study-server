package self.study.sels.model.book

import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Int> {
    fun findAllByBookcaseIdAndMemberId(
        bookcaseId: Int,
        memberId: Int,
    ): List<Book>
}
