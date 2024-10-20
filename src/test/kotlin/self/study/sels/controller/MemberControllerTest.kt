package self.study.sels.controller

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import self.study.sels.IntegrationTest

@AutoConfigureMockMvc
class MemberControllerTest(
    private val mockMvc: MockMvc,
) : IntegrationTest() {

    @Test
    fun `휴대폰번호 형식이 올바르지 않을경우 예외가 발생한다`() {
        // given
        val invalidPhoneRequest = """
            {
                "phone": "0101234123"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/sels/api/u/member/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidPhoneRequest),
        )
            .andExpect(status().isBadRequest)
    }
}
