package self.study.sels.userapi.book.adpater.`in`

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
import self.study.sels.config.auth.MemberInfo
import self.study.sels.userapi.book.application.action.CreateBookRequestDto
import self.study.sels.userapi.book.application.action.CreateBookResponseDto
import self.study.sels.userapi.book.application.action.UpdateBookRequestDto
import self.study.sels.userapi.book.application.action.UpdateBookResponseDto
import self.study.sels.userapi.book.application.port.`in`.CreateBookCommand
import self.study.sels.userapi.book.application.port.`in`.CreateBookUseCase
import self.study.sels.userapi.book.application.port.`in`.GetBookCommand
import self.study.sels.userapi.book.application.port.`in`.GetBookUseCase
import self.study.sels.userapi.book.application.port.`in`.StartSolveBookCommand
import self.study.sels.userapi.book.application.port.`in`.StartSolveBookUseCase
import self.study.sels.userapi.book.application.port.`in`.UpdateBookCommand
import self.study.sels.userapi.book.application.port.`in`.UpdateBookUseCase

@RestController
@RequestMapping("/sels/api/u/book")
class BookController(
    private val memberInfo: MemberInfo,
    private val getBookUseCase: GetBookUseCase,
    private val createBookUseCase: CreateBookUseCase,
    private val updateBookUseCase: UpdateBookUseCase,
    private val startSolveBookUseCase: StartSolveBookUseCase,
) {
    @GetMapping("/{bookId}")
    fun detail(
        @PathVariable("bookId") bookId: Int,
    ): ResponseEntity<Any> {
        val command = GetBookCommand(
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
        val command = CreateBookCommand(
            bookcaseId = request.bookcaseId,
            name = request.name,
            color = request.color,
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
        val command = UpdateBookCommand(
            bookId = request.bookId,
            name = request.name,
            memberId = memberInfo.memberId,
        )
        return ResponseEntity.ok(
            UpdateBookResponseDto(
                name = updateBookUseCase.update(command),
            ),
        )
    }

    @PostMapping("/{bookId}/solve")
    fun startSolveBook(
        @PathVariable("bookId") bookId: Int,
    ) {
        val command = StartSolveBookCommand(
            bookId = bookId,
            memberId = memberInfo.memberId,
        )
        startSolveBookUseCase.execute(command)
    }
}
