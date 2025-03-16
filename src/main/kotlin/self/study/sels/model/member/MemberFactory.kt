package self.study.sels.model.member

import org.springframework.stereotype.Component

@Component
class MemberFactory {
    fun create(command: Command) =
        Member(
            authToken = command.authToken,
            pushYn = command.pushYn,
            marketingYn = command.marketingYn,
            phone = command.phone,
        )

    data class Command(
        val authToken: String,
        val pushYn: Boolean,
        val marketingYn: Boolean,
        val phone: String,
    )
}
