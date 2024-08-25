package self.study.sels.service

import org.springframework.stereotype.Service
import self.study.sels.feignclient.DiscordWebhookClient
import self.study.sels.feignclient.ErrorMsg
import self.study.sels.util.logger

@Service
class WatchTowerService(
    private val discordWebhookClient: DiscordWebhookClient,
) {
    fun sendErrorNotification(errorMsg: ErrorMsg) {
        try {
            discordWebhookClient.send(errorMsg)
        } catch (e: Exception) {
            logger().error("디스코드 알림 전송 실패: ${e.message}")
        }
    }
}
