package self.study.sels.application.book_case.action

import self.study.sels.annotation.Action
import self.study.sels.application.book_case.port.`in`.UpdateBookcaseCommand
import self.study.sels.application.book_case.port.`in`.UpdateBookcaseUseCase
import self.study.sels.exception.ExistsNameException
import self.study.sels.exception.NotFoundException
import self.study.sels.model.book_case.BookcaseRepository

@Action
class UpdateBookcaseAction(
    private val bookcaseRepository: BookcaseRepository,
) : UpdateBookcaseUseCase {
    override fun update(
        command: UpdateBookcaseCommand
    ): String {
        val bookcase = bookcaseRepository.findByIdAndMemberId(command.bookcaseId, command.memberId)
            ?: throw NotFoundException("책장이 없습니다.")

        if (bookcaseRepository.existsByMemberIdAndName(bookcase.memberId, command.name)) {
            throw ExistsNameException("이미 존재하는 책장 이름으로는 변경 할 수 없습니다.")
        }

        bookcase.updateName(command.name)

        bookcaseRepository.save(bookcase)

        return bookcase.name
    }
}
