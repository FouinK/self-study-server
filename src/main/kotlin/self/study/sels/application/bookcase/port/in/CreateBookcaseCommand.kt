package self.study.sels.application.bookcase.port.`in`

import self.study.sels.model.bookcase.Bookcase

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
