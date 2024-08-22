package self.study.sels.application.book.action

import fixtures.BookBuilder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import self.study.sels.application.book.port.`in`.UpdateBookUseCase
import self.study.sels.controller.dto.UpdateBookRequestDto
import self.study.sels.model.book.Book
import self.study.sels.model.book.BookRepository

@SpringBootTest
class UpdateBookActionTest(
    @Autowired val bookRepository: BookRepository,
) {
    lateinit var updateBookUseCase: UpdateBookUseCase
    lateinit var book: Book

    @BeforeEach
    fun setUp() {
        updateBookUseCase =
            UpdateBookAction(
                bookRepository,
            )

        book =
            bookRepository.save(
                BookBuilder(
                    name = "1단원",
                    memberId = 1,
                    bookcaseId = 1,
                ).build(),
            )
    }

    @Test
    fun `책 이름을 정상 수정한다`() {
        // given
        val updateName = "수정된 이름"
        val command =
            UpdateBookRequestDto(
                bookId = book.id,
                name = updateName,
            )

        // when
        val updatedName =
            updateBookUseCase.update(
                command,
            )

        // then
        book = bookRepository.findByIdOrNull(book.id)!!
        assertThat(book.name).isEqualTo(updatedName)
    }
}
