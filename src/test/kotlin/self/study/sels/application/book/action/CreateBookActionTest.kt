package self.study.sels.application.book.action

import fixtures.BookcaseBuilder
import fixtures.MemberBuilder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import self.study.sels.application.book.port.`in`.CreateBookCommand
import self.study.sels.application.book.port.`in`.CreateBookUseCase
import self.study.sels.model.book.BookRepository
import self.study.sels.model.book_case.Bookcase
import self.study.sels.model.book_case.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository

@SpringBootTest
class CreateBookActionTest(
    @Autowired val bookcaseRepository: BookcaseRepository,
    @Autowired val bookRepository: BookRepository,
    @Autowired val memberRepository: MemberRepository,
) {
    lateinit var createBookUseCase: CreateBookUseCase
    lateinit var member: Member
    lateinit var bookcase: Bookcase

    @BeforeEach
    fun setUp() {
        createBookUseCase =
            CreateBookAction(
                bookcaseRepository,
                bookRepository,
            )

        member = memberRepository.save(MemberBuilder().build())
        bookcase =
            bookcaseRepository.save(
                BookcaseBuilder(
                    name = "영어",
                    memberId = member.id,
                ).build(),
            )
    }

    @Test
    fun `book이 정상 저장된다`() {
        // given
        val command =
            CreateBookCommand(
                bookcaseId = bookcase.id,
                name = "1단원",
                memberId = member.id,
            )

        // when
        val bookId = createBookUseCase.create(command)

        // then
        val book = bookRepository.findByIdOrNull(bookId)!!

        assertThat(book.bookcaseId).isEqualTo(command.bookcaseId)
        assertThat(book.name).isEqualTo(command.name)
        assertThat(book.memberId).isEqualTo(command.memberId)
    }
}
