package self.study.sels.model.member

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class MemberAuthenticationRedisRepository(
    private val stringRedisTemplate: StringRedisTemplate,
) {
    fun incrementMemberAuthenticationRequestCount(phone: String): Int {
        val redisKey = "${phone}_count"
        val count = stringRedisTemplate.opsForValue().increment(redisKey, 1L)?.toInt() ?: 0

        val isNewKey = stringRedisTemplate.getExpire(redisKey) == -1L
        if (isNewKey) {
            stringRedisTemplate.expire(redisKey, Duration.ofDays(1L))
        }

        return count
    }

    fun saveMemberAuthenticationCode(phone: String, authenticationCode: String) {
        stringRedisTemplate.opsForValue().set(phone, authenticationCode, Duration.ofMinutes(5L))
    }
}
