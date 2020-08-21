package chap_06_协程

import kotlinx.coroutines.delay

/**
 * Created by wangjiao on 2020/8/20.
 * description:
 */
/**
 * 异步流
 * 挂起函数可以异步的返回单个值。但如何异步返回多个计算好的值呢，这正是kotlin流flow的用武之地。
 *
 * */

/**表示多个值 集合表示多个值 */
//fun simple():List<Int> = listOf(1,2,3)
//fun main(){
//    simple().forEach { value -> println(value)}
//}

/** 序列 如果使用消耗cpu资源的阻塞代码计算数字（每次计算需要100ms），那么可以使用sequence来表示数字*/
//fun simple():Sequence<Int> = sequence{
//    for(i in 1..3){
//        Thread.sleep(100)
//        yield(i)
//    }
//}
//fun main(){
//    simple().forEach { i -> println(i) }
//}

/** 挂起函数 计算过程阻塞运行该代码的主线程。当这些值有异步代码计算时，可以使用suspend修饰符标记函数simple,这样就可以在不阻塞的情况下执行其工作并将结果作为列表返回*/
//suspend fun simple():List<Int>{
//    delay(100)
//    return listOf(1,2,3)
//}
//fun main()= runBlocking {
//    simple().forEach { value -> println(value) }
//}

/** 流 使用list结果意味着只返回所有值，为表示异步计算的值流，可以使用flow类型.就如同步计算用sequence类型
 * 流  使用emit发射值，使用collect收集值
 * */
//fun simple(): kotlinx.coroutines.flow.Flow<Int> = flow{
//    for(i in 1..3){
//        delay(100)
//        emit(i)
//    }
//}
//fun main()= runBlocking<Unit> {
//    launch {
//        for(k in 1..3){
//            println("not blocking $k")
//            delay(100)
//        }
//    }
//    //收集这个流
//    simple().collect { value -> println(value) }
//}

/**流是冷的 Flow是一种类似于序列的冷流， flow构造器的代码直到流被收集的时候才允许*/
//fun simple():Flow<Int> = flow {
//    println("flow start")
//    for(i in 1..3){
//        delay(100)
//        emit(i)
//    }
//}
//fun main()= runBlocking {
//    println("calling simple function...")
//    val flow = simple()
//    println("calling collect...")
//    flow.collect { v -> println(v) }
//    println("calling collect again")
//    flow.collect { v-> println(v) }
//}

/** 流取消基础 流采用与协程同样的协程取消。*/
//fun simple(): Flow<Int> = flow{
//    for(i in 1..3){
//        delay(100)
//        println(" emitting $i")
//        emit(i)
//    }
//}
//fun main()= runBlocking {
//    withTimeoutOrNull(250){
//        simple().collect { v -> println(v) }
//    }
//    println("Done")
//}

/** 流构建器
 * flowOf构建器定义一个发射固定值集的流
 * 使用.asFlow()扩展函数，可以将各种集合和序列转换为流
 * */
//suspend fun main(){
//    (1..4).asFlow().collect { v-> println(v) }
//}


/** 过渡流操作符 ，用于上游流，并返回下游流。这类操作符本身不是挂起函数，运行速度快，返回新的转换流的定义
 * 流和序列的主要区别在于 这些操作符中的代码可以调用挂起函数
 * */
suspend fun performRequest(request:Int):String{
    delay(100)
    return "response $request"
}
//fun main()= runBlocking {
//    (1..3).asFlow()
//        .map { request -> performRequest(request) }
//        .collect { response -> println(response) }
//}

/** 转换操作符 最通用的一种称为transform 。可以发射任意值 。比如在执行长时间异步请求前发射一个字符串并跟踪这个响应*/
//fun main()= runBlocking {
//    (1..3).asFlow()
//        .transform { request ->
//            emit("Making request $request")
//            emit(performRequest(request))
//        }
//        .collect { response -> println(response) }
//}

/** 限长操作符
 * 限长过渡操作符如take 在流触及相应限制的时候回将它执行取消，协程中的取消操作总是通过抛出异常来执行，这样所以的资源管理函数（try catch）会在取消的情况下正常运行
 * */
//fun numbers(): Flow<Int> = flow {
//    try{
//        emit(1)
//        emit(2)
//        println("this line will not execute")
//        emit(3)
//    }finally {
//        println("finally in numbers")
//    }
//}
//fun main()= runBlocking<Unit> {
//    numbers()
//        .take(2)
//        .collect { v-> println(v) }
//}

/** 末端流操作符
 * 用语启动流搜集的挂起函数 collect是最基础的末端操作符。还有一些其他的
 * -转化为各种集合 ，如toList 与 toSet
 * -获取第一个first值与确保流发射单个single值的操作符
 * -使用reduce与fold将流规约到单个值
 * */
//suspend fun main(){
//    val sum = (1..5).asFlow()
//        .map { it * it }
//        .reduce { a,b   -> a+b  }//求和，末端操作符
//    println(sum)
//}

/** 流是连续的
 * 流的每次搜集都是按顺序的，除非进行特殊操作的操作符使用多个流，该收集过程直接在协程中运行，该协程调用末端操作符。
 * 默认情况下不启动新协程，从上游到下游每个过渡操作符都会处理每个发射出的值然后再交给末端操作符
 * */

//suspend fun main(){
//    (1..5).asFlow()
//        .filter {
//            println("filter $it")
//            it%2==0
//        }
//        .map{
//            println("map $it")
//            "string $it"
//        }.collect { println("collect $it") }
//}

/** 流上下文
 * 流的搜集总是在调用协程的上下文中发生。
 * */
//fun log2(msg:String) = println("[${Thread.currentThread().name}] $msg")
//fun simple(): Flow<Int> = flow{
//    log2("started simple flow")
//    for(i in 1..3){
//        emit(i)
//    }
//}
//fun main()= runBlocking {
//    simple().collect { v -> log2("collect $v") }
//}

/** withContext发出错误
 * 长时间运行的消耗cpu的代码也许需要在Dispatchers.Default上下文中执行，并且更新UI的代码在Dispatchers.Main中执行。
 * 通过withContext 用于改变上下文，但是flow{..}必须遵守上下文保存属性，不允许从其他上下文中发射
 * */
//fun simple(): Flow<Int> = flow {
//    withContext(Dispatchers.Default){ //会产生异常
//            for( i in 1..4){
//            Thread.sleep(100)
//            emit(i)
//         }
//    }
//}
//fun main()= runBlocking {
//    simple().collect { v -> println(v) }
//}

/** */

