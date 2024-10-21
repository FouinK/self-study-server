package self.study.sels.util

object AuthCodeUtil {
    fun generateAuthenticationCode(): String {
        return (1000..9999).random().toString()
    }
}
