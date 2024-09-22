package self.study.sels.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.member.port.`in`.JoinMemberCommand
import self.study.sels.application.member.port.`in`.JoinUseCase
import self.study.sels.application.member.port.`in`.PlatForm
import self.study.sels.controller.dto.JoinMemberRequestDto

@RestController
@RequestMapping("/sels/api/u/member")
class MemberController(
    private val joinUseCase: JoinUseCase,
) {
    @PostMapping("/kakao")
    fun kakaoJoin(
        @RequestBody request: JoinMemberRequestDto,
    ): ResponseEntity<Any> {
        val command = JoinMemberCommand(
            platForm = PlatForm.KAKAO,
            code = request.code,
        )
        joinUseCase.join(command)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/naver")
    fun naverJoin(
        @RequestBody request: JoinMemberRequestDto,
    ): ResponseEntity<Any> {
        val command = JoinMemberCommand(
            platForm = PlatForm.NAVER,
            code = request.code,
        )
        joinUseCase.join(command)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
