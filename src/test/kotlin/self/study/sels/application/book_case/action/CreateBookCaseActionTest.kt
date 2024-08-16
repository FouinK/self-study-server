package self.study.sels.application.book_case.action

import fixtures.BookCaseBuilder
import fixtures.MemberBuilder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.application.book_case.port.`in`.CreateBookCaseCommand
import self.study.sels.application.book_case.port.`in`.CreateBookCaseUseCase
import self.study.sels.model.book_case.BookCase
import self.study.sels.model.book_case.BookCaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import java.lang.IllegalArgumentException

@SpringBootTest
class CreateBookCaseActionTest(
    @Autowired val bookCaseRepository: BookCaseRepository,
    @Autowired val memberRepository: MemberRepository,
) {
    lateinit var createBookCaseUseCase: CreateBookCaseUseCase
    lateinit var defaultMember: Member
    lateinit var defaultBookCase: BookCase

    @BeforeEach
    fun setUp() {
        createBookCaseUseCase =
            CreateBookCaseAction(
                bookCaseRepository,
            )

        defaultMember = memberRepository.save(MemberBuilder().build())
        defaultBookCase =
            bookCaseRepository.save(
                BookCaseBuilder(
                    name = "디폴트 책장",
                    memberId = defaultMember.id,
                ).build(),
            )
    }

    @Test
    fun `book_case 생성 시 정상 저장 된다`() {
        // given
        val bookCaseName = "영단어 암기"
        val createBookCaseCommand =
            CreateBookCaseCommand(
                name = bookCaseName,
                memberId = defaultMember.id,
            )

        // when
        val bookCaseId = createBookCaseUseCase.create(command = createBookCaseCommand)

        // then
        val bookCase =
            bookCaseRepository.findById(bookCaseId)
                .orElseThrow { throw IllegalArgumentException("없는 아이디로 조회 테스트 실패") }

        assertThat(bookCase.id).isEqualTo(bookCaseId)
        assertThat(bookCase.name).isEqualTo(bookCaseName)
        assertThat(bookCase.memberId).isEqualTo(defaultMember.id)
    }

    @Test
    fun `이미 사용중인 이름으로 또 생성 시 예외가 발생한다`() {
        // given
        val createBookCaseCommand =
            CreateBookCaseCommand(
                name = defaultBookCase.name,
                memberId = defaultMember.id,
            )

        // when & then
        assertThrows<Exception> {
            createBookCaseUseCase.create(command = createBookCaseCommand)
        }.message.apply { assertThat("이미 사용중인 이름입니다.") }
    }
}
