package chap_06_协程

import kotlinx.coroutines.*
import java.io.IOError
import java.io.IOException
import java.lang.ArithmeticException
import java.lang.AssertionError
import java.lang.IndexOutOfBoundsException

/**
 * Created by wangjiao on 2020/8/26.
 * description:
 */
///** 异常的传播
// * 协程构建器有两种形式：自动传播异常（launch 与 actor ）或向用户暴露异常（async 与 produce）当这些构建器用于创建一个根协程时，前者
// * 这类构建器将异常视为未捕获异常，后者则依赖用户来最终消费异常，例如通过await或receive
// ** /
//

//fun main()= runBlocking {
//    val job = GlobalScope.launch {// launch根协程
//        println("throwing exception from launch")
//        throw IndexOutOfBoundsException()//将在控制台打印
//    }
//    job.join()
//    println("Joined failed job")
//    val deferred = GlobalScope.async {//async 根协程
//        println("throwing exception from async")
//        throw ArithmeticException()//没有打印任何东西，依赖用户去调用等待
//    }
//    try{
//        deferred.await()
//        println("Unreached")
//    }catch (e:ArithmeticException){
//        println("caught ArithmeticException")
//    }
//}

/**
 * JVM中可以重定义一个全局的异常处理者来将所有协程通过ServiceLoader注册到CoroutineExceptionHandler
 * 全局异常处理者就如同 Thread.defaultUncaughtExceptionHandler一样，在没有更多的指定异常处理者被注册的时候被使用。
 * uncaughtExceptionPreHandler被设置在全局协程处理者中
 * */

//fun main()= runBlocking {
//    val handler = CoroutineExceptionHandler { _, throwable -> println("CoroutineExceptioHandler got $throwable") }
//    val job = GlobalScope.launch(handler){
//        throw AssertionError()
//    }
//    val deferred = GlobalScope.async(handler){
//        throw ArithmeticException() //没有打印任何东西，依赖用户await
//    }
//    joinAll(job,deferred)
//}

/** 取消与异常
 * 协程内部使用 CancellatonException来进行取消。这个异常会被所有的处理者忽略。
 * 当协程使用job.cancel取消的时候，它会被终止，但是它不会取消它的父协程
 * */
//fun main()= runBlocking {
//    val job = launch {
//        val child = launch {
//            try{
//                delay(Long.MAX_VALUE)
//            }finally {
//                println("child is canneld")
//            }
//        }
//        yield()
//        println("cancel child")
//        child.cancel()
//        child.join()
//        println("child finish")
//        yield()
//        println("parent is not cancel ")
//    }
//    job.join()  //等待协程执行
//    println("finish")
//}

/** 如果一个协程遇到CancellationException以外的异常，它将使用该异常取消它的父协程。
 * 这个行为无法被覆盖，并且为结构化并发 提供了稳定的协程层级结构
 * coroutineExceptionHandler的实现并不是用于子协程
 *
 * 父协程的所有子协程都结束了，原始的异常才会被父协程处理，
 * */

//fun main()= runBlocking {
//    val handler = CoroutineExceptionHandler { coroutineContext, throwable -> println("coroutineExceptionHandler got $throwable") }
//    val job = GlobalScope.launch(handler){
//        launch { //第一个子协程
//            try{
//                delay(Long.MAX_VALUE)
//            }finally {
//                withContext(NonCancellable){
//                    println("children are cancelled ,but exception is not handled until all children terminate")
//                    delay(100)
//                    println("thr first child finished its non cancellable block")
//                }
//
//            }
//          }
//        launch {//第二个子协程
//            delay(10)
//            println("second child throwd an exception")
//            throw ArithmeticException()
//        }
//    }
//    job.join()
//}

/** 异常聚合  当协程的多个子协程因异常而失败时，一般规则是取第一个异常，因此将处理第一个异常。在第一个异常之后发生的所有其他异常都作为被抑制的异常绑定至第一个异常
 * */
