package chap_06_协程

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

/**
 * Created by wangjiao on 2020/8/26.
 * description:
 */
/** select表达式 同时等待多个挂起函数，并选择第一个可用的*/

/** 在通道中select */
fun CoroutineScope.fizz()=produce<String> {
    while (true){
        delay(300)
        send("Fizz")
    }
}

fun CoroutineScope.buzz() = produce<String> {
    while (true){
        delay(500)
        send("Buzz")
    }
}

suspend fun selectFizzBuzz(fizz:ReceiveChannel<String>,buzz:ReceiveChannel<String>){
    select<Unit>{
        fizz.onReceive{value ->
            println("fizz -> '$value ")
        }
        buzz.onReceive{ value ->
            println("buzz -> '$value")
        }
    }
}

fun main()= runBlocking {
    val fizz = fizz()
    val buzz = buzz()
    repeat(7){
        selectFizzBuzz(fizz,buzz)
    }
    coroutineContext.cancelChildren()
}