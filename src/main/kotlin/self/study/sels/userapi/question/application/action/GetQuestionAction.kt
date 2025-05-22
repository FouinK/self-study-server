package self.study.sels.userapi.question.application.action

import self.study.sels.annotation.Action
import self.study.sels.exception.NotFoundException
import self.study.sels.model.question.QuestionRepository
import self.study.sels.userapi.question.application.port.`in`.GetQuestionCommand
import self.study.sels.userapi.question.application.port.`in`.GetQuestionUseCase

@Action
class GetQuestionAction(
    private val questionRepository: QuestionRepository,
) : GetQuestionUseCase {
    override fun detail(
        command: GetQuestionCommand
    ): GetQuestionResponseDto {
        val question = questionRepository.findByIdAndMemberId(command.questionId, command.memberId)
            ?: throw NotFoundException("질문이 존재하지 않습니다.")

        return GetQuestionResponseDto(
            questionId = question.id,
            question = question.question,
            answerId = if (command.getCorrectAnswer) question.answerId else null,
            answerList = if (command.getAnswerList) {
                question.answerList.map {
                    GetQuestionResponseDto.Item(
                        answerId = it.id,
                        answer = it.answer,
                        correctYn = if (command.getCorrectAnswer) it.correctYn else false,
                    )
                }
            } else {
                listOf()
            },
        )
    }
}
