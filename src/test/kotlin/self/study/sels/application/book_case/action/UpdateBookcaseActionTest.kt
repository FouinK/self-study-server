package self.study.sels.application.book_case.action

import fixtures.BookcaseBuilder
import fixtures.MemberBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import self.study.sels.application.book_case.port.`in`.UpdateBookcaseUseCase
import self.study.sels.controller.dto.UpdateBookcaseRequestDto
import self.study.sels.model.book_case.Bookcase
import self.study.sels.model.book_case.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository

@SpringBootTest
class UpdateBookcaseActionTest(
    @Autowired val bookcaseRepository: BookcaseRepository,
    @Autowired val memberRepository: MemberRepository,
) {
    lateinit var updateBookcaseUseCase: UpdateBookcaseUseCase
    lateinit var member: Member
    lateinit var existsName: String
    lateinit var updateBeforeName: String
    lateinit var bookcase: Bookcase

    @BeforeEach
    fun setUp() {
        updateBookcaseUseCase =
            UpdateBookcaseAction(
                bookcaseRepository,
            )

        member = memberRepository.save(MemberBuilder().build())

        existsName = "이미 존재하는 이름"
        updateBeforeName = "수정 전 이름"
        bookcaseRepository.save(BookcaseBuilder(name = existsName, memberId = member.id).build())
        bookcase = bookcaseRepository.save(BookcaseBuilder(name = updateBeforeName, memberId = member.id).build())
    }

    @Test
    fun `책장 이름이 정상 수정 된다`() {
        // given
        val updateName = "수정 될 이름"
        val command =
            UpdateBookcaseRequestDto(
                bookcaseId = bookcase.id,
                name = updateName,
            )

        // when
        val updatedName = updateBookcaseUseCase.update(command)

        // then
        val updatedBookcase = bookcaseRepository.findByIdOrNull(bookcase.id)!!

        Assertions.assertThat(updatedBookcase.name).isEqualTo(updatedName)
    }

    @Test
    fun `이미 존재하는 이름으로 수정 시 예외가 발생한다`() {
        // given
        val updateName = existsName
        val command =
            UpdateBookcaseRequestDto(
                bookcaseId = bookcase.id,
                name = updateName,
            )

        // when & then
        assertThrows<Exception> {
            val updatedName = updateBookcaseUseCase.update(command)
        }.message.apply { Assertions.assertThat(this).isEqualTo("이미 존재하는 책장 이름으로는 변경 할 수 없습니다.") }
    }
}
