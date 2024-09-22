package self.study.sels.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class YmlProperties(
    @Value("\${kakao.client-key}")
    val kakaoClientKey: String,
    @Value("\${naver.client-key}")
    val naverClientKey: String,
)
