package self.study.sels.userapi.answer.adpater.`in`

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import self.study.sels.config.auth.MemberInfo
import self.study.sels.userapi.answer.application.port.`in`.SolveQuestionCommand
import self.study.sels.userapi.answer.application.port.`in`.SolveQuestionUseCase
import self.study.sels.userapi.question.application.action.SolveQuestionRequestDto

@RestController
@RequestMapping("/sels/api/u/answer")
class AnswerController(
    private val memberInfo: MemberInfo,
    private val solveQuestionUseCase: SolveQuestionUseCase,
) {
    @PostMapping("/{questionId}")
    fun solveQuestion(
        @PathVariable("questionId") questionId: Int,
        @RequestBody request: SolveQuestionRequestDto,
    ) {
        val command = SolveQuestionCommand(
            questionId = questionId,
            answerId = request.answerId,
            answer = request.answer,
            memberId = memberInfo.memberId,
        )
        solveQuestionUseCase.execute(command = command)
    }
}
