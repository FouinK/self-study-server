package self.study.sels.application.question.action

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerCommand
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerUseCase
import self.study.sels.exception.NotFoundException
import self.study.sels.model.book.BookRepository
import self.study.sels.model.question.Question
import self.study.sels.model.question.QuestionRepository

@Component
class CreateQuestionAnswerAction(
    private val questionRepository: QuestionRepository,
    private val bookRepository: BookRepository,
) : CreateQuestionAnswerUseCase {
    @Transactional
    override fun createQuestionAnswer(command: CreateQuestionAnswerCommand): Int {
        val question =
            questionRepository.save(
                Question(
                    memberId = command.memberId,
                    book = bookRepository.findById(command.bookId).orElseThrow { throw NotFoundException("책이 없습니다.") },
                    question = command.question,
                    multipleChoiceYn = command.multipleChoiceYn,
                ),
            )

        val answerList = command.toAnswerEntityList(question).toMutableList()

        question.answerList = answerList

        questionRepository.save(question)

        return question.id
    }
}
