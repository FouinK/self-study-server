package self.study.sels.fixture.step

import org.springframework.stereotype.Component
import self.study.sels.fixture.*
import self.study.sels.model.answer.AnswerRepository
import self.study.sels.model.book.Book
import self.study.sels.model.book.BookRepository
import self.study.sels.model.bookcase.Bookcase
import self.study.sels.model.bookcase.BookcaseRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import self.study.sels.model.question.Question
import self.study.sels.model.question.QuestionRepository

@Component
class CreateQuestionStep(
    private val memberRepository: MemberRepository,
    private val bookCaseRepository: BookcaseRepository,
    private val bookRepository: BookRepository,
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
) {
    fun create(
        questionString: String,
        answerListSize: Int,
        answerStringList: MutableList<String> = mutableListOf(),
    ): CreateQuestionResponse {
        val member = memberRepository.save(
            MemberBuilder().build(),
        )
        val bookcase = bookCaseRepository.save(
            BookcaseBuilder(
                name = "영어",
                memberId = member.id,
            ).build(),
        )
        val book = bookRepository.save(
            BookBuilder(
                name = "영어",
                memberId = member.id,
                bookcaseId = bookcase.id,
            ).build(),
        )

        val question = questionRepository.save(
            QuestionBuilder(
                memberId = member.id,
                bookId = book.id,
                question = questionString,
            ).build(),
        )

        question.updateAnswerList(
            answerRepository.saveAll(
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
            ),
        )

        return CreateQuestionResponse(
            question = questionRepository.save(question),
            book = book,
            bookcase = bookcase,
            member = member,
        )
    }

    data class CreateQuestionResponse(
        val question: Question,
        val book: Book,
        val bookcase: Bookcase,
        val member: Member,
    )
}
