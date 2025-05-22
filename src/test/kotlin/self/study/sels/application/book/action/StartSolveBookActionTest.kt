package self.study.sels.application.book.action

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import self.study.sels.IntegrationTest
import self.study.sels.exception.NotFoundException
import self.study.sels.fixture.MemberBuilder
import self.study.sels.fixture.step.CreateQuestionStep
import self.study.sels.model.answer.Answer
import self.study.sels.model.book.Book
import self.study.sels.model.bookresulthistory.BookResultHistoryRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import self.study.sels.model.question.Question
import self.study.sels.userapi.book.application.port.`in`.StartSolveBookCommand
import self.study.sels.userapi.book.application.port.`in`.StartSolveBookUseCase
import kotlin.jvm.optionals.getOrNull

class StartSolveBookActionTest(
    private val createQuestionStep: CreateQuestionStep,
    private val startSolveBookUseCase: StartSolveBookUseCase,
    private val memberRepository: MemberRepository,
    private val bookResultHistoryRepository: BookResultHistoryRepository,
) : IntegrationTest() {
    lateinit var member: Member
    lateinit var book: Book
    lateinit var question: Question
    lateinit var questionString: String
    lateinit var answer1: Answer
    lateinit var answer2: Answer
    lateinit var answer3: Answer
    lateinit var answer4: Answer
    lateinit var answer5: Answer
    lateinit var answerStringList: List<String>

    @BeforeEach
    fun setUp() {
        questionString = "문제"
        val answerListSize = 5

        val createQuestionResponse = createQuestionStep.create(
            questionString = questionString,
            answerListSize = answerListSize,
        )

        question = createQuestionResponse.question
        book = createQuestionResponse.book
        member = createQuestionResponse.member

        question.answerList.forEachIndexed { index, answer ->
            when (index) {
                0 -> answer1 = answer
                1 -> answer2 = answer
                2 -> answer3 = answer
                3 -> answer4 = answer
                4 -> answer5 = answer
            }
        }
        answerStringList = question.answerList.map {
            it.answer
        }
    }

    @Test
    fun `문제풀이를 정상 시작한다`() {
        //given
        val command = StartSolveBookCommand(
            bookId = book.id,
            memberId = member.id,
        )

        //when
        val bookResultHistoryId = startSolveBookUseCase.execute(command)

        //then
        val bookResultHistory = bookResultHistoryRepository.findById(bookResultHistoryId).getOrNull()!!

        Assertions.assertThat(bookResultHistory.bookId).isEqualTo(book.id)
        Assertions.assertThat(bookResultHistory.memberId).isEqualTo(member.id)
    }

    @Test
    fun `본인의 책이 아닌 경우에 예외가 발생한다`() {
        //given
        val anotherMember = memberRepository.save(MemberBuilder().build())

        val command = StartSolveBookCommand(
            bookId = book.id,
            memberId = anotherMember.id,
        )

        //when & then
        assertThrows<NotFoundException> {
            startSolveBookUseCase.execute(command)
        }
    }
}
