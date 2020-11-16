package chap_06_协程

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * Created by wangjiao on 2020/8/26.
 * description:
 */
/** */
suspend fun massiveRun(action:suspend () ->Unit){
    val n = 100
    val k =1000
    val time = measureTimeMillis {
        coroutineScope {//协程的作用域
            repeat(n){
                launch {
                    repeat(k){
                        action()
                    }
                }
            }
        }
    }
    println("Completed ${n*k} actions in $time ms")
}
//
//@Volatile
//var counter =0
//fun main()= runBlocking {
//    withContext(Dispatchers.Default){
//        massiveRun {
//            counter++
//        }
//    }
//    println("Counter = $counter") //多个协程，访问同一个数据，造成数据不正确，应该加上同步代码防止共享数据的异常
//}

/** 100个协程在多个线程中同时递增计算，但没有做并发处理，因此结果不可能打印出counter=1000000*/

/** volatile 无济于事,代码运行更慢，结果仍然没有1000000，因为volatile变量保证可线性化（”原子“）的读取和写入 ,但是大量动作（“++递增”）发生时，不提供原子性*/

/** 线程安全的数据结构
 * 对线程 协程都有效的常规方法，就是使用线程安全（同步的，可线性化，原子）的数据结构，它为需要在共享状态上执行的相应操作提供所有必须的同步处理
 *
 * */

//val counter = AtomicInteger() //这是线程安全的数据结构，可以随便操作
//fun main() = runBlocking {
//    withContext(Dispatchers.Default){
//        massiveRun {
//            counter.incrementAndGet()
//        }
//    }
//    println("counter = $counter")
//}

/** 以细粒度限制线程  通过使用一个单线程
 * 这段代码运行非常缓慢，每个增量操作都得使用[withContext(counterContext)]块从多线程Dispatchers.Default上下文切换到单线程上下文。
 * */
//val  conterContext = newSingleThreadContext("counterContext")
//var counter =0
//fun main()= runBlocking {
//    withContext(Dispatchers.Default){
//        massiveRun {
//            withContext(conterContext){ //从多线程切换到单线程。就能保证数据结果正确了
//                counter++
//            }
//        }
//    }
//    println("counter = $counter") //耗时462ms
//}

/** 以粗粒度限制线程  在单线程上线问中运行每个协程*/
//val counterContext = newSingleThreadContext("CounterContext")
//var counter =0
//fun main()= runBlocking {
//    //将一切都限制在单线程上下文中 ,速度还可以
//    withContext(counterContext){
//        massiveRun {
//            counter++
//        }
//    }
//    println("counter =$counter")//耗时25ms
//}

/** 互斥  使用永远不会同时执行的 关键代码块 来保护共享状态的所有修改。在阻塞的世界中，通常会为此目的使用 synchronized 或者 ReentrantLock 。在协程中的替代品时 Mutex .
 * 它具有lock 和 unlock 方法。可以隔离关键部分，关键区别在于 Mutex.lock()是一个挂起函数，它不会阻塞线程
 *
 * withLock扩展函数，可以方便替代常用的mutex.lock();try{}finally{mutex.unlock()}
 * */
//val mutex = Mutex()
//var counter =0
//fun main()= runBlocking {
//    withContext(Dispatchers.Default){
//        massiveRun {
//            mutex.withLock {//是一个挂起函数，不会阻塞线程
//                counter++
//            }
//        }
//    }
//    println("counter = $counter") //158ms 比上面的要慢
//}

/** actors
 * 一个actor 是由协程 、被限制并封装到该协程中的状态 以及 一个与其他协程通信的通道 组合而成的一个实体。一个简单的actor可以简单的写成一个函数，
 * 但是一个拥有复杂状态的actor更适合由类来表示
 *
 * 有一个actor协程构建器，他能方便的将actor的邮箱通道组合到其作用域中（用来接收消息）、组合发送channel与结果集对象，这样对actor的单个引用就可以作为其句柄持有
 *
 * 使用actor的第一步时定义一个actor要处理的消息类，kotlin的密封类很适合这种场景。我们使用IncCounter消息（递增计数器）和GetCounter消息（获取值）来定义CounterMsg密封类。
 * 后者需要发送回复，completableDeferred通信原语表示未来可知（可传达）的单个值，这里被用于此目的
 * */
sealed class CounterMsg
object IncCounter:CounterMsg()//递增计数器的单向消息
class GetCounter(val response:CompletableDeferred<Int>):CounterMsg() //携带回复的请求

fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter =0
    for(msg in channel){
       when(msg){
           is IncCounter -> counter++
           is GetCounter -> msg.response.complete(counter)
       }
    }
}

fun main()= runBlocking<Unit> {
    val counter = counterActor()
    withContext(Dispatchers.Default){
        massiveRun {
            counter.send(IncCounter)
        }
    }
     //发送一条消息用来从一个actor中获取计数值
    val response = CompletableDeferred<Int>()
    counter.send(GetCounter(response))
    println("couter ${response.await()}")
    counter.close()
}
/** actor本身执行时所处上下无关紧要，一个actor是一个协程，而一个协程是按顺序执行的，因此将状态限制到特定协程可以解决共享可变状态的问题。
 * 实际上，actor可以修改自己的私有状态， 但只能通过消息互相影响（避免任何锁定）
 * actor在高负载下，比锁更有效，因为这种情况下，它总是有工作要做，而且根本不需要切换到不同的上下文。
 *
 * 注意，actor协程构建器是一个双重的produce协程构建器。一个actor与它接收消息的通道相关联，而一个producer与它发生元素的通道相关联
 * */