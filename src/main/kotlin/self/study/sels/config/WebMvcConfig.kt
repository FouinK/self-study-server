package self.study.sels.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import self.study.sels.config.interceptor.UpdateAppInfoInterceptor
import self.study.sels.config.interceptor.UserAuthInterceptor

@Configuration
class WebMvcConfig(
    private val userAuthInterceptor: UserAuthInterceptor,
    private val updateAppInfoInterceptor: UpdateAppInfoInterceptor,
) : WebMvcConfigurer {
    private val excludeUrlList = arrayOf("/sels/api/u/member/**")
    private val userUrlList = arrayOf("/sels/api/u/**")
    private val appUrlList = arrayOf("/api/**", *userUrlList)

    override fun addCorsMappings(registry: CorsRegistry) {
        // View
        registry
            .addMapping("/sels/api/**")
            .allowedOrigins(
                "http://localhost:3000",
                "http://192.168.123.105:3000",
                "https://brandsite-alpha.cleaninglab.co.kr",
                "https://www.cleaninglab.co.kr",
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
