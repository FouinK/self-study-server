package self.study.sels.application.member.action

import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.transaction.annotation.Transactional
import self.study.sels.annotation.Action
import self.study.sels.application.member.port.`in`.JoinAndLoginUseCase
import self.study.sels.application.member.port.`in`.JoinMemberResponseDto
import self.study.sels.controller.dto.JoinMemberRequestDto
import self.study.sels.exception.NotFoundException
import self.study.sels.model.answer.AnswerFactory
import self.study.sels.model.answer.AnswerRepository
import self.study.sels.model.book.BookFactory
import self.study.sels.model.book.BookRepository
import self.study.sels.model.bookcase.BookcaseFactory
import self.study.sels.model.bookcase.BookcaseRepository
import self.study.sels.model.member.MemberAuthenticationRedisRepository
import self.study.sels.model.member.MemberFactory
import self.study.sels.model.member.MemberRepository
import self.study.sels.model.question.QuestionFactory
import self.study.sels.model.question.QuestionRepository
import self.study.sels.service.BookPOJO
import self.study.sels.service.BookcasePOJO
import self.study.sels.service.NewbieJoinService
import self.study.sels.util.AuthCodeUtil

@Action
class JoinAndLoginAction(
    private val environment: Environment,
    private val memberAuthenticationRedisRepository: MemberAuthenticationRedisRepository,
    private val memberFactory: MemberFactory,
    private val memberRepository: MemberRepository,
    private val newbieJoinService: NewbieJoinService,
    private val bookcaseFactory: BookcaseFactory,
    private val bookFactory: BookFactory,
    private val bookcaseRepository: BookcaseRepository,
    private val bookRepository: BookRepository,
    private val questionFactory: QuestionFactory,
    private val answerFactory: AnswerFactory,
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
) : JoinAndLoginUseCase {
    @Transactional
    override fun execute(
        command: JoinMemberRequestDto
    ): JoinMemberResponseDto {
        if (environment.acceptsProfiles(Profiles.of("production")) || environment.acceptsProfiles(Profiles.of("test"))) {
            val memberAuthenticationCode = memberAuthenticationRedisRepository.getMemberAuthenticationCode(command.phone)
                ?: throw NotFoundException("인증 번호 요청을 먼저 진행해주세요.")
            if (memberAuthenticationCode != command.authenticationCode) {
                throw Exception("인증번호가 일치하지 않습니다.")
            }
        }

        var member = memberRepository.findByPhone(command.phone)

        member = if (member != null) {
            member.refreshToken()
            member
        } else {
            memberRepository.save(
                memberFactory.create(
                    MemberFactory.Command(
                        authToken = AuthCodeUtil.generateAuthToken(),
                        pushYn = false,
                        marketingYn = false,
                        phone = command.phone,
                    ),
                ),
            )
        }

        memberAuthenticationRedisRepository.deleteMemberAuthenticationCode(command.phone)

        createNewbie(memberId = member.id)

        return JoinMemberResponseDto(
            memberId = member.id,
            authToken = member.authToken!!,
        )
    }

    private fun createNewbie(memberId: Int) {
        val bookcase = bookcaseRepository.save(
            bookcaseFactory.create(
                BookcaseFactory.Command(
                    name = BookcasePOJO().name,
                    color = BookcasePOJO().color,
                    memberId = memberId,
                ),
            ),
        )

        val book = bookRepository.save(
            bookFactory.create(
                BookFactory.Command(
                    name = BookPOJO().name,
                    color = BookPOJO().color,
                    bookcaseId = bookcase.id,
                    memberId = memberId,
                ),
            ),
        )

        val questionPOJOs = newbieJoinService.createNewbieQuestionPOJOs()

        val questions = questionPOJOs.map { questionPOJO ->
            val question = questionFactory.create(
                QuestionFactory.Command(
                    memberId = memberId,
                    bookId = book.id,
                    question = questionPOJO.question,
                ),
            )
            val answers = questionPOJO.answerPOJOs.map { answerPOJO ->
                answerFactory.create(
                    AnswerFactory.Command(
                        question = question,
                        answer = answerPOJO.answer,
                        correctYn = answerPOJO.correctYn,
                        memberId = memberId,
                    ),
                )
            }
            question to answers
        }

        questionRepository.saveAll(questions.map { it.first })
        answerRepository.saveAll(questions.flatMap { it.second })

        questions.forEach { it.first.updateAnswerList(it.second) }

        questionRepository.saveAll(questions.map { it.first })
    }
}
