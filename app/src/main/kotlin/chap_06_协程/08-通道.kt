package chap_06_协程

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*


/**
 * Created by mumu on 2020/8/25.
 * 功能描述：
 */
/** 扇出  多个协程会接收多个管道，在它们之间进行分布式工作 */
//fun CoroutineScope.produceNumbers() = produce<Int>{
//    var x= 1
//    while (true){
//        send(x++)
//        delay(100)
//    }
//}
//
//fun CoroutineScope.launchProcessor(id:Int, channel: ReceiveChannel<Int>)=launch{
//    for(msg in channel){
//        println("processor #$id received $msg")
//    }
//}
//fun main()= runBlocking {
//    val producer = produceNumbers()
//    repeat(5){launchProcessor(it,producer)}
//    delay(950)
//    producer.cancel()
//}

/** 扇入  多个协程同时发送同一个通道。让我们创建一个字符串通道，
 * 和一个在这个通道中以指定的延迟反复发送一个指定字符串的挂起函数*/
//suspend fun sendString(channel:SendChannel<String>,s:String,time:Long){
//    while (true){
//        delay(time)
//        channel.send(s)
//    }
//}
//fun main()= runBlocking {
//    val channel = Channel<String>()
//    launch { sendString(channel,"foo",200L) }
//    launch { sendString(channel,"BAR",500L) }
//    repeat(6){
//        println(channel.receive())
//    }
//    coroutineContext.cancelChildren()
//}

/** 带缓冲的通道 */
//fun main()= runBlocking {
//    val channel = Channel<Int>(4)
//    val sender = launch {
//        repeat(10){
//            println("sending $it")
//            channel.send(it)//在缓冲区被沾满时挂起
//        }
//    }
//    delay(1000)
//    sender.cancel()
//}

/** 通道是公平的   遵守先进先出*/
//data class Ball(var hits:Int)
//suspend fun player(name:String,table:Channel<Ball>){
//    for(ball in table){ //从循环中接球
//        ball.hits++
//        println("$name $ball")
//        delay(300)
//        table.send(ball)//并将球发送回去
//    }
//}
//fun main()= runBlocking {
//    val table = Channel<Ball>()
//    launch { player("ping",table)  }
//    launch { player("pong",table) }
//    table.send(Ball(0))
//    delay(1000)
//    coroutineContext.cancelChildren()
//}

/** 计时器通道  */
fun main()= runBlocking<Unit> {
    val tickerChannel = ticker(delayMillis = 100,initialDelayMillis = 0)//创建计时通道
    var nextElement = withTimeoutOrNull(1){tickerChannel.receive()}
    println("initial element is available immediately : $nextElement")
    nextElement = withTimeoutOrNull(50) {tickerChannel.receive()}
    println("Next element is not ready in 50ms $nextElement")
    nextElement = withTimeoutOrNull(60){tickerChannel.receive()}
    println("Next element is ready in 100 ms :$nextElement")
    println("consumer pauses for 150ms")
    delay(150)
    nextElement = withTimeoutOrNull(1){tickerChannel.receive()}
    println("next element is available immediately after large consumer delay :$nextElement")
    nextElement = withTimeoutOrNull(60){tickerChannel.receive()}
    println("next element is readt 50ms after consume pause in 150ms $nextElement")
    tickerChannel.cancel()
}