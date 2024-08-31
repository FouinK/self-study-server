package self.study.sels.application.question.action

import fixtures.BookBuilder
import fixtures.MemberBuilder
import fixtures.QuestionBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import self.study.sels.application.question.port.`in`.GetQuestionUseCase
import self.study.sels.model.book.Book
import self.study.sels.model.book.BookRepository
import self.study.sels.model.member.Member
import self.study.sels.model.member.MemberRepository
import self.study.sels.model.question.QuestionRepository

@SpringBootTest
class GetQuestionActionTest(
    @Autowired val questionRepository: QuestionRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val bookRepository: BookRepository,
) {
    lateinit var getQuestionUseCase: GetQuestionUseCase
    lateinit var member: Member
    lateinit var book: Book

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

        QuestionBuilder(
            memberId = member.id,
            book = book,
            question = "질문",
        ).build()
    }

    @Test
    fun `질문 상세를 정상 조회한다`() {
        //given

        //when

        //then
    }
}
