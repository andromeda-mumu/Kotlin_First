package chap_06_协程

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by wangjiao on 2020/8/14.
 * description:
 */
//fun main(){
//    GlobalScope.launch { //在后台启动一个新的协程并继续
//        delay(1000L)
//        println("world")
//
//    }
//    println("hello,")
//    Thread.sleep(2000L)
    /** 本质上，协程是轻量级的线程。
     * 在GlobalScope中启动一个新的协程，那意味着，新协程的生命周期只受整个应用程序的生命周期限制
     * */

    /** 编译不通过，delay是一个特殊的挂起函数，它不会造成线程阻塞，但是会 挂起协程，兵器只能在协程中使用*/
//    thread { delay(1000L) }


    /** 桥接阻塞 和 非桥接阻塞的世界
     * 一下只使用了非阻塞的函数delay,调用runBlocking的主线程会一直阻塞知道runBolcking内部 的协程执行完毕
     * */
//    GlobalScope.launch {
//        delay(1000L)
//        println("World")
//    }
//    println("Hello")
//    runBlocking { delay(2000L) }
//}

/** 使用runBlocking 来包装main函数执行*/
//fun main() = runBlocking<Unit>{
//    GlobalScope.launch {
//        delay(1000L)
//        println("world")
//    }
//    println("hello")
//    delay(2000L)//延迟2s包装JVM存活
//}


/** 等待一个作业*/
//suspend fun main(){
//    val job=GlobalScope.launch {
//        delay(1000L)
//        println("world")
//    }
//    println("hello")
//    job.join() //等待知道子协程执行结束
//}

/** 结构化并发 */
//fun  main()=runBlocking{
//    launch{
//        delay(1000L)
//        println("world")
//    }
//    println("hello")
//}

/** 作用域构建器
 * 除了由不同的构建器提供协程作用域外，还可以使用coroutineScrop构建器声明自己的作用域。它会创建一个协程作用域并且在所有已启动子协程执行完毕之前不会结束。
 * runBlocking 与 coroutineScope看起来类似，它们都会等待其协程体以及所有子协程结束，主要区别在于，runBlocking方法会阻塞当前线程来等待，而coroutineScope只是挂起，
 * 会释放底层线程用语其他用途。
 * 因此runBlocking是常规函数，而coroutineScope是挂起函数
 * */
//fun main()= runBlocking {
//    launch{
//        delay(200L)
//        println("Task from run bloacking ")
//    }
//    coroutineScope {
//        launch {
//            delay(500L)
//            println("task from nested launch")
//        }
//        delay(100L)
//        println("task from coroutine scope")//这会在内嵌的launch之前输出
//    }
//    println("Coroutine scope is over")//这一行在内嵌launch之后输出
//}
/** 提取函数重构
 * 将launch{}内部的代码块提取到独立的函数中，当对这段代码执行“提取函数”重构时，你会得到一个带有suspend修饰符的新函数，这是第一个挂起函数。
 * 在协程内部可以像普通函数一样使用挂起函数，
 *
 *
 * */
//fun main()= runBlocking {
//    launch{
//        doWorld()
//        println("hello")
//    }
//}
//suspend fun doWorld(){
//    delay(1000L)
//    println("world")
//}
/** 协程很轻量*/
//fun main()=runBlocking{
//    repeat(100000){//启动大量协程
//        launch {
//            delay(5000L)
//            println(".")
//        }
//    }
//}
/** 全局协程像守护线程
 * 在GlobalScope中启动的活动协程并不会使进程保活，他们像守护进程一样。
 * */
suspend fun main(){
    GlobalScope.launch {
        repeat(1000){
            i->
            println("i'm sleeping $i")
            delay(500L)
        }
    }
    delay(2300L) //在延迟后退出
}






