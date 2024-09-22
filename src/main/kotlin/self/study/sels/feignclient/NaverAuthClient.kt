package self.study.sels.feignclient

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "NaverAuthClient", url = "\${naver.auth-url}")
interface NaverAuthClient {
    @GetMapping("/oauth2.0/authorize")
    fun authorize(
        @RequestParam("response_type") responseType: String = "code",
        @RequestParam("client_id") clientId: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("state") state: String,
    )
}
