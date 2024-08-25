package self.study.sels.model.book_case

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookcaseRepository : JpaRepository<Bookcase, Int> {
    fun findAllByMemberId(
        memberId: Int,
        pageable: Pageable,
    ): Page<Bookcase>

    fun existsByMemberIdAndName(
        memberId: Int,
        name: String,
    ): Boolean

    fun findByIdAndMemberId(
        bookcaseId: Int,
        memberId: Int,
    ): Bookcase?
}
