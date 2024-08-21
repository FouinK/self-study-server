package self.study.sels.application.book_case.port.`in`

import self.study.sels.model.book_case.Bookcase

class CreateBookcaseCommand(
    val name: String,
    val memberId: Int,
) {
    fun toEntity(): Bookcase {
        return Bookcase(
            name = this.name,
            memberId = this.memberId,
        )
    }
}
