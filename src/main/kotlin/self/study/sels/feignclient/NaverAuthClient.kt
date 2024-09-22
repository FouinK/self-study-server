package self.study.sels.feignclient

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "NaverAuthClient", url = "\${naver.auth-url}")
interface NaverAuthClient {
    @GetMapping("/oauth2.0/authorize")
    fun token()
}
