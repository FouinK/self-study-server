package self.study.sels.model.bookcase

import org.springframework.stereotype.Component

@Component
class BookcaseFactory {
    fun create(command: Command): Bookcase =
        Bookcase(
            name = command.name,
            memberId = command.memberId,
        )

    data class Command(
        val name: String,
        val memberId: Int,
    )
}
