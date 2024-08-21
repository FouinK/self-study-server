package self.study.sels.application.question.action

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerCommand
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerUseCase
import self.study.sels.model.question.QuestionRepository

@Component
class CreateQuestionAnswerAction(
    private val questionRepository: QuestionRepository,
) : CreateQuestionAnswerUseCase {
    @Transactional
    override fun createQuestionAnswer(command: CreateQuestionAnswerCommand): Int {
        val question = questionRepository.save(command.toQuestionEntity())

        val answerList = command.toAnswerEntityList(question).toMutableList()

        question.answerList = answerList

        questionRepository.save(question)

        return question.id
    }
}
