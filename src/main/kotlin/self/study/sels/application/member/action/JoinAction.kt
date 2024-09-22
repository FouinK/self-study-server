package self.study.sels.application.member.action

import self.study.sels.annotation.Action
import self.study.sels.application.member.port.`in`.JoinUseCase
import self.study.sels.config.YmlProperties
import self.study.sels.controller.dto.JoinMemberRequestDto
import self.study.sels.controller.dto.PlatForm
import self.study.sels.feignclient.KakaoAuthClient
import self.study.sels.feignclient.NaverAuthClient

@Action
class JoinAction(
    private val kakaoAuthClient: KakaoAuthClient,
    private val naverAuthClient: NaverAuthClient,
    private val ymlProperties: YmlProperties,
) : JoinUseCase {
    override fun join(command: JoinMemberRequestDto) {
        if (command.platForm == PlatForm.KAKAO) {
            kakaoAuthClient.authorize(
                clientId = ymlProperties.kakaoClientKey,
                redirectUri = command.redirectUri,
            )
        } else if (command.platForm == PlatForm.NAVER) {
            naverAuthClient.authorize(
                clientId = ymlProperties.naverClientKey,
                redirectUri = command.redirectUri,
                state = "",
            )
        } else {
        }
    }
}
