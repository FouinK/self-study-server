package self.study.sels.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerCommand
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerUseCase
import self.study.sels.application.question.port.`in`.GetQuestionCommand
import self.study.sels.application.question.port.`in`.GetQuestionUseCase
import self.study.sels.config.auth.MemberInfo
import self.study.sels.controller.dto.CreateQuestionAnswerRequestDto

@RestController
@RequestMapping("/sels/api/u/question")
class QuestionController(
    private val memberInfo: MemberInfo,
    private val getQuestionUseCase: GetQuestionUseCase,
    private val createQuestionAnswerUseCase: CreateQuestionAnswerUseCase,
) {
    @GetMapping("/{questionId}")
    fun detail(
        @PathVariable("questionId") questionId: Int,
        @RequestParam("getAnswerList") getAnswerList: Boolean,
        @RequestParam("getCorrectAnswer") getCorrectAnswer: Boolean,
    ): ResponseEntity<Any> {
        val command = GetQuestionCommand(
            memberId = memberInfo.memberId,
            questionId = questionId,
            getAnswerList = getAnswerList,
            getCorrectAnswer = getCorrectAnswer,
        )
        return ResponseEntity.ok(getQuestionUseCase.detail(command))
    }

    @PostMapping
    fun createQuestionAnswer(
        @RequestBody @Valid request: CreateQuestionAnswerRequestDto,
    ): ResponseEntity<Any> {
        val command = CreateQuestionAnswerCommand(
            bookId = request.bookId,
            question = request.question,
            multipleChoiceYn = request.multipleChoiceYn,
            memberId = memberInfo.memberId,
            answerList = request.answerList,
        )
        createQuestionAnswerUseCase.createQuestionAnswer(command)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
