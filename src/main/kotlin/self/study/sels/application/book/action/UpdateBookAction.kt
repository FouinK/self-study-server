package self.study.sels.application.book.action

import org.springframework.stereotype.Component
import self.study.sels.application.book.port.`in`.UpdateBookCommand
import self.study.sels.application.book.port.`in`.UpdateBookUseCase
import self.study.sels.exception.ExistsNameException
import self.study.sels.exception.NotFoundException
import self.study.sels.model.book.BookRepository

@Component
class UpdateBookAction(
    private val bookRepository: BookRepository,
) : UpdateBookUseCase {
    override fun update(command: UpdateBookCommand): String {
        val book =
            bookRepository.findByIdAndMemberIdOrderByIdDesc(command.bookId, command.memberId)
                ?: throw NotFoundException("책이 없습니다.")

        if (bookRepository.existsByMemberIdAndName(book.memberId, command.name)) {
            throw ExistsNameException("이미 존재하는 책 이름으로는 변경 할 수 없습니다.")
        }

        book.updateName(command.name)

        bookRepository.save(book)

        return book.name
    }
}
