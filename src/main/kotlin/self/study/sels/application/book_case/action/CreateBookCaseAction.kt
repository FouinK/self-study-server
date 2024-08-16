package self.study.sels.application.book_case.action

import org.springframework.stereotype.Component
import self.study.sels.application.book_case.port.`in`.CreateBookCaseCommand
import self.study.sels.application.book_case.port.`in`.CreateBookCaseUseCase
import self.study.sels.model.book_case.BookCase
import self.study.sels.model.book_case.BookCaseRepository

@Component
class CreateBookCaseAction(
    private val bookCaseRepository: BookCaseRepository,
) : CreateBookCaseUseCase {
    override fun create(command: CreateBookCaseCommand): Int {
        if (bookCaseRepository.existsByMemberIdAndName(command.memberId, command.name)) {
            throw Exception("이미 사용중인 이름입니다.")
        }

        val bookCase =
            BookCase(
                memberId = command.memberId,
                name = command.name,
            )

        val newBookCase = bookCaseRepository.save(bookCase)

        return newBookCase.id
    }
}
