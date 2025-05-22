package self.study.sels.userapi.bookcase.adpater.`in`

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
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
import self.study.sels.userapi.bookcase.application.action.CreateBookcaseRequestDto
import self.study.sels.userapi.bookcase.application.action.CreateBookcaseResponseDto
import self.study.sels.userapi.bookcase.application.action.UpdateBookcaseRequestDto
import self.study.sels.userapi.bookcase.application.action.UpdateBookcaseResponseDto
import self.study.sels.userapi.bookcase.application.port.`in`.CreateBookcaseCommand
import self.study.sels.userapi.bookcase.application.port.`in`.CreateBookcaseUseCase
import self.study.sels.userapi.bookcase.application.port.`in`.GetBookcaseCommand
import self.study.sels.userapi.bookcase.application.port.`in`.GetBookcaseListCommand
import self.study.sels.userapi.bookcase.application.port.`in`.GetBookcaseListUseCase
import self.study.sels.userapi.bookcase.application.port.`in`.GetBookcaseUseCase
import self.study.sels.userapi.bookcase.application.port.`in`.UpdateBookcaseCommand
import self.study.sels.userapi.bookcase.application.port.`in`.UpdateBookcaseUseCase

@RestController
@RequestMapping("/sels/api/u/bookcase")
class BookcaseController(
    private val getBookCaseListUseCase: GetBookcaseListUseCase,
    private val createBookCaseUseCase: CreateBookcaseUseCase,
    private val getBookcaseUseCase: GetBookcaseUseCase,
    private val updateBookcaseUseCase: UpdateBookcaseUseCase,
    private val memberInfo: MemberInfo,
) {
    @GetMapping
    fun list(
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): ResponseEntity<Any> {
        val command = GetBookcaseListCommand(
            memberId = memberInfo.memberId,
            pageable = pageable,
        )
        return ResponseEntity.ok(getBookCaseListUseCase.list(command = command))
    }

    @GetMapping("/{bookcaseId}")
    fun detail(
        @PathVariable("bookcaseId") bookcaseId: Int,
    ): ResponseEntity<Any> {
        val command = GetBookcaseCommand(
            bookcaseId = bookcaseId,
            memberId = memberInfo.memberId,
        )

        return ResponseEntity.ok(getBookcaseUseCase.detail(command))
    }

    @PostMapping
    fun create(
        @RequestBody @Valid request: CreateBookcaseRequestDto,
    ): ResponseEntity<Any> {
        val command = CreateBookcaseCommand(
            name = request.name,
            color = request.color,
            memberId = memberInfo.memberId,
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(
            CreateBookcaseResponseDto(
                bookcaseId = createBookCaseUseCase.create(command),
            ),
        )
    }

    @PutMapping
    fun update(
        @RequestBody @Valid request: UpdateBookcaseRequestDto,
    ): ResponseEntity<Any> {
        val command = UpdateBookcaseCommand(
            bookcaseId = request.bookcaseId,
            name = request.name,
            memberId = memberInfo.memberId,
        )
        return ResponseEntity.ok(
            UpdateBookcaseResponseDto(
                name = updateBookcaseUseCase.update(command),
            ),
        )
    }
}
