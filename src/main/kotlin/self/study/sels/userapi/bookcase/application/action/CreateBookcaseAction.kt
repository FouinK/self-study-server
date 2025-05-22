package self.study.sels.userapi.bookcase.application.action

import self.study.sels.annotation.Action
import self.study.sels.model.bookcase.BookcaseFactory
import self.study.sels.model.bookcase.BookcaseRepository
import self.study.sels.userapi.bookcase.application.port.`in`.CreateBookcaseCommand
import self.study.sels.userapi.bookcase.application.port.`in`.CreateBookcaseUseCase

@Action
class CreateBookcaseAction(
    private val bookcaseRepository: BookcaseRepository,
    private val bookcaseFactory: BookcaseFactory,
) : CreateBookcaseUseCase {
    override fun create(
        command: CreateBookcaseCommand
    ): Int {
        if (bookcaseRepository.existsByMemberIdAndName(command.memberId, command.name)) {
            throw Exception("이미 사용중인 이름입니다.")
        }

        val bookcase = bookcaseRepository.save(
            bookcaseFactory.create(
                BookcaseFactory.Command(
                    name = command.name,
                    color = command.color,
                    memberId = command.memberId,
                ),
            ),
        )

        return bookcase.id
    }
}
