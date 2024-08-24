package self.study.sels.application.book.action

import org.springframework.stereotype.Component
import self.study.sels.application.book.port.`in`.UpdateBookUseCase
import self.study.sels.controller.dto.UpdateBookRequestDto
import self.study.sels.model.book.BookRepository

@Component
class UpdateBookAction(
    private val bookRepository: BookRepository,
) : UpdateBookUseCase {
    override fun update(command: UpdateBookRequestDto): String {
        val book =
            bookRepository.findByIdOrderByIdDesc(command.bookId)
                ?: throw Exception("책이 없습니다.")

        if (bookRepository.existsByMemberIdAndName(book.memberId, command.name)) {
            throw Exception("이미 존재하는 책 이름으로는 변경 할 수 없습니다.")
        }

        book.updateName(command.name)

        bookRepository.save(book)

        return book.name
    }
}
