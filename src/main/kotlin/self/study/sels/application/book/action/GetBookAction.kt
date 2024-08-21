package self.study.sels.application.book.action

import org.springframework.stereotype.Component
import self.study.sels.application.book.port.`in`.GetBookUseCase
import self.study.sels.controller.dto.GetBookResponseDto
import self.study.sels.model.book.BookRepository

@Component
class GetBookAction(
    private val bookRepository: BookRepository,
) : GetBookUseCase {
    override fun detail(bookId: Int): GetBookResponseDto {
        val book =
            bookRepository.findById(bookId)
                .orElseThrow { throw Exception("책이 없습니다.") }

        return GetBookResponseDto(
            bookName = book.name,
            questionList =
                book.questionList.map {
                    GetBookResponseDto.QuestionItem(
                        questionId = it.id,
                        question = it.question,
                    )
                },
        )
    }
}
