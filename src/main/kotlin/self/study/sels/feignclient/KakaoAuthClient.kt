package self.study.sels.feignclient

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kakaoAuthClient", url = "\${kakao.auth-url}")
interface KakaoAuthClient {
    @PostMapping("/oauth/token", consumes = ["application/x-www-form-urlencoded"])
    fun token(
        @RequestHeader("Content-Type") contentType: String = "application/json;charset=utf-8",
        @RequestParam("grant_type") grantType: String = "authorization_code",
        @RequestParam("client_id") clientId: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("code") code: String,
    ): OauthTokenResponseDto
}

class OauthTokenRequestDto(
    val grant_type: String = "authorization_code",
    val client_id: String,
    val redirect_uri: String,
    val code: String,
)

class OauthTokenResponseDto(
    val token_type: String,
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val refresh_token_expires_in: Int,
)
