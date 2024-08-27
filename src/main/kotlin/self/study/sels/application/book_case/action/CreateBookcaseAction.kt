package self.study.sels.application.book_case.action

import self.study.sels.annotation.Action
import self.study.sels.application.book_case.port.`in`.CreateBookcaseCommand
import self.study.sels.application.book_case.port.`in`.CreateBookcaseUseCase
import self.study.sels.model.book_case.BookcaseRepository

@Action
class CreateBookcaseAction(
    private val bookcaseRepository: BookcaseRepository,
) : CreateBookcaseUseCase {
    override fun create(
        command: CreateBookcaseCommand
    ): Int {
        if (bookcaseRepository.existsByMemberIdAndName(command.memberId, command.name)) {
            throw Exception("이미 사용중인 이름입니다.")
        }

        val bookCase = command.toEntity()

        val newBookcase = bookcaseRepository.save(bookCase)

        return newBookcase.id
    }
}
