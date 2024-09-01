package self.study.sels.application.question.action

import org.springframework.transaction.annotation.Transactional
import self.study.sels.annotation.Action
import self.study.sels.application.question.port.`in`.CreateQuestionAndAnswerCommand
import self.study.sels.application.question.port.`in`.CreateQuestionAndAnswerUseCase
import self.study.sels.exception.ExistsNameException
import self.study.sels.exception.NotFoundException
import self.study.sels.model.answer.AnswerRepository
import self.study.sels.model.book.BookRepository
import self.study.sels.model.question.Question
import self.study.sels.model.question.QuestionRepository

@Action
class CreateQuestionAndAnswerAction(
    private val questionRepository: QuestionRepository,
    private val bookRepository: BookRepository,
    private val answerRepository: AnswerRepository,
) : CreateQuestionAndAnswerUseCase {
    @Transactional
    override fun createQuestionAndAnswer(command: CreateQuestionAndAnswerCommand): Int {
        if (!bookRepository.existsById(command.bookId)) {
            throw NotFoundException("책이 없습니다.")
        }

        if (questionRepository.existsByQuestionAndBookId(command.question, command.bookId)) {
            throw ExistsNameException("동일한 질문이 이미 존재합니다.")
        }

        val question = questionRepository.save(
            Question(
                memberId = command.memberId,
                bookId = command.bookId,
                question = command.question,
            ),
        )

        val answerList = command.toAnswerEntityList(question).toMutableList()

        if (answerList.isNotEmpty()) {
            val newAnswerList = answerRepository.saveAll(answerList)
            question.answerList = newAnswerList
        }

        questionRepository.save(question)

        return question.id
    }
}
