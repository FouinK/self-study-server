package self.study.sels.application.book_case.action

import fixtures.BookBuilder
import fixtures.BookcaseBuilder
import fixtures.MemberBuilder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.application.book_case.port.`in`.GetBookcaseCommand
import self.study.sels.application.book_case.port.`in`.GetBookcaseUseCase
import self.study.sels.model.book.BookRepository
import self.study.sels.model.book_case.Bookcase
import self.study.sels.model.book_case.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository

@SpringBootTest
class GetBookcaseActionTest(
    @Autowired val bookRepository: BookRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val bookcaseRepository: BookcaseRepository,
) {
    lateinit var getBookcaseUseCase: GetBookcaseUseCase
    lateinit var member: Member
    lateinit var bookcase: Bookcase
    lateinit var bookName1: String
    lateinit var bookName2: String
    lateinit var bookName3: String

    @BeforeEach
    fun setUp() {
        getBookcaseUseCase =
            GetBookcaseAction(
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

        bookName1 = "1단원"
        bookName2 = "2단원"
        bookName3 = "3단원"

        bookRepository.saveAll(
            listOf(
                BookBuilder(
                    name = bookName1,
                    memberId = member.id,
                    bookcaseId = bookcase.id,
                ).build(),
                BookBuilder(
                    name = bookName2,
                    memberId = member.id,
                    bookcaseId = bookcase.id,
                ).build(),
                BookBuilder(
                    name = bookName3,
                    memberId = member.id,
                    bookcaseId = bookcase.id,
                ).build(),
            ),
        )
    }

    @Test
    fun `bookcase 상세 조회했을 떄 book 들이 정상 조회 된다`() {
        // given
        val command =
            GetBookcaseCommand(
                bookcaseId = bookcase.id,
                memberId = member.id,
            )

        // when
        val getBookcaseResponseDto = getBookcaseUseCase.detail(command)

        // then
        val actualBookcaseNames = getBookcaseResponseDto.bookList.map { it.bookName }

        assertThat(getBookcaseResponseDto.bookList.size).isEqualTo(3)
        assertThat(actualBookcaseNames).containsExactlyInAnyOrder(
            bookName1,
            bookName2,
            bookName3,
        )
    }
}
