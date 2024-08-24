package self.study.sels.application.book_case.action

import org.springframework.stereotype.Component
import self.study.sels.application.book_case.port.`in`.UpdateBookcaseUseCase
import self.study.sels.controller.dto.UpdateBookcaseRequestDto
import self.study.sels.model.book_case.BookcaseRepository

@Component
class UpdateBookcaseAction(
    private val bookcaseRepository: BookcaseRepository,
) : UpdateBookcaseUseCase {
    override fun update(command: UpdateBookcaseRequestDto): String {
        val bookcase =
            bookcaseRepository.findById(command.bookcaseId)
                .orElseThrow { throw Exception("책장이 없습니다.") }

        if (bookcaseRepository.existsByMemberIdAndName(bookcase.memberId, command.name)) {
            throw Exception("이미 존재하는 책장이름으로는 변경 할 수 없습니다.")
        }

        bookcase.updateName(command.name)

        bookcaseRepository.save(bookcase)

        return bookcase.name
    }
}
