package self.study.sels.application.book.action

import org.springframework.stereotype.Component
import self.study.sels.application.book.port.`in`.GetBookCommand
import self.study.sels.application.book.port.`in`.GetBookUseCase
import self.study.sels.controller.dto.GetBookResponseDto
import self.study.sels.model.book.BookRepository

@Component
class GetBookAction(
    private val bookRepository: BookRepository,
) : GetBookUseCase {
    override fun detail(
        command: GetBookCommand
    ): GetBookResponseDto {
        val book = bookRepository.findByIdAndMemberId(command.bookId, command.memberId)
            ?: throw Exception("책이 없습니다.")

        return GetBookResponseDto(
            bookName = book.name,
            questionList = book.questionList.map {
                GetBookResponseDto.QuestionItem(
                    questionId = it.id,
                    question = it.question,
                )
            },
        )
    }
}
