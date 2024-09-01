package self.study.sels.application.question.port.`in`

interface CreateQuestionAndAnswerUseCase {
    fun createQuestionAndAnswer(command: CreateQuestionAndAnswerCommand): Int
}
