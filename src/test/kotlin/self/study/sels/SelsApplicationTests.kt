package self.study.sels

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SelsApplicationTests {

    @Test
    fun contextLoads() {
        main()
    }

    fun main(): Unit = runBlocking {
        println("start")
        launch {
            newRoutine()
        }
        yield()
        println("end")
    }

    suspend fun newRoutine() {
        val num1 = 1
        val num2 = 2
        yield()
        println(num1 + num2)
    }
}
