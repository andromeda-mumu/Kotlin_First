package chap_06_协程

import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Created by wangjiao on 2020/8/17.
 * description:
 */
/** 取消协程的执行
 * launch函数返回了一个可以被用来取消的协程的job
 * */
//suspend fun main(){
//    val job = GlobalScope.launch{
//        repeat(1000){i->
//            println("Job: Im sleeping $i")
//            delay(500L)
//        }
//    }
//    delay(1300L)
//    println("main:Im tired of waiting ")
//    job.cancel()
//    job.join()
//    println("main:now I can quit ")
//}

/** 取消是协作的
 * 协程的取消是协作的，一段协程代码必须协作才能被取消。
 * 挂起函数都是可被取消的。他们检查协程的取消，并在取消时抛出cancellationException。然鹅如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的。
 * */
//suspend fun main(){
//    val startTime = System.currentTimeMillis()
//    val job=GlobalScope.launch {
//        var nextPringTime = startTime
//        var i =0
//        while(i<5){
//            if(System.currentTimeMillis()>=nextPringTime){
//                println("job:im sleeping ${i++}...")
//                nextPringTime+=500L
//            }
//        }
//    }
//    delay(1300L)
//    println("main:im tried of waiting ")
//    job.cancelAndJoin()//即时执行了取消，但协程任然在继续执行。正在计算的代码不可取消
//    println("main:now i can quit")
//}

/** 使计算代码可取消
 * 第一种方法：定期调用挂起函数来检查取消，yield是一个好的选择
 * 第二种方法：显式的检查取消状态
 *
 * 将上面的示例while(i<5) 改成 while(isActive)并重新运行它
 * */
//suspend fun main(){
//    val startTime = System.currentTimeMillis()
//    val job= GlobalScope.launch {
//        var nextPringTime = startTime
//        var i =0
//        while(isActive){
//            if(System.currentTimeMillis()>=nextPringTime){
//                println("job:im sleeping ${i++}...")
//                nextPringTime+=500L
//            }
//        }
//    }
//    delay(1300L)
//    println("main:im tried of waiting ")
//    job.cancelAndJoin()
//    println("main:now i can quit")
//}

/** 在finally中释放资源
 *
 *
 * */
//suspend fun main(){
//    val job= GlobalScope.launch {
//        try{
//            repeat(1000){
//                i->
//                println("job: im sleeping $i")
//                delay(500L)
//            }
//        }finally {
//            println("job :im running finally")
//        }
//    }
//    delay(1300L)
//    println("main:im tried of waiting ")
//    job.cancelAndJoin()//取消该作业并等待它结束，紧接着会进入finally。
//    println("main:now i can quit")
//}

/** 运行不能取消的代码块
 *
 *
 * */
//suspend fun main(){
//    val job = GlobalScope.launch {
//        try{
//            repeat(1000){
//                i->
//                println("job:I'm sleeping $i")
//                delay(500L)
//            }
//        }finally {
//            withContext(NonCancellable){
//                println("job:I'm running finally ")
//                delay(1000L)
//                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
//            }
//        }
//    }
//    delay(1300L)
//    println("main:I'm tired of waiting ")
//    job.cancelAndJoin()
//    println("main:Now I can qiut")
//}

/** 超时
   在实践中，大部分取消一个协程的理由是他有可能超时。当手动追踪一个相关job的引用并启动一个单独的协程在延迟后取消追踪，
   已有withTimeout函数来做这件事。

 */
//suspend fun main(){
//    withTimeout(1300L){
//        repeat(1000){i->
//            println("i'm sleeping $i")
//            delay(500L)
//        }
//    }
//}

suspend fun main(){
    withTimeoutOrNull(1300L){//替代withTimeout，不会抛出异常
        repeat(1000){i->
            println("i'm sleeping $i")
            delay(500L)
        }
    }
}