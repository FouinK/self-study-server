package self.study.sels.application.question.port.`in`

interface CreateQuestionAnswerUseCase {
    fun createQuestionAnswer(command: CreateQuestionAnswerCommand): Int
}
