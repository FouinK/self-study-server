package self.study.sels.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import self.study.sels.application.book_case.port.`in`.GetBookCaseListUseCase
import self.study.sels.auth.MemberInfo

@RestController
@RequestMapping("/sels/api/u")
class BookCaseController(
    private val getBookCaseListUseCase: GetBookCaseListUseCase,
    private val memberInfo: MemberInfo,
) {
    @GetMapping
    fun list(): ResponseEntity<Any> {
        getBookCaseListUseCase.list(memberInfo.memberId)
        return ResponseEntity.ok("")
    }
}
