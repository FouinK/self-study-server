package self.study.sels.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.book.port.`in`.CreateBookCommand
import self.study.sels.application.book.port.`in`.CreateBookUseCase
import self.study.sels.config.auth.MemberInfo
import self.study.sels.controller.dto.CreateBookRequestDto
import self.study.sels.controller.dto.CreateBookResponseDto

@RestController
@RequestMapping("/sels/api/u/book")
class BookController(
    private val memberInfo: MemberInfo,
    private val createBookUseCase: CreateBookUseCase,
) {
    @PostMapping
    fun create(
        @RequestBody request: CreateBookRequestDto,
    ): ResponseEntity<Any> {
        val command =
            CreateBookCommand(
                bookcaseId = request.bookcaseId,
                name = request.name,
                memberId = memberInfo.memberId,
            )

        return ResponseEntity.status(HttpStatus.CREATED).body(
            CreateBookResponseDto(
                bookId = createBookUseCase.create(command),
            ),
        )
    }
}
