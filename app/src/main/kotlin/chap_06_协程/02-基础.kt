package chap_06_协程

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
