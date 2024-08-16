package self.study.sels.application.book_case.action

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.application.book_case.port.`in`.GetBookCaseListUseCase
import self.study.sels.model.book_case.BookCaseRepository

@SpringBootTest
class CreateBookCaseActionTest(
    @Autowired val bookCaseRepository: BookCaseRepository,
) {
    lateinit var getBookCaseListUseCase: GetBookCaseListUseCase

    @BeforeEach
    fun setUp() {
        getBookCaseListUseCase =
            GetBookCaseListAction(
                bookCaseRepository,
            )
    }

    @Test
    fun `여기에작성`() {
    }
}
