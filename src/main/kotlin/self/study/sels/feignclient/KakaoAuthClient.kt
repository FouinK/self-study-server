package self.study.sels.feignclient

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "kakaoAuthClient", url = "\${kakao.auth-url}")
interface KakaoAuthClient {
    @GetMapping("/oauth/authorize")
    fun token()
}
