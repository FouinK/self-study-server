package self.study.sels.application.question.action

import self.study.sels.annotation.Action
import self.study.sels.application.question.port.`in`.UpdateQuestionAndAnswerCommand
import self.study.sels.application.question.port.`in`.UpdateQuestionAndAnswerUseCase
import self.study.sels.controller.dto.UpdateQuestionAndAnswerResponseDto
import self.study.sels.exception.NotFoundException
import self.study.sels.model.question.QuestionRepository

@Action
class UpdateQuestionAndAnswerAction(
    private val questionRepository: QuestionRepository,
) : UpdateQuestionAndAnswerUseCase {
    override fun update(
        command: UpdateQuestionAndAnswerCommand
    ): UpdateQuestionAndAnswerResponseDto {
        val question = questionRepository.findByIdAndMemberId(
            questionId = command.questionId,
            memberId = command.memberId,
        ) ?: throw NotFoundException("문제가 존재하지 않습니다.")

        if (!command.question.isNullOrBlank()) {
            question.updateQuestion(command.question)
        }

        if (command.answerList.isNotEmpty()) {
            val changeAnswerList = command.answerList.map {
                val answer = question.answerList.find { answer -> answer.id == it.answerId }
                    ?: throw NotFoundException("보기가 존재하지 않습니다.")

                if (!it.answer.isNullOrBlank()) {
                    answer.updateAnswer(it.answer)
                }

                if (it.correctYn != null) {
                    answer.updateCorrectYn(it.correctYn)
                }

                answer
            }.toMutableList()

            question.updateAnswerList(changeAnswerList)
        }

        questionRepository.save(question)

        return UpdateQuestionAndAnswerResponseDto(
            questionId = command.questionId,
        )
    }
}
