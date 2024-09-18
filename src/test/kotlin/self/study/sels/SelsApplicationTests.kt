package self.study.sels

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SelsApplicationTests {

    @Test
    fun contextLoads() {
        main()
    }

    fun main(): Unit = runBlocking {
        // async는 launch와 다르게 결과를 반환할 수 있음
        val job = async {
            3 + 5
        }

        // await는 async의 결과를 가져오는 함수
        val eight = job.await()

        printWithThread(eight)
    }

    fun example4(): Unit = runBlocking {
        val job1 = launch {
            delay(1000)
            printWithThread("job 1")
        }

        job1.join() // 없으면 중첩돼서 대기하고 있다면 직관적 코드 진행처럼 1초 대기 후에 아래 코루틴이 실행됨

        val job2 = launch {
            delay(1000)
            printWithThread("job 2")
        }
    }

    fun example3(): Unit = runBlocking {
        val job = launch {
            (1..5).forEach {
                printWithThread(it)
                delay(500)
            }
        }

        delay(1_000L)
        job.cancel()
    }

    fun example2(): Unit = runBlocking {
        val job = launch(start = CoroutineStart.LAZY) {
            printWithThread("Hello Launch")
        }

        delay(1_000L)

        job.start()
    }

    suspend fun newRoutine() {
        val num1 = 1
        val num2 = 2
        yield()
        println(num1 + num2)
    }

    fun printWithThread(str: Any) {
        println("[${Thread.currentThread().name}] $str")
    }
}
