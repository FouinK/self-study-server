package self.study.sels.application.book_case.action

import fixtures.MemberBuilder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.application.book_case.port.`in`.CreateBookCaseCommand
import self.study.sels.application.book_case.port.`in`.CreateBookCaseUseCase
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
    lateinit var member: Member

    @BeforeEach
    fun setUp() {
        createBookCaseUseCase =
            CreateBookCaseAction(
                bookCaseRepository,
            )

        member = memberRepository.save(MemberBuilder().build())
    }

    @Test
    fun `book_case 생성 시 정상 저장 된다`() {
        val bookCaseName = "영단어 암기"
        val createBookCaseCommand =
            CreateBookCaseCommand(
                name = bookCaseName,
                memberId = member.id,
            )
        val bookCaseId = createBookCaseUseCase.create(command = createBookCaseCommand)

        val bookCase =
            bookCaseRepository.findById(bookCaseId)
                .orElseThrow { throw IllegalArgumentException("없는 아이디로 조회 테스트 실패") }

        assertThat(bookCase.id).isEqualTo(bookCaseId)
        assertThat(bookCase.name).isEqualTo(bookCaseName)
        assertThat(bookCase.memberId).isEqualTo(member.id)
    }
}