//fun main()= runBlocking {
//    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        println("CoroutineExceptionHandler got $throwable") }
//    val job = GlobalScope.launch(handler){
//        launch {
//            try{
//                delay(Long.MAX_VALUE)
//            }finally {
//                throw ArithmeticException()
//            }
//        }
//        launch {
//            delay(100)
//           throw IOException()  //只要一个异常输出
//        }
//        delay(Long.MAX_VALUE)
//    }
//    job.join()
//}

/** 取消异常是透明的，默认情况下是未包装的*/

//fun main()= runBlocking {
//    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        println("CoroutineExceptionHandler got $throwable")
//    }
//    val job = GlobalScope.launch(handler){
//        val inner = launch {//该站内的协程都将被取消
//            println("111")
//            launch {
//                println("222")
//                launch {
//                    println("333")
//                    throw IOException()//原始异常
//                }
//            }
//        }
//        try{
//            println("444")
//            inner.join()
//        }catch (e:CancellationException){
//            println("rethrowing cancellationException with original cause") //这个优先，然后才是IOException
//            throw  e
//        }
//    }
//    job.join()
//}
/** 监督
 * 取消是在协程的整个层次结构中传播的双向关系，现在看看的单向取消情况。
 * 应用场景：如果任何一个UI的子作业执行失败，它并不总是有必要取消整个UI组件，但是如果UI组件被销毁了，并且它的作业也被取消了，由于它的结果
 * 不再被需要了，它有必要使所有子作业执行失败
 *
 * 另一个场景：服务进程孵化一些子作业并且需要监督它们的执行，最终它们的故障并在这些子作业执行失败时重启
 * */

/** 监督作业  supervisorJob 。类似常规的job,但这取消只会向下传播*/
//fun main()= runBlocking {
//    val supervisor = SupervisorJob()
//    with(CoroutineScope(coroutineContext+supervisor)){
//        //启动第一个子作业，这个示例将会忽略它的异常（不要在实践中这么做）
//        val firstChild = launch(CoroutineExceptionHandler { _, _ -> }){
//            println("the first child is failing")
//            throw AssertionError("the first child is cancelled")
//        }
//        //启动第二个子作业
//        val secondChild = launch {
//            firstChild.join()
//            //取消第一个子作业，但没有传播给第二个子作业
//            println("the first child is  cancelled ${firstChild.isCancelled},but the second is active")
//            try{
//                delay(Long.MAX_VALUE)
//            }finally {
//                println("thr second child is cancelled because the supervisor was cancelled")
//            }
//        }
//        firstChild.join()
//        println("cancelling the supervisor")
//        supervisor.cancel()
//        secondChild.join()
//    }
//}


/** 监督作用域 可以用supervisorScope替代coroutineScope来实现相同的目的。它只会单向传播，并且当作业自身执行失败的时候将所有子作业全部取消*/

//fun main()= runBlocking {
//    try{
//        supervisorScope {
//            val child = launch {
//                try{
//                    println("the child is sleeping ")
//                    delay(Long.MAX_VALUE)
//                }finally {
//                    println("the child is cancelled")
//                }
//            }
//            //使用yield给子作业一个机会执行打印
//            yield()
//            println("throwing an exception from the scope")
//            throw AssertionError()
//        }
//    }catch (e:AssertionError){
//        println("caught an assertion error")
//    }
//}

/** 监督协程中的异常 */
//fun main()= runBlocking {
//    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        println("${Thread.currentThread().name} CoroutineExceptionHandler got $throwable")
//    }
//    supervisorScope {
//        launch(handler){
//            println("${Thread.currentThread().name} the child throws an exception")
//            throw AssertionError()
//        }
//        println("${Thread.currentThread().name} the scope is completing")
//    }
//    println("${Thread.currentThread().name} the scope is completed")
//}



















