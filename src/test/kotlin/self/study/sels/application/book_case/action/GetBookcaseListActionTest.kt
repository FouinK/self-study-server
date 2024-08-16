package self.study.sels.application.book_case.action

import fixtures.BookcaseBuilder
import fixtures.MemberBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import self.study.sels.application.book_case.port.`in`.GetBookcaseListCommand
import self.study.sels.application.book_case.port.`in`.GetBookcaseListUseCase
import self.study.sels.model.book_case.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository

@SpringBootTest
class GetBookcaseListActionTest(
    @Autowired val bookcaseRepository: BookcaseRepository,
    @Autowired val memberRepository: MemberRepository,
) {
    lateinit var getBookcaseListUseCase: GetBookcaseListUseCase
    lateinit var member: Member
    lateinit var english: String
    lateinit var korean: String
    lateinit var science: String

    @BeforeEach
    fun beforeEach() {
        getBookcaseListUseCase =
            GetBookcaseListAction(
                bookcaseRepository,
            )

        member = memberRepository.save(MemberBuilder().build())

        english = "영어"
        korean = "국어"
        science = "과학"

        bookcaseRepository.saveAll(
            listOf(
                BookcaseBuilder(
                    name = english,
                    memberId = member.id,
                ).build(),
                BookcaseBuilder(
                    name = korean,
                    memberId = member.id,
                ).build(),
                BookcaseBuilder(
                    name = science,
                    memberId = member.id,
                ).build(),
            ),
        )
    }

    @Test
    fun `책장 리스트를 정상 조회한다`() {
        // given & when
        val page = 0
        val command =
            GetBookcaseListCommand(
                memberId = member.id,
                pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")),
            )

        val getBookcaseListResponseDto = getBookcaseListUseCase.list(command)

        // then
        Assertions.assertThat(getBookcaseListResponseDto.totalElement).isEqualTo(3)
        Assertions.assertThat(getBookcaseListResponseDto.page).isEqualTo(page)
        val actualBookcaseNames = getBookcaseListResponseDto.bookcaseList.map { it.bookcaseName }
        Assertions.assertThat(actualBookcaseNames).containsExactly(
            science,
            korean,
            english,
        )
    }
}
