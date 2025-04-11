package self.study.sels.util

import java.util.*

object AuthCodeUtil {
    fun generateAuthenticationCode() = (1000..9999).random().toString()

    fun generateAuthToken() = UUID.randomUUID().toString()
}
