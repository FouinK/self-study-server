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
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.book.port.`in`.*
import self.study.sels.config.auth.MemberInfo
import self.study.sels.controller.dto.CreateBookRequestDto
import self.study.sels.controller.dto.CreateBookResponseDto
import self.study.sels.controller.dto.UpdateBookRequestDto
import self.study.sels.controller.dto.UpdateBookResponseDto

@RestController
@RequestMapping("/sels/api/u/book")
class BookController(
    private val memberInfo: MemberInfo,
    private val createBookUseCase: CreateBookUseCase,
    private val getBookUseCase: GetBookUseCase,
    private val updateBookUseCase: UpdateBookUseCase,
) {
    @GetMapping("/{bookId}")
    fun detail(
        @PathVariable("bookId") bookId: Int,
    ): ResponseEntity<Any> {
        val command =
            GetBookCommand(
                bookId = bookId,
                memberId = memberInfo.memberId,
            )
        return ResponseEntity.ok(
            getBookUseCase.detail(command),
        )
    }

    @PostMapping
    fun create(
        @RequestBody @Valid request: CreateBookRequestDto,
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

    @PutMapping
    fun update(
        @RequestBody @Valid request: UpdateBookRequestDto,
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(
            UpdateBookResponseDto(
                name = updateBookUseCase.update(request),
            ),
        )
    }
}
