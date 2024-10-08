package self.study.sels.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.question.port.`in`.*
import self.study.sels.config.auth.MemberInfo
import self.study.sels.controller.dto.CreateQuestionAndAnswerRequestDto
import self.study.sels.controller.dto.UpdateQuestionAndAnswerRequestDto

@RestController
@RequestMapping("/sels/api/u/question")
class QuestionController(
    private val memberInfo: MemberInfo,
    private val getQuestionUseCase: GetQuestionUseCase,
    private val createQuestionAndAnswerUseCase: CreateQuestionAndAnswerUseCase,
    private val updateQuestionAndAnswerUseCase: UpdateQuestionAndAnswerUseCase,
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
    fun createQuestionAndAnswer(
        @RequestBody @Valid request: CreateQuestionAndAnswerRequestDto,
    ): ResponseEntity<Any> {
        val command = CreateQuestionAndAnswerCommand(
            bookId = request.bookId,
            question = request.question,
            memberId = memberInfo.memberId,
            answerList = request.answerList,
        )
        createQuestionAndAnswerUseCase.createQuestionAndAnswer(command)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping
    fun updateQuestionAnswer(
        @RequestBody @Valid request: UpdateQuestionAndAnswerRequestDto,
    ): ResponseEntity<Any> {
        val command = UpdateQuestionAndAnswerCommand(
            questionId = request.questionId,
            question = request.question,
            answerList = request.answerList,
            memberId = memberInfo.memberId,
        )
        updateQuestionAndAnswerUseCase.update(command)
        return ResponseEntity.ok("")
    }
}
