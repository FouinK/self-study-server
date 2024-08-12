package self.study.sels.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import self.study.sels.interceptor.UpdateAppInfoInterceptor
import self.study.sels.interceptor.UserAuthInterceptor

@Configuration
class WebMvcConfig(
    private val userAuthInterceptor: UserAuthInterceptor,
    private val updateAppInfoInterceptor: UpdateAppInfoInterceptor,
) : WebMvcConfigurer {
    private val excludeUrlList =
        arrayOf(
            "/sels/api/u/faq/**",
        )
    private val userUrlList = arrayOf("/sels/api/u/**")
    private val appUrlList = arrayOf("/api/**", *userUrlList)

    override fun addCorsMappings(registry: CorsRegistry) {
        // CY STORE
        registry
            .addMapping("/sels/cy-store/**")
            .allowedOrigins(
                "http://localhost:3007",
                "http://127.0.0.1:3007",
                "http://192.168.0.44:3007", // huni office
                "http://192.168.0.2:3007", // huni ho
                "http://192.168.0.96:3007", // juan office
                "http://192.168.0.169:3007", // juan office two
                "http://192.168.35.45:3007", // juan house
                "http://192.168.219.105:3007", // juan house two
                "https://brandsite-alpha.cleaninglab.co.kr",
                "https://brandsite-beta.cleaninglab.co.kr",
                "https://brandsite-alpha-huni.cleaninglab.co.kr",
                "https://www.cleaninglab.co.kr",
                "https://cleaninglab.co.kr",
                "https://brandsite.cleaninglab.co.kr",
                "https://www.cy-store.co.kr",
                "https://cy-store.co.kr",
            ).allowedMethods("*")
            .allowCredentials(true)

        // View
        registry
            .addMapping("/sels/api/**")
            .allowedOrigins(
                "http://localhost:3010",
                "http://127.0.0.1:3010",
                "http://192.168.0.141:3010",
                "http://192.168.0.40:3010",
                "http://192.168.0.205:3010",
                "http://192.168.35.45:3010", // juan house
                "https://view-alpha.cleaninglab.co.kr",
                "https://view-beta.cleaninglab.co.kr",
                "https://view.cleaninglab.co.kr",
                "https://view-alpha-jayd.cleaninglab.co.kr",
            ).allowedMethods("*")
            .allowCredentials(true)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(userAuthInterceptor)
            .order(1)
            .addPathPatterns(*userUrlList)
            .excludePathPatterns(*excludeUrlList)

        registry
            .addInterceptor(updateAppInfoInterceptor)
            .order(2)
            .addPathPatterns(*appUrlList)
            .excludePathPatterns(*excludeUrlList)
    }
}
