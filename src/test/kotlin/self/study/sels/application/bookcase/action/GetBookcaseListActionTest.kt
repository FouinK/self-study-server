package self.study.sels.application.bookcase.action

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import self.study.sels.fixture.BookcaseBuilder
import self.study.sels.fixture.MemberBuilder
import self.study.sels.model.bookcase.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import self.study.sels.userapi.bookcase.application.action.GetBookcaseListAction
import self.study.sels.userapi.bookcase.application.port.`in`.GetBookcaseListCommand
import self.study.sels.userapi.bookcase.application.port.`in`.GetBookcaseListUseCase

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
    lateinit var color: String

    @BeforeEach
    fun beforeEach() {
        getBookcaseListUseCase =
            GetBookcaseListAction(
                bookcaseRepository,
            )

        member = memberRepository.save(MemberBuilder().build())

        color = "#6C86E8"
        english = "영어"
        korean = "국어"
        science = "과학"

        bookcaseRepository.saveAll(
            listOf(
                BookcaseBuilder(
                    name = english,
                    color = color,
                    memberId = member.id,
                ).build(),
                BookcaseBuilder(
                    name = korean,
                    color = color,
                    memberId = member.id,
                ).build(),
                BookcaseBuilder(
                    name = science,
                    color = color,
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
        assertThat(getBookcaseListResponseDto.totalElement).isEqualTo(3)
        assertThat(getBookcaseListResponseDto.page).isEqualTo(page)
        assertThat(getBookcaseListResponseDto.bookcaseList.size).isEqualTo(3)
        val actualBookcaseNames = getBookcaseListResponseDto.bookcaseList.map { it.bookcaseName }
        assertThat(actualBookcaseNames).containsExactly(
            science,
            korean,
            english,
        )
    }
}
