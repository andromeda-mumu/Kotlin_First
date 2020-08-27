package chap_06_协程

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import java.util.*

/**
 * Created by wangjiao on 2020/8/26.
 * description:
 */
/** select表达式 同时等待多个挂起函数，并选择第一个可用的*/

/** 在通道中select */
//fun CoroutineScope.fizz()=produce<String> {
//    while (true){
//        delay(300)
//        send("Fizz")
//    }
//}
//
//fun CoroutineScope.buzz() = produce<String> {
//    while (true){
//        delay(500)
//        send("Buzz")
//    }
//}
//
//suspend fun selectFizzBuzz(fizz:ReceiveChannel<String>,buzz:ReceiveChannel<String>){
//    select<Unit>{
//        fizz.onReceive{value ->
//            println("fizz -> '$value ")
//        }
//        buzz.onReceive{ value ->
//            println("buzz -> '$value")
//        }
//    }
//}
//
//fun main()= runBlocking {
//    val fizz = fizz()
//    val buzz = buzz()
//    repeat(7){
//        selectFizzBuzz(fizz,buzz)
//    }
//    coroutineContext.cancelChildren()
//}


/** select 延迟值
 * 延迟值可以使用onAwait字句查询。=========不懂
 * */
//fun CoroutineScope.asyncString(time:Int)=async{
//    delay(time.toLong())
//    "waited for $time ms"
//}
//
//fun CoroutineScope.asyncStringsList():List<Deferred<String>>{
//    val random = Random(3)
//    return List(12){asyncString(random.nextInt(1000))}
//}
//fun main()= runBlocking {
//    val list = asyncStringsList()
//    list.forEach { value -> println(value) }
//    val result=select<String>{
//        list.withIndex().forEach{(index,deferred)->
//            deferred.onAwait{
//                answer->
//                "deferred $index produced answer '$answer"
//            }
//        }
//    }
//    println(result)
//    val countActive = list.count{it.isActive}
//    println("$countActive coroutines are still active ")
//}


/** 在延迟通道上切换 */
fun CoroutineScope.switchMapDeferreds(input:ReceiveChannel<Deferred<String>>) = produce<String>{
    var current = input.receive()
    while(isActive){//循环直到被取消或关闭
        val next = select<Deferred<String>?> {//从这个select中，返回下一个延迟值或null
            input.onReceiveOrNull{update ->
                update //替换下一个等待值
            }
            current.onAwait{ value ->
                send(value)//发送当前延迟生成的值
                input.receiveOrNull() //然后使用输入通道得到下一个延迟值
            }
        }
        if(next==null){
            println("channel was closed")
            break
        }else{
            current = next
        }
    }
}

fun CoroutineScope.asyncString(str:String,time:Long)= async{
    delay(time)
    str
}
fun main()= runBlocking {
    val chan = Channel<Deferred<String>>()
    launch {
        for(s in switchMapDeferreds(chan))
            println(s)
    }
    chan.send(asyncString("BEGIN",100))
    delay(200)//充足的时间产生BEGIN
    chan.send(asyncString("SLOW",500))
    delay(100)//不充足的时间产生 SLOW
    chan.send(asyncString("Replace",100))
    delay(500)//在最后一个前给它一点时间
    chan.send(asyncString("END",500))
    delay(1000)//给执行一段时间
    chan.close()
    delay(500)//等待一段时间来让它结束

}