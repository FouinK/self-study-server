package self.study.sels.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerCommand
import self.study.sels.application.question.port.`in`.CreateQuestionAnswerUseCase
import self.study.sels.config.auth.MemberInfo
import self.study.sels.controller.dto.CreateQuestionAnswerRequestDto

@RestController
@RequestMapping("/sels/api/u/question")
class QuestionController(
    private val memberInfo: MemberInfo,
    private val createQuestionAnswerUseCase: CreateQuestionAnswerUseCase,
) {
    @PostMapping
    fun createQuestionAnswer(
        @RequestBody request: CreateQuestionAnswerRequestDto,
    ): ResponseEntity<Any> {
        val command =
            CreateQuestionAnswerCommand(
                bookId = request.bookId,
                question = request.question,
                multipleChoiceYn = request.multipleChoiceYn,
                memberId = memberInfo.memberId,
                answerList = request.answerList,
            )
        createQuestionAnswerUseCase.createQuestionAnswer(command)
        return ResponseEntity.ok("")
    }
}
