package chap_06_协程

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * Created by wangjiao on 2020/8/17.
 * description:
 */

/** 默认顺序调用*/
suspend fun doSomethingUsefulOne():Int{
    delay(1000L)//假设在这里做一些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo():Int{
    delay(1000L)
    return 29
}
/** 测量执行两个挂起函数所需要的总时间*/
// suspend fun main(){
//    GlobalScope.launch {
//        val time = measureTimeMillis { //按顺序执行，1s后执行下一个。
//            val one = doSomethingUsefulOne()
//            val two = doSomethingUsefulTwo()
//            println("the answer is ${one+two}")
//        }
//        println("completed in $time as")
//    }
//    delay(3000L)
//}

/** 使用async并发 ，当两个函数之间没有依赖，想要更快得到结果，应该使用并发
 * async类似于launch，它启动了一个单独的协程，这是一个轻量级线程并与其他所有的协程一起并发的工作，不同之处在于launch返回一个job，
 * 并且不附带任何结果值。而async返回一个deferred。一个轻量级的非阻塞future,这代表了一个将会在稍后提供结果的promise.你可以使用.await()在一个延期的
 * 值上得到它的最终结果。但是deferred也是一个job,所以也是可以取消的
 * */
//suspend fun main(){
//   GlobalScope.launch {
//       val time = measureTimeMillis {
//           val one = async { doSomethingUsefulOne() }
//           val two = async { doSomethingUsefulTwo() }
//           println("The answer is ${one.await()+two.await()}")
//       }
//       println("completed in ${time} ms") //并发执行，快了2倍。
//   }
//    delay(1200L)
//}

/** 惰性启动async
 * 可选的，async可以通过将start参数设置为CoroutineStart.LAZY 而变为惰性的。在这个模式下，只有结果通过await获取的时候，
 * 协程才会启动，或者在job的start函数调用的时候。
 * */
//suspend fun main(){
//    GlobalScope.launch {
//        val time = measureTimeMillis {
//            val one = async(start = CoroutineStart.LAZY){ doSomethingUsefulOne()}
//            val two = async(start = CoroutineStart.LAZY){ doSomethingUsefulTwo()}
//            //执行一些计算
//            one.start()//启动第一个
//            two.start()//启动第二个
//            println("the answer is ${one.await()+two.await()}")
//        }
//        println("completed in $time ms")
//    }
//    delay(1200L)
//}

/** Async 风格的函数
 * kotlin并不推荐这样写。
 *
 * */
//fun somethingUsefulOneAsync() = GlobalScope.async {
//    doSomethingUsefulOne()
//}
//fun somethingUsefulTwoAsync()=GlobalScope.async { //函数的返回值类型是Deferred<Int>
//    doSomethingUsefulTwo()
//}
//fun main(){
//    val time = measureTimeMillis {
//        //可以在协程外，启动异步执行
//        val one = somethingUsefulOneAsync()
//        val two = somethingUsefulTwoAsync()
//        //但是等待结果必须调用其他的挂起或阻塞
//        //当我们等待结果的时候，这里使用 runBlocking{} 来阻塞主线程
//        runBlocking {
//            println("the answer is ${one.await()+two.await()}")
//        }
//    }
//    println("completed in $time ms")
//}

/** 使用async的结构化并发
 *  如果在concurrentSum函数内部发送错误，并且它抛出一个异常，所有在作用域中启动的协程都会被取消。
 * */
//suspend fun concurrentSum():Int = coroutineScope{
//    val one = async{ doSomethingUsefulOne()}
//    val two = async { doSomethingUsefulTwo()}
//    one.await()+two.await()
//}
//suspend fun main(){
//    val time = measureTimeMillis {
//        println("the answer is ${concurrentSum()}")
//    }
//    println("completed is $time ms")
//}


/** 取消始终通过协程的层次结构来进行传递
 * 如果一个子协程（two）失败，第一个async以及等待中的父协程都会被取消。
 * */
fun main()= runBlocking<Unit>{
    try{
        failedConcurrentSum()
    }catch (e:ArithmeticException){
        println("computation failed with arithmeticException")
    }
}
suspend fun failedConcurrentSum():Int=coroutineScope{
    val one = async<Int>{
        try{
            delay(Long.MAX_VALUE)
            42
        }finally {
            println("first child was cancelled")
        }
    }
    val two = async<Int>{
        println("second child throws an exception ")
        throw  java.lang.ArithmeticException()
    }
    one.await()+two.await()
}






























