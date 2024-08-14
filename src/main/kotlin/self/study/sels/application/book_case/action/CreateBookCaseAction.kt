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
        val bookCase =
            BookCase(
                memberId = command.memberId,
                name = command.name,
            )

        val newBookCase = bookCaseRepository.save(bookCase)

        return newBookCase.id
    }
}
