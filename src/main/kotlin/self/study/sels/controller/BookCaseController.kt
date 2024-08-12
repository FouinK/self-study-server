package self.study.sels.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.book_case.port.`in`.GetBookCaseListUseCase

@RestController
@RequestMapping("/sels/u")
class BookCaseController(
    private val getBookCaseListUseCase: GetBookCaseListUseCase,
) {
    @GetMapping
    fun list(
        @RequestParam("memberId") memberId: Int,
    ): ResponseEntity<Any> {
        getBookCaseListUseCase.list(memberId)
        return ResponseEntity.ok("")
    }
}
