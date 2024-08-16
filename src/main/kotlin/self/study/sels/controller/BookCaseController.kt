package self.study.sels.controller

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.book_case.port.`in`.CreateBookCaseCommand
import self.study.sels.application.book_case.port.`in`.CreateBookCaseUseCase
import self.study.sels.application.book_case.port.`in`.GetBookCaseListCommand
import self.study.sels.application.book_case.port.`in`.GetBookCaseListUseCase
import self.study.sels.config.auth.MemberInfo
import self.study.sels.controller.dto.CreateBookCaseRequestDto
import self.study.sels.controller.dto.CreateBookCaseResponseDto

@RestController
@RequestMapping("/sels/api/u/book-case")
class BookCaseController(
    private val getBookCaseListUseCase: GetBookCaseListUseCase,
    private val createBookCaseUseCase: CreateBookCaseUseCase,
    private val memberInfo: MemberInfo,
) {
    @GetMapping
    fun list(
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): ResponseEntity<Any> {
        val command =
            GetBookCaseListCommand(
                memberId = memberInfo.memberId,
                pageable = pageable,
            )
        return ResponseEntity.ok(getBookCaseListUseCase.list(command = command))
    }

    @PostMapping
    fun create(
        @RequestBody request: CreateBookCaseRequestDto,
    ): ResponseEntity<Any> {
        val command =
            CreateBookCaseCommand(
                name = request.name,
                memberId = memberInfo.memberId,
            )

        return ResponseEntity.status(HttpStatus.CREATED).body(
            CreateBookCaseResponseDto(
                bookCaseId = createBookCaseUseCase.create(command),
            ),
        )
    }
}
