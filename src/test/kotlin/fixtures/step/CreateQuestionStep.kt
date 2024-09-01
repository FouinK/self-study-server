package fixtures.step

import fixtures.AnswerListBuilder
import fixtures.QuestionBuilder
import self.study.sels.model.answer.AnswerRepository
import self.study.sels.model.book.Book
import self.study.sels.model.member.Member
import self.study.sels.model.question.Question
import self.study.sels.model.question.QuestionRepository

class CreateQuestionStep(
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
) {
    fun create(
        member: Member,
        book: Book,
        questionString: String,
        answerListSize: Int,
        answerStringList: MutableList<String> = mutableListOf(),
    ): Question {
        var question = questionRepository.save(
            QuestionBuilder(
                memberId = member.id,
                bookId = book.id,
                question = questionString,
            ).build(),
        )

        question.answerList = answerRepository.saveAll(
            AnswerListBuilder(
                size = answerListSize,
                question = question,
                answerList = List(answerListSize) { i ->
                    val answer = "보기" + (i + 1)
                    answerStringList.add(answer)
                    answer
                },
                correctYnList = List(answerListSize - 1) { false } + List(1) { true },
            ).build(),
        )

        question = questionRepository.save(question)

        return question
    }
}
