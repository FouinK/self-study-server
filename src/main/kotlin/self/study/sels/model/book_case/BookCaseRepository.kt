package self.study.sels.model.book_case

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookCaseRepository : JpaRepository<BookCase, Int> {
    fun findAllByMemberId(
        memberId: Int,
        pageable: Pageable,
    ): Page<BookCase>

    fun existsByMemberIdAndName(
        memberId: Int,
        name: String,
    ): Boolean
}
