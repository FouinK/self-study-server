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
import self.study.sels.application.book_case.port.`in`.UpdateBookcaseCommand
import self.study.sels.application.book_case.port.`in`.UpdateBookcaseUseCase
import self.study.sels.exception.ExistsNameException
import self.study.sels.exception.NotFoundException
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
    lateinit var otherMember: Member
    lateinit var existsName: String
    lateinit var updateBeforeName: String
    lateinit var bookcase: Bookcase
    lateinit var otherMemberBookcase: Bookcase

    @BeforeEach
    fun setUp() {
        updateBookcaseUseCase =
            UpdateBookcaseAction(
                bookcaseRepository,
            )

        member = memberRepository.save(MemberBuilder().build())
        otherMember = memberRepository.save(MemberBuilder().build())

        existsName = "이미 존재하는 이름"
        updateBeforeName = "수정 전 이름"
        bookcaseRepository.save(BookcaseBuilder(name = existsName, memberId = member.id).build())
        bookcase = bookcaseRepository.save(BookcaseBuilder(name = updateBeforeName, memberId = member.id).build())
        otherMemberBookcase = bookcaseRepository.save(BookcaseBuilder(name = updateBeforeName, memberId = otherMember.id).build())
    }

    @Test
    fun `책장 이름이 정상 수정 된다`() {
        // given
        val updateName = "수정 될 이름"
        val command =
            UpdateBookcaseCommand(
                bookcaseId = bookcase.id,
                name = updateName,
                memberId = member.id,
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
            UpdateBookcaseCommand(
                bookcaseId = bookcase.id,
                name = updateName,
                memberId = member.id,
            )

        // when & then
        assertThrows<ExistsNameException> {
            updateBookcaseUseCase.update(command)
        }.message.apply { Assertions.assertThat(this).isEqualTo("이미 존재하는 책장 이름으로는 변경 할 수 없습니다.") }
    }

    @Test
    fun `내 책장이 아닌 책장을 수정할 시 예외가 발생한다`() {
        // given
        val updateName = "수정 될 이름"
        val command =
            UpdateBookcaseCommand(
                bookcaseId = bookcase.id,
                name = updateName,
                memberId = otherMember.id,
            )

        // when & then
        assertThrows<NotFoundException> {
            updateBookcaseUseCase.update(command)
        }.message.apply { Assertions.assertThat(this).isEqualTo("책장이 없습니다.") }
    }

    @Test
    fun `내 책장이 아닌 책장을 수정할 시 예외가 발생한다2`() {
        // given
        val updateName = "수정 될 이름"
        val command =
            UpdateBookcaseCommand(
                bookcaseId = otherMemberBookcase.id,
                name = updateName,
                memberId = member.id,
            )

        // when & then
        assertThrows<NotFoundException> {
            updateBookcaseUseCase.update(command)
        }.message.apply { Assertions.assertThat(this).isEqualTo("책장이 없습니다.") }
    }
}
