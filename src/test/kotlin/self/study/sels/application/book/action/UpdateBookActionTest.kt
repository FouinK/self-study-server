package self.study.sels.application.book.action

import fixtures.BookBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import self.study.sels.application.book.port.`in`.UpdateBookUseCase
import self.study.sels.controller.dto.UpdateBookRequestDto
import self.study.sels.exception.ExistsNameException
import self.study.sels.model.book.Book
import self.study.sels.model.book.BookRepository

@SpringBootTest
class UpdateBookActionTest(
    @Autowired val bookRepository: BookRepository,
) {
    lateinit var updateBookUseCase: UpdateBookUseCase
    lateinit var book: Book
    lateinit var existsName: String
    lateinit var updateBeforeName: String

    @BeforeEach
    fun setUp() {
        updateBookUseCase =
            UpdateBookAction(
                bookRepository,
            )

        existsName = "이미 존재하는 이름"
        updateBeforeName = "수정 전 이름"

        bookRepository.save(
            BookBuilder(
                name = existsName,
                memberId = 1,
                bookcaseId = 1,
            ).build(),
        )
        book =
            bookRepository.save(
                BookBuilder(
                    name = updateBeforeName,
                    memberId = 1,
                    bookcaseId = 1,
                ).build(),
            )
    }

    @Test
    fun `책 이름을 정상 수정한다`() {
        // given
        val updateName = "수정 될 이름"
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

    @Test
    fun `이미 존재하는 이름으로 수정 시 예외가 발생한다`() {
        // given
        val updateName = existsName
        val command =
            UpdateBookRequestDto(
                bookId = book.id,
                name = updateName,
            )

        // when & then
        assertThrows<ExistsNameException> {
            updateBookUseCase.update(command)
        }.message.apply { assertThat(this).isEqualTo("이미 존재하는 책 이름으로는 변경 할 수 없습니다.") }
    }
}
