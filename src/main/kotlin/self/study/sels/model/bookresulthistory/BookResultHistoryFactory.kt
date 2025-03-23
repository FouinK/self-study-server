package self.study.sels.model.bookresulthistory

import org.springframework.stereotype.Component

@Component
class BookResultHistoryFactory {
    fun create(
        command: Command
    ): BookResultHistory =
        BookResultHistory(
            bookId = command.bookId,
            memberId = command.memberId,
            bookcaseId = command.bookcaseId,
            name = command.name,
        )

    class Command(
        val bookId: Int,
        val memberId: Int,
        val bookcaseId: Int,
        val name: String,
    )
}
