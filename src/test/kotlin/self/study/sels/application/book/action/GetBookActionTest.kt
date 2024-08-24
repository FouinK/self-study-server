package self.study.sels.application.book.action

import fixtures.BookBuilder
import fixtures.BookcaseBuilder
import fixtures.MemberBuilder
import fixtures.QuestionBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.application.book.port.`in`.GetBookCommand
import self.study.sels.application.book.port.`in`.GetBookUseCase
import self.study.sels.model.book.Book
import self.study.sels.model.book.BookRepository
import self.study.sels.model.book_case.Bookcase
import self.study.sels.model.book_case.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import self.study.sels.model.question.QuestionRepository

@SpringBootTest
class GetBookActionTest(
    @Autowired val questionRepository: QuestionRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val bookcaseRepository: BookcaseRepository,
    @Autowired val bookRepository: BookRepository,
) {
    lateinit var member: Member
    lateinit var otherMember: Member
    lateinit var bookcase: Bookcase
    lateinit var otherMemberBookcase: Bookcase
    lateinit var book: Book
    lateinit var otherMemberBook: Book
    lateinit var questionString1: String
    lateinit var questionString2: String
    lateinit var questionString3: String
    lateinit var questionString4: String
    lateinit var getBookUseCase: GetBookUseCase

    @BeforeEach
    fun setUp() {
        getBookUseCase = GetBookAction(bookRepository)

        member = memberRepository.save(MemberBuilder().build())
        otherMember = memberRepository.save(MemberBuilder().build())

        bookcase =
            bookcaseRepository.save(
                BookcaseBuilder(
                    name = "역사",
                    memberId = member.id,
                ).build(),
            )
        otherMemberBookcase =
            bookcaseRepository.save(
                BookcaseBuilder(
                    name = "역사",
                    memberId = otherMember.id,
                ).build(),
            )

        book =
            bookRepository.save(
                BookBuilder(
                    name = "1단원",
                    memberId = member.id,
                    bookcaseId = bookcase.id,
                ).build(),
            )

        otherMemberBook =
            bookRepository.save(
                BookBuilder(
                    name = "1단원",
                    memberId = otherMember.id,
                    bookcaseId = otherMemberBookcase.id,
                ).build(),
            )

        questionString1 = "질문1"
        questionString2 = "질문2"
        questionString3 = "질문3"
        questionString4 = "질문4"

        questionRepository.saveAll(
            listOf(
                QuestionBuilder(
                    memberId = member.id,
                    book = book,
                    question = questionString1,
                ).build(),
                QuestionBuilder(
                    memberId = member.id,
                    book = book,
                    question = questionString2,
                ).build(),
                QuestionBuilder(
                    memberId = member.id,
                    book = book,
                    question = questionString3,
                ).build(),
                QuestionBuilder(
                    memberId = member.id,
                    book = book,
                    question = questionString4,
                ).build(),
            ),
        )
    }

    @Test
    fun `책 상세를 정상 조회한다`() {
        // given
        val command =
            GetBookCommand(
                bookId = book.id,
                memberId = member.id,
            )

        // when
        val getBookResponseDto = getBookUseCase.detail(command)

        // then
        assertThat(getBookResponseDto.bookName).isEqualTo(book.name)
        assertThat(getBookResponseDto.questionList.size).isEqualTo(4)

        val actualBookcaseNames = getBookResponseDto.questionList.map { it.question }
        assertThat(actualBookcaseNames).containsExactly(
            questionString1,
            questionString2,
            questionString3,
            questionString4,
        )
    }

    @Test
    fun `생성되지 않은 책 ID로 조회 시 예외가 발생한다`() {
        // given
        val command =
            GetBookCommand(
                bookId = Int.MAX_VALUE - 10,
                memberId = member.id,
            )

        // when & then
        assertThrows<Exception> {
            getBookUseCase.detail(command)
        }.message.apply { assertThat(this).isEqualTo("책이 없습니다.") }
    }

    @Test
    fun `내 책이 아닌 책을 조회할 경우 예외가 발생한다`() {
        // given
        val command =
            GetBookCommand(
                bookId = book.id,
                memberId = otherMember.id,
            )

        // when & then
        assertThrows<Exception> {
            getBookUseCase.detail(command)
        }.message.apply { assertThat(this).isEqualTo("책이 없습니다.") }
    }
}
