package self.study.sels.controller

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
import self.study.sels.application.book_case.port.`in`.*
import self.study.sels.config.auth.MemberInfo
import self.study.sels.controller.dto.CreateBookcaseRequestDto
import self.study.sels.controller.dto.CreateBookcaseResponseDto
import self.study.sels.controller.dto.UpdateBookcaseRequestDto
import self.study.sels.controller.dto.UpdateBookcaseResponseDto

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
