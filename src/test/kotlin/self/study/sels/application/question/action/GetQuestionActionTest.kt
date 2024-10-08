package self.study.sels.application.question.action

import fixtures.BookBuilder
import fixtures.MemberBuilder
import fixtures.step.CreateQuestionStep
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.application.question.port.`in`.GetQuestionCommand
import self.study.sels.application.question.port.`in`.GetQuestionUseCase
import self.study.sels.controller.dto.GetQuestionResponseDto
import self.study.sels.exception.NotFoundException
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
    lateinit var answerStringList: List<String>

    @BeforeEach
    fun setUp() {
        getQuestionUseCase = GetQuestionAction(
            questionRepository,
        )

        val createQuestionStep = CreateQuestionStep(
            questionRepository,
            answerRepository,
        )

        member = memberRepository.save(MemberBuilder().build())
        book = bookRepository.save(
            BookBuilder(
                name = "영어",
                memberId = member.id,
                bookcaseId = 1,
            ).build(),
        )

        questionString = "문제"
        val answerListSize = 5

        question = createQuestionStep.create(
            member = member,
            book = book,
            questionString = questionString,
            answerListSize = answerListSize,
        )

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
    fun `문제 상세를 조회할때 정답을 노출하지 않고 리스트만 조회한다`() {
        //given
        val command = GetQuestionCommand(
            memberId = member.id,
            questionId = question.id,
            getAnswerList = true,
            getCorrectAnswer = false,
        )

        //when
        val detail: GetQuestionResponseDto = getQuestionUseCase.detail(command)

        //then
        assertThat(detail.answerList.size).isEqualTo(5)
        assertThat(detail.question).isEqualTo(questionString)
        assertThat(detail.answerId).isEqualTo(null)
        val answerStringList = detail.answerList.map { item -> item.answer }
        assertThat(answerStringList).containsExactly(
            this.answerStringList[0],
            this.answerStringList[1],
            this.answerStringList[2],
            this.answerStringList[3],
            this.answerStringList[4],
        )
    }

    @Test
    fun `문제 상세를 조회 할 때 정답까지 같이 조회한다`() {
        //given
        val command = GetQuestionCommand(
            memberId = member.id,
            questionId = question.id,
            getAnswerList = true,
            getCorrectAnswer = true,
        )

        //when
        val detail = getQuestionUseCase.detail(command)

        //then
        val answerList = answerRepository.findAllByIdIn(detail.answerList.map { it.answerId })

        val correctAnswer = answerList.find { it.correctYn }!!

        assertThat(detail.answerList.size).isEqualTo(5)
        assertThat(detail.question).isEqualTo(questionString)
        assertThat(detail.answerId).isEqualTo(correctAnswer.id)
        val answerStringList = detail.answerList.map { item -> item.answer }
        assertThat(answerStringList).containsExactly(
            this.answerStringList[0],
            this.answerStringList[1],
            this.answerStringList[2],
            this.answerStringList[3],
            this.answerStringList[4],
        )
    }

    @Test
    fun `내가 작성한 문제가 아니라면 정상 조회 되지 않는다`() {
        //given
        val command = GetQuestionCommand(
            memberId = Int.MAX_VALUE - 100,
            questionId = question.id,
            getAnswerList = true,
            getCorrectAnswer = true,
        )

        //when & then
        assertThrows<NotFoundException> {
            getQuestionUseCase.detail(command)
        }.message.apply { assertThat(this).isEqualTo("질문이 존재하지 않습니다.") }
    }
}
