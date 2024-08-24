package self.study.sels.application.book.action

import fixtures.BookBuilder
import fixtures.BookcaseBuilder
import fixtures.MemberBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import self.study.sels.application.book.port.`in`.UpdateBookCommand
import self.study.sels.application.book.port.`in`.UpdateBookUseCase
import self.study.sels.exception.ExistsNameException
import self.study.sels.exception.NotFoundException
import self.study.sels.model.book.Book
import self.study.sels.model.book.BookRepository
import self.study.sels.model.book_case.Bookcase
import self.study.sels.model.book_case.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository

@SpringBootTest
class UpdateBookActionTest(
    @Autowired val bookRepository: BookRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val bookcaseRepository: BookcaseRepository,
) {
    lateinit var updateBookUseCase: UpdateBookUseCase
    lateinit var book: Book
    lateinit var otherMemberBook: Book
    lateinit var existsName: String
    lateinit var updateBeforeName: String
    lateinit var member: Member
    lateinit var otherMember: Member
    lateinit var bookcase: Bookcase
    lateinit var otherMemberBookcase: Bookcase

    @BeforeEach
    fun setUp() {
        updateBookUseCase =
            UpdateBookAction(
                bookRepository,
            )

        existsName = "이미 존재하는 이름"
        updateBeforeName = "수정 전 이름"

        member = memberRepository.save(MemberBuilder().build())
        otherMember = memberRepository.save(MemberBuilder().build())

        bookcase =
            bookcaseRepository.save(
                BookcaseBuilder(
                    name = "책장",
                    memberId = member.id,
                ).build(),
            )
        otherMemberBookcase =
            bookcaseRepository.save(
                BookcaseBuilder(
                    name = "책장",
                    memberId = otherMember.id,
                ).build(),
            )

        bookRepository.save(
            BookBuilder(
                name = existsName,
                memberId = member.id,
                bookcaseId = bookcase.id,
            ).build(),
        )
        book =
            bookRepository.save(
                BookBuilder(
                    name = updateBeforeName,
                    memberId = member.id,
                    bookcaseId = bookcase.id,
                ).build(),
            )

        otherMemberBook =
            bookRepository.save(
                BookBuilder(
                    name = "책이름",
                    memberId = otherMember.id,
                    bookcaseId = otherMemberBookcase.id,
                ).build(),
            )
    }

    @Test
    fun `책 이름을 정상 수정한다`() {
        // given
        val updateName = "수정 될 이름"
        val command =
            UpdateBookCommand(
                bookId = book.id,
                name = updateName,
                memberId = member.id,
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
            UpdateBookCommand(
                bookId = book.id,
                name = updateName,
                memberId = member.id,
            )

        // when & then
        assertThrows<ExistsNameException> {
            updateBookUseCase.update(command)
        }.message.apply { assertThat(this).isEqualTo("이미 존재하는 책 이름으로는 변경 할 수 없습니다.") }
    }

    @Test
    fun `내 책이 아닌 책을 수정할 경우 예외가 발생한다`() {
        // given
        val command =
            UpdateBookCommand(
                bookId = otherMemberBook.id,
                name = "책이름 수정하기",
                memberId = member.id,
            )

        // when && then
        assertThrows<NotFoundException> {
            updateBookUseCase.update(command)
        }.message.apply { assertThat(this).isEqualTo("책이 없습니다.") }
    }

    @Test
    fun `내 책이 아닌 책을 수정할 경우 예외가 발생한다2`() {
        // given
        val command =
            UpdateBookCommand(
                bookId = book.id,
                name = "책이름 수정하기",
                memberId = otherMember.id,
            )

        // when && then
        assertThrows<NotFoundException> {
            updateBookUseCase.update(command)
        }.message.apply { assertThat(this).isEqualTo("책이 없습니다.") }
    }
}
