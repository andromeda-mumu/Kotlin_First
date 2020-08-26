package chap_06_协程

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/**
 * Created by wangjiao on 2020/8/25.
 * description:
 */
/** 延期的值提供了一种便捷的方法使单个值在多个协程之间进行相互传输，通道提供了一种在流中传输值的方法*/

/** 通道基础
 *  Channel和BlocingQueue非常相似的概念。其中一个不同时它代替了阻塞的put操作并提供了挂起send，替代了take操作提供了挂起receive
 * */
//fun main()= runBlocking {
//    val channel = Channel<Int>()
//    launch {
//        for(x in 1..5) channel.send(x*x)
//    }
//    repeat(5){ println(channel.receive())}
//    println("done")
//}

/** 关闭与迭代通道
 * 和队列不同，一个通道可以通过被关闭来表明没有更多的元素将会进入通道。在接收者中可以定期的使用for循环从通道中接收元素
 * 迭代停止，保证所有先前发送出去的元素都要在通道关闭前被接受到
 * */
//fun main()= runBlocking {
//    val channel = Channel<Int>()
//    launch {
//        for(x in 1..5)channel.send(x*x)
//        channel.close()
//    }
//    for( y in channel) println(y)
//    println("done")
//}

/** 构建通道生产者
 * produce  comsumEach 生产者 消费者模式
 * */
//fun CoroutineScope.produceSquares():ReceiveChannel<Int> = produce{
//    for(x in 1..5) send(x*x)
//}
//
//fun main()= runBlocking {
//    val squares = produceSquares()
//    squares.consumeEach { println(it) }
//    println("done")
//}

/** 管道 是一种一个协程在流中开始生产无穷多个元素的模式,并且另一个或多个协程开始消费这些流，做一些操作，产生额外的结果
 * 所有创建了协程的函数被定义了CoroutineScope的扩展。因此我们可以依靠结构化并发来确保没有常驻在我们应用程序中的全局协程。
 * */
//fun CoroutineScope.produceNumbers()=produce<Int>{
//    var x =1
//    while(true) send(x++)
//}
//fun CoroutineScope.square(numbers:ReceiveChannel<Int>):ReceiveChannel<Int> = produce {
//    for(x in numbers) send(x*x)
//}
//
//fun main()= runBlocking {
//    val numbers = produceNumbers()
//    val square = square(numbers)
//    repeat(5){
//        println(square.receive())
//    }
//    println("done")
//    coroutineContext.cancelChildren()//取消子协程
//}

/** 使用管道的素数*/
//fun CoroutineScope.numbersFrom(start:Int) = produce<Int> {
//    var x= start
//    while (true)send(x++)
//}
//fun CoroutineScope.filter(numbers:ReceiveChannel<Int>,prime:Int)=produce<Int> {
//    for(x in numbers) if(x%prime!=0)send (x)
//}
//fun main()= runBlocking {
//    var cur = numbersFrom(2)
//    repeat(10) {
//        var prime = cur.receive()
//        println(prime)
//        cur = filter(cur, prime)
//    }
//    coroutineContext.cancelChildren()//取消所有子协程
//}


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