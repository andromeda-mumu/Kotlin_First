package chap_06_协程

import kotlinx.coroutines.*

/**
 * Created by wangjiao on 2020/8/18.
 * description:
 */

/**
 * 协程总是运行在一些以 coroutineContext类型为代表的上下文中。
 * 协程上下文是各种不同元素的集合。其中主元素是协程的job
 * */

/**
 * 调度器与线程
 * 协程上下文包含一个 协程调度器coroutineDispatcher 它确定了哪些线程或与线程相对应的协程执行。协程调度器可以将协程限制在一个特定的线程执行。
 * 或将它分派到一个线程池，亦或是让它不受限地运行。
 *
 * 所有的协程构建器如launch 和async 接收一个可选的CoroutineContext 参数。他可以被用来显示的为一个新协程或其他上下文元素指定一个调度器。
 * */
//fun main(){
//    runBlocking {//运行在父协程的上下文中，即runBlocking主协程
//        launch {
//            println("main runBlocking; i'working in thread ${Thread.currentThread().name}")
//        }
//        launch(Dispatchers.Unconfined){ //不受限 将工作在主线程中
//            println("Unconfined : I'm working in thread ${Thread.currentThread().name}")
//        }
//        launch(Dispatchers.Default) {//默认调度器
//            println("default :i'm working in thread ${Thread.currentThread().name}")
//        }
//        launch(newSingleThreadContext("MyOwnThread")){//使它获得一个新线程
//            println("newSingleThreadXContext:I'm working in thread ${Thread.currentThread().name}")
//        }
//    }
//}

/** 非受限调度器 vs 受限调度器
 * Dispatchers,Unconfined协程调度器在调用它的线程启动了一个协程，但它仅仅只运行到第一个挂起点，挂起后，它恢复线程中的协程。而这完全由被调用的挂起函数来决定。
 * 非受限调度器适合执行 不消耗cpu时间的任务，以及不更新局限于特定线程的任何共享数据如UI的协程。
 *
 * 非受限的调度器是高级机制，尽量不要用。
 * */
//fun main(){
//    runBlocking {
//        launch(Dispatchers.Unconfined) {//非受限  将和主线程一起工作
//            println("Unconfined:Thread:${Thread.currentThread().name}")
//            delay(500L)
//            println("Unconfined :After delay Thread：${Thread.currentThread().name}")
//        }
//        launch{//父协程的上下文，主runBlocking 协程
//            println("main :Thread: ${Thread.currentThread().name}")
//            delay(100L)
//            println("main : After delay Thread:${Thread.currentThread().name}")
//        }
//    }
//}

/**
 * 调试协程与线程
 * 用日志调试 。不成功，没有打印标识符
 * */
//fun main(){
//    runBlocking {
//        val a = async { log("I'm computing a piece of the answer")
//        6}
//        val b = async { log("I'm computing another piece of the answer")
//        7}
//        log("the answer is ${a.await()*b.await()}")
//    }
//}
fun log(msg:String)=println("[${Thread.currentThread().name}] $msg")


/** 不同线程之间跳转 */

//fun main(){
//    runBlocking {
//        newSingleThreadContext("Ctx1").use{
//            ctx1->
//            newSingleThreadContext("Ctx2").use{
//                ctx2 ->
//                runBlocking(ctx1) {
//                    log("start in ctx1")
//                    withContext(ctx2){//改变协程的上下文
//                        log("working in ctx2")
//                    }
//                    log("Back to ctx1")
//                }
//            }
//
//        }
//    }
//}

/** 上下文中的作业
 * 协程的job是上下文的一部分，并且可以使用coroutineContext[Job] 表达式在上下文中检索它
 * */
//fun main()=runBlocking {
//        println("my job is ${coroutineContext[Job]}")
//}


/** 子协程
 *
 *
 * */
//fun main() = runBlocking<Unit> {//启动一个协程，处理请求
//    val request=launch {
//        GlobalScope.launch {
//            println("job1: before")
//            delay(1000)
//            println("job1:after")
//        }
//        launch {//继承父协程的上下文
//            delay(100)
//            println("job2:befor")
//            delay(1000)
//            println("job2:after")
//        }
//    }
//    delay(500)
//    request.cancel()
//    delay(2000)
//    println("main:who has surived ")
//}


/**
 * 父协程的职责
 * 一个父协程总是等待所有子协程执行结束。父协程并不显示的跟着所有子协程的启动，并且不必使用job.join在最后的时候等待它们
 *
 * */
//fun main()= runBlocking {
//    val request= launch {
//        repeat(3){i->
//            launch {
//                delay((i+1)*200L)
//                println("coroutine $i is done")
//            }
//        }
//        println("request:done")
//    }
//    request.join()//等待请求完成，包括其所有子协程
//    println("request completed")
//}

/**
 * 命名协程以用于调试
 * CoroutineName 上下文元素与线程名具有相同的目的。
 * */
//fun main()= runBlocking {
//    println("start main coroutine ")
//    val v1=async(CoroutineName("v1coroutine")){
//        delay(500)
//        println("computing v1")
//        252
//    }
//    val v2= async(CoroutineName("v2coroutine")){
//        delay(1000)
//        println("computin v2")
//        6
//    }
//    println("the answer for v1/v2 =${v1.await()/v2.await()}")
//}

/**
 * 组合上下文的元素
 * 有时需要在协程上下文中定义多个元素，可以使用+操作符来实现。
 *
 * */
//fun main()= runBlocking<Unit> {
//    launch(Dispatchers.Default+ CoroutineName("test")) {
//        println("thread : ${Thread.currentThread().name}")
//    }
//}


/**
 * 协程作用域
 * */
//class Activity{
//    private val mainScope = CoroutineScope(Dispatchers.Default)
//    fun destory(){
//        mainScope.cancel()
//    }
//    fun doSomething(){
//        repeat(10){i->
//            mainScope.launch {
//                delay((i+1)*200L)
//                println("Coroutine $i is done ")
//            }
//        }
//    }
//
//}
//suspend fun main()= runBlocking<Unit> {
//    val act = Activity()
//    act.doSomething()
//    println("launch coroutines")
//    delay(500L)
//    println("destorying activty")
//    act.destory()
//    delay(1000L) //为了确认 协程已经停止了
//}
//


/**
 * 线程局面数据
 * 将线程局部数据传递到协程与协程之间是很方便的。
 * ThreadLocal  asContextElement 扩展函数在这里会充当救兵。它创建了额外的上下文元素，且保留给定ThreadLocal的值，并在每次协程切换其上下文时恢复它
 * */
val threadLocal=java.lang.ThreadLocal<String>()
fun main()= runBlocking<Unit> {
    threadLocal.set("main")
    println("pre-main thread:${Thread.currentThread()},thread value ${threadLocal.get()}")
    val job = launch(Dispatchers.Default+ threadLocal.asContextElement(value = "launch")){
        println("launch start  thread:${Thread.currentThread()},thread local value :'${threadLocal.get()}")
        yield()
        println("after yield ,current thread:${Thread.currentThread()},thread local value :'${threadLocal.get()}'")
    }
    job.join()
    println("post main thread:${Thread.currentThread()},thread local value:'${threadLocal.get()}")

}



