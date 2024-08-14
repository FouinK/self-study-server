package self.study.sels.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.book_case.port.`in`.CreateBookCaseCommand
import self.study.sels.application.book_case.port.`in`.CreateBookCaseUseCase
import self.study.sels.application.book_case.port.`in`.GetBookCaseListUseCase
import self.study.sels.config.auth.MemberInfo
import self.study.sels.controller.dto.BookCaseCreateRequestDto

@RestController
@RequestMapping("/sels/api/u")
class BookCaseController(
    private val getBookCaseListUseCase: GetBookCaseListUseCase,
    private val createBookCaseUseCase: CreateBookCaseUseCase,
    private val memberInfo: MemberInfo,
) {
    @GetMapping
    fun list(): ResponseEntity<Any> {
        getBookCaseListUseCase.list(memberInfo.memberId)
        return ResponseEntity.ok("")
    }

    @PostMapping
    fun create(
        @RequestBody request: BookCaseCreateRequestDto,
    ): ResponseEntity<Any> {
        val command =
            CreateBookCaseCommand(
                name = request.name,
                memberId = memberInfo.memberId,
            )
        createBookCaseUseCase.create(command)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
