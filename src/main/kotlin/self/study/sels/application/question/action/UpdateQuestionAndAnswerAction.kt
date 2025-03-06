package self.study.sels.application.question.action

import self.study.sels.annotation.Action
import self.study.sels.application.question.port.`in`.UpdateQuestionAndAnswerCommand
import self.study.sels.application.question.port.`in`.UpdateQuestionAndAnswerUseCase
import self.study.sels.controller.dto.UpdateQuestionAndAnswerResponseDto
import self.study.sels.exception.NotFoundException
import self.study.sels.model.answer.AnswerFactory
import self.study.sels.model.answer.AnswerFactory.*
import self.study.sels.model.question.QuestionRepository

@Action
class UpdateQuestionAndAnswerAction(
    private val questionRepository: QuestionRepository,
    private val answerFactory: AnswerFactory,
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

        if (command.answerList.isEmpty()) {
            throw NotFoundException("수정할 답변을 입력해주세요.")
        }

        val newAnswerList = command.answerList.map {
            answerFactory.create(
                Command(
                    question = question,
                    answer = it.answer,
                    correctYn = it.correctYn,
                    memberId = command.memberId,
                ),
            )
        }

        question.updateAnswerList(newAnswerList)

        questionRepository.save(question)

        return UpdateQuestionAndAnswerResponseDto(
            questionId = command.questionId,
        )
    }
}
