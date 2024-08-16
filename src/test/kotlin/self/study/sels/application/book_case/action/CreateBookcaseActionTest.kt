package self.study.sels.application.book_case.action

import fixtures.BookcaseBuilder
import fixtures.MemberBuilder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.application.book_case.port.`in`.CreateBookcaseCommand
import self.study.sels.application.book_case.port.`in`.CreateBookcaseUseCase
import self.study.sels.model.book_case.Bookcase
import self.study.sels.model.book_case.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import java.lang.IllegalArgumentException

@SpringBootTest
class CreateBookcaseActionTest(
    @Autowired val bookcaseRepository: BookcaseRepository,
    @Autowired val memberRepository: MemberRepository,
) {
    lateinit var createBookcaseUseCase: CreateBookcaseUseCase
    lateinit var defaultMember: Member
    lateinit var defaultBookcase: Bookcase

    @BeforeEach
    fun setUp() {
        createBookcaseUseCase =
            CreateBookcaseAction(
                bookcaseRepository,
            )

        defaultMember = memberRepository.save(MemberBuilder().build())
        defaultBookcase =
            bookcaseRepository.save(
                BookcaseBuilder(
                    name = "국어",
                    memberId = defaultMember.id,
                ).build(),
            )
    }

    @Test
    fun `bookcase 생성 시 정상 저장 된다`() {
        // given
        val bookcaseName = "영어"
        val createBookCaseCommand =
            CreateBookcaseCommand(
                name = bookcaseName,
                memberId = defaultMember.id,
            )

        // when
        val bookcaseId = createBookcaseUseCase.create(command = createBookCaseCommand)

        // then
        val bookcase =
            bookcaseRepository.findById(bookcaseId)
                .orElseThrow { throw IllegalArgumentException("없는 아이디로 조회 테스트 실패") }

        assertThat(bookcase.id).isEqualTo(bookcaseId)
        assertThat(bookcase.name).isEqualTo(bookcaseName)
        assertThat(bookcase.memberId).isEqualTo(defaultMember.id)
    }

    @Test
    fun `이미 사용중인 이름으로 또 생성 시 예외가 발생한다`() {
        // given
        val createBookcaseCommand =
            CreateBookcaseCommand(
                name = defaultBookcase.name,
                memberId = defaultMember.id,
            )

        // when & then
        assertThrows<Exception> {
            createBookcaseUseCase.create(command = createBookcaseCommand)
        }.message.apply { assertThat("이미 사용중인 이름입니다.") }
    }
}
