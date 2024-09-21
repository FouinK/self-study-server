package self.study.sels

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.system.measureNanoTime

@SpringBootTest
class SelsApplicationTests {

    @Test
    fun contextLoads() {
        main()
    }

    fun main(): Unit = runBlocking {
        val job = launch {
            try {
                delay(1000)
            } catch (e: CancellationException) {
                //아무것도 하지 않는다
            }
            printWithThread("delay에 의해 취소되지 않았다.")
        }

        delay(100)
        printWithThread("취소 실행햇음")
        job.cancel()
    }

    fun example10(): Unit = runBlocking {
        // 협력하는 코루틴이 없어도 다른 스레드에서 취소명령을 먼저 실행해버리니까 가능
        // Dispatchers.Default는 다른 스레드에서 돌리겠다.
        val job = launch(Dispatchers.Default) {
            var i = 1
            var nextPrintTime = System.currentTimeMillis()
            while (i <= 5) {
                if (nextPrintTime <= System.currentTimeMillis()) {
                    printWithThread("${i++} 번째 출력 !")
                    nextPrintTime += 1000L
                }

                if (!isActive) {
                    throw CancellationException()
                }
            }
        }

        delay(100)
        job.cancel()
    }

    fun example9(): Unit = runBlocking {
        // 협력하는 코루틴 suspend fun 이 안에 없어서 job.cancel()이 안됨 (yield()나 delay())
        val job = launch {
            var i = 1
            var nextPrintTime = System.currentTimeMillis()
            while (i <= 5) {
                if (nextPrintTime <= System.currentTimeMillis()) {
                    printWithThread("${i++} 번째 출력 !")
                    nextPrintTime += 1000L
                }
            }
        }

        delay(100)
        job.cancel()
    }

    fun example8(): Unit = runBlocking {
        val job1 = launch {
            //delay에 suspend fun 이 있어서 job.cancel의 명령을 받을 수 있음
            delay(1000)
            printWithThread("job1")
        }
        val job2 = launch {
            delay(1000)
            printWithThread("job2")
        }

        delay(100)
        job1.cancel()
    }

    fun example7(): Unit = runBlocking {
        val time = measureNanoTime {
            val job1 = async(start = CoroutineStart.LAZY) { apiCall1() }
            val job2 = async(start = CoroutineStart.LAZY) { apiCall2() }

            job1.start()
            job2.start()

            // LAZY 옵션이 있으면 여기서 끝날때까지 기다리면서 시작하지만 위의 start()로 미리 실행해버리면 LAZY 옵션이 없는 것처럼 할 수 있음
            printWithThread(job1.await() + job2.await())
        }

        printWithThread("소요시간 : $time ms")
    }

    fun example6(): Unit = runBlocking {
        val time = measureNanoTime {
            // 콜백을 사용하지 않고 동기방식의 코드를 작성 가능 (실제로는 비동기(?))
            val job1 = async { apiCall1() }
            val job2 = async { apiCall2() }
            printWithThread(job1.await() + job2.await())
        }

        printWithThread("소요시간 : $time ms")
    }

    suspend fun apiCall1(): Int {
        delay(1000)
        return 1
    }

    suspend fun apiCall2(): Int {
        delay(1000)
        return 2
    }

    fun example5(): Unit = runBlocking {
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
