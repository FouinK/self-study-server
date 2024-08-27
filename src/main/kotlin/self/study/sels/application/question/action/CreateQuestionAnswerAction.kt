package self.study.sels.application.question.action

import org.springframework.transaction.annotation.Transactional
import self.study.sels.annotation.Action
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerCommand
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerUseCase
import self.study.sels.exception.ExistsNameException
import self.study.sels.exception.NotFoundException
import self.study.sels.model.answer.AnswerRepository
import self.study.sels.model.book.BookRepository
import self.study.sels.model.question.Question
import self.study.sels.model.question.QuestionRepository

@Action
class CreateQuestionAnswerAction(
    private val questionRepository: QuestionRepository,
    private val bookRepository: BookRepository,
    private val answerRepository: AnswerRepository,
) : CreateQuestionAnswerUseCase {
    @Transactional
    override fun createQuestionAnswer(command: CreateQuestionAnswerCommand): Int {
        val book = bookRepository.findById(command.bookId)
            .orElseThrow { throw NotFoundException("책이 없습니다.") }

        if (questionRepository.existsByQuestionAndBook(command.question, book)) {
            throw ExistsNameException("동일한 질문이 이미 존재합니다.")
        }

        val question = questionRepository.save(
            Question(
                memberId = command.memberId,
                book = bookRepository.findById(command.bookId).orElseThrow { throw NotFoundException("책이 없습니다.") },
                question = command.question,
                multipleChoiceYn = command.multipleChoiceYn,
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
