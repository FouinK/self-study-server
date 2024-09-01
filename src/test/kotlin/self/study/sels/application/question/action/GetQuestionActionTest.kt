package self.study.sels.application.question.action

import fixtures.AnswerListBuilder
import fixtures.BookBuilder
import fixtures.MemberBuilder
import fixtures.QuestionBuilder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.application.question.port.`in`.GetQuestionCommand
import self.study.sels.application.question.port.`in`.GetQuestionUseCase
import self.study.sels.model.answer.Answer
import self.study.sels.model.answer.AnswerRepository
import self.study.sels.model.book.Book
import self.study.sels.model.book.BookRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import self.study.sels.model.question.Question
import self.study.sels.model.question.QuestionRepository

@SpringBootTest
class GetQuestionActionTest(
    @Autowired val questionRepository: QuestionRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val bookRepository: BookRepository,
    @Autowired val answerRepository: AnswerRepository,
) {
    lateinit var getQuestionUseCase: GetQuestionUseCase
    lateinit var member: Member
    lateinit var book: Book
    lateinit var question: Question
    lateinit var questionString: String
    lateinit var answer1: Answer
    lateinit var answer2: Answer
    lateinit var answer3: Answer
    lateinit var answer4: Answer
    lateinit var answer5: Answer

    @BeforeEach
    fun setUp() {
        getQuestionUseCase = GetQuestionAction(
            questionRepository,
        )

        member = memberRepository.save(MemberBuilder().build())
        book = bookRepository.save(
            BookBuilder(
                name = "영어",
                memberId = member.id,
                bookcaseId = 1,
            ).build(),
        )

        questionString = "질문"

        question = questionRepository.save(
            QuestionBuilder(
                memberId = member.id,
                book = book,
                question = questionString,
            ).build(),
        )

        val answerListSize = 5

        answerRepository.saveAll(
            AnswerListBuilder(
                size = answerListSize,
                question = question,
                answerList = List(answerListSize) { i -> "보기" + (i + 1) },
                correctYnList = List(answerListSize - 1) { false } + List(1) { true },
            ).build(),
        ).forEachIndexed { index, answer ->
            when (index) {
                0 -> answer1 = answer
                1 -> answer2 = answer
                2 -> answer3 = answer
                3 -> answer4 = answer
                4 -> answer5 = answer
            }
        }

        question.answerList = mutableListOf(
            answer1,
            answer2,
            answer3,
            answer4,
            answer5,
        )

        question = questionRepository.save(question)
    }

    @Test
    fun `질문 상세를 조회할때 정답을 노출하지 않고 리스트만 조회한다`() {
        //given
        val command = GetQuestionCommand(
            memberId = member.id,
            questionId = question.id,
            getAnswerList = true,
            getCorrectAnswer = false,
        )

        //when
        val detail = getQuestionUseCase.detail(command)

        //then
        assertThat(detail.answerList.size).isEqualTo(5)
        assertThat(detail.question).isEqualTo(questionString)
        assertThat(detail.answerId).isEqualTo(null)
        assertThat(detail.multipleChoiceYn).isEqualTo(true)
    }
}
