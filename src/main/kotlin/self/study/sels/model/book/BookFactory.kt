package self.study.sels.model.book

import org.springframework.stereotype.Component

@Component
class BookFactory {
    fun create(command: Command): Book =
        Book(
            bookcaseId = command.bookcaseId,
            name = command.name,
            memberId = command.memberId,
        )

    data class Command(
        val memberId: Int,
        val bookcaseId: Int,
        val name: String,
    )
}
