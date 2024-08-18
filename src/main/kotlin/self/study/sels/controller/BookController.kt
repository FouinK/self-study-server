package self.study.sels.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import self.study.sels.config.auth.MemberInfo
import self.study.sels.controller.dto.CreateBookRequestDto

@RestController
@RequestMapping("/sels/api/u/book")
class BookController(
    private val memberInfo: MemberInfo,
) {
    @PostMapping
    fun create(
        @RequestBody request: CreateBookRequestDto,
    ): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
