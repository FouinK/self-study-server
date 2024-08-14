package self.study.sels.model.book_case

import org.springframework.data.jpa.repository.JpaRepository

interface BookCaseRepository : JpaRepository<BookCase, Int>
