package chap_06_协程

import android.renderscript.Sampler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

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

/** flowOn操作符，可以更改流发射的上下文
 * 改变了 流的顺序，收集和发射在不同的协程里并行执行
 * */
/*fun simple(): Flow<Int> = flow{
    for (i in 1..3){
        Thread.sleep(100)
        println("emiiting $i  ${Thread.currentThread().name}")
        emit(i)
    }
}.flowOn(Dispatchers.Default) //在流构建器中改变消耗CPU代码上下文的正确方式
fun main()= runBlocking {
    simple().collect { value ->
        println("collected $value ${Thread.currentThread().name}")
    }
}*/

/** 缓冲 */

//fun simple():Flow<Int> = flow {
//    for(i in 1..3){
//        delay(100)
//        emit(1)
//    }
//}
//fun main()= runBlocking<Unit> {
//    val time = measureTimeMillis {
//        simple()
//            .buffer()//加入buffer后，从每个数字处理400ms，总共1200ms.变为了，第一个数字100ms,每个数组各300ms，总共1000ms。
//            .collect {
//                value->
//            delay(300)
//            println(value)
//        }
//    }
//    println("collected in $time ms")
//}

/** 合并
 * 当流代表部分操作结果或操作状态更新时，没必要处理每个值，只处理最新的那个。当收集器处理太慢时，conflate操作符可以用于跳过中间值.
 * */

//fun simple():Flow<Int> = flow {
//    for(i in 1..3){
//        delay(100)
//        emit(i)
//    }
//}
//fun main()= runBlocking {
//    val time = measureTimeMillis {
//        simple()
//            .conflate() //虽然第一个数字还在处理，但第二第三个数字已经产生了，
//            .collect { value ->
//                delay(300)
//                println(value)
//            }
//    }
//    println("collected in $time ms")
//}

/** 处理最新值
 * 当发射器和收集器很慢的时候，合并是加快处理速度的一种方式。另一种方式是取消收集器，每次发射新值的时候重新启动它。
 * */

//fun simple():Flow<Int> = flow {
//    for(i in 1..3){
//        delay(100)
//        emit(i)
//    }
//}
//fun main()= runBlocking {
//    val time = measureTimeMillis {
//        simple()
//            .collectLatest { value ->//取消并重新发射最后一个值
//                println("collecting $value")
//                delay(300)
//                println("done $value")
//            }
//    }
//    println("collected in $time ms")
//}

/**--------------组合多个流----------------*/
/** zip */
//suspend fun main(){
//    val nums = (1..3).asFlow()
//    val strs = flowOf("one","two","three")
//    nums.zip(strs) {a,b ->"$a -> $b"}//组合单个字符串
//        .collect { println(it) }
//}

/** combine  */
//suspend fun main(){
//    val nums = (1..3).asFlow().onEach { delay(300) }
//    val strs = flowOf("one","two","three").onEach { delay(400) }
//    val time = System.currentTimeMillis()
//    nums.combine(strs){a,b -> "$a -> $b"}
//        .collect { value ->
//            println("$value at ${System.currentTimeMillis()-time} ms from start")
//        }
//}

/** 展平流
 *
 *
 * */
fun requestFlow(i:Int):Flow<String> = flow {
    emit("$i :first")
    delay(500)
    emit("$i: second")
}
//fun main() = runBlocking<Unit> {
//    (1..3).asFlow().map { requestFlow(it) } //这将得到一个包含流的流（Flow<Flow<String>）
//}

/** flatMapConcat
 *  连接模式由 flatMapConcat与flattenConcat操作符实现。它们在等待内部流完成之前开始收集下一个值 。
 * */
//suspend fun main(){
//    val starttime = System.currentTimeMillis()
//    (1..3).asFlow().onEach { delay(100) }
//        .flatMapConcat { requestFlow(it) }
//        .collect { value ->
//            println("$value at ${System.currentTimeMillis()-starttime} ms from start ")
//        }
//}

/** flatMapMerge
 * 并发收集所有传入的流，并将她们的值合并到一个单独的流，一遍尽快发射值
 * */

//fun main()= runBlocking<Unit> {
//    val startTime = System.currentTimeMillis()
//    (1..3).asFlow().onEach { delay(100) }
//        .flatMapMerge { requestFlow(it) }
//        .collect { value ->
//            println("$value at ${System.currentTimeMillis()-startTime} ms from start ")
//        }
//}

/** flatMapLatest */
//fun main()= runBlocking<Unit> {
//    val startTime = System.currentTimeMillis()
//    (1..3).asFlow().onEach { delay(100) }
//        .flatMapLatest { requestFlow(it) }
//        .collect { value ->
//            println("$value at ${System.currentTimeMillis()-startTime} ms from start ")
//        }
//}

/**--------------流异常----------------*/
/** 收集器 try 与 catch */
//fun simple():Flow<Int> = flow {
//    for( i in 1..3){
//        println("emitting $i")
//        emit(i)
//    }
//}
//fun main()= runBlocking<Unit> {
//    try{
//        simple().collect { value ->
//            println(value)
//            check(value <=1){"colloected $value"}
//        }
//    }catch (e:Throwable){
//        println("Caught $e")
//    }
//}

/** 一切都已捕获 */
//fun simple():Flow<String> = flow {
//    for(i in 1..3){
//        println("emitting $i")
//        emit(i)
//    }
//}.map { value->
//    check(value <=1){"crashed on $value "}
//    "string $value"
//}
//fun main()= runBlocking<Unit> {
//    try{
//        simple().collect { value -> println(value) }
//    }catch (e:Throwable){
//        println("caught $e")
//    }
//}
/** 异常透明性*/
//fun main()= runBlocking<Unit> {
//    simple().catch { e -> emit("caught $e") }.collect { value -> println(value) }
//}

/** 透明捕获 如果collect{} 块抛出异常，则异常会逃逸*/
//fun simple():Flow<Int> = flow{
//    for(i in 1..3){
//        println("Emitting $i")
//        emit(i)
//    }
//}
//fun  main() = runBlocking<Unit> {
//    simple().catch { e-> println("caught $e") }
//        .collect { value ->
//            check(value <=1){"collected $value"}
//            println(value)
//        }
//}

/** 声明式捕获 我们可以将catch操作符与处理所有异常的期望相结合，将collect操作符的代码块移动到onEach汇总，并将其放到catch操作符之前。
 * 收集流必须由调用无参的collect()来触发
 * */
//fun main()= runBlocking<Unit> {
//    simple().onEach { value ->
//    check(value <=1){"collect $value"}
//        println(value)
//    }.catch { e-> println("caught $e") }
//        .collect()
//}

/**--------------流完成----------------*/
/**
 * 命令式finally块
 * 除了try/catch 之外，收集器还能使用finally块再collect完成时执行一个动作
 * */
//fun simple():Flow<Int> = (1..3).asFlow()
//fun main()= runBlocking<Unit> {
//    try{
//        simple().collect { value -> println(value) }
//    }finally {
//        println("done")
//    }
//}

/** 声明式处理 onCompletion 过度操作符
 * */
//fun main() = runBlocking<Unit> {
//    simple().onCompletion { println("done") }
//        .collect { value -> println(value) }
//}
/** onCompletion优点是：lambda表达式的可空参数 Throwable可以用于确定流手机是正常完成还是有异常发生。
 * onCompletion 操作符与catch不同，它不处理异常。异常会流向下游，并提供给onCompletion操作符，并可以由catch操作符处理 * */
//fun simple():Flow<Int> = flow{
//    emit(1)
//    throw RuntimeException()
//}
//fun main()=runBlocking<Unit>{
//    simple()
//        .onCompletion { cause ->if (cause!=null) println("Flow completed exceptionally") }
//        .catch { cause -> println("caught exception") }
//        .collect { value -> println(value) }
//}

/** 启动流 */
//fun events():Flow<Int> = (1..3).asFlow().onEach { delay(100) }
////fun main()= runBlocking<Unit> {
////    events().onEach { event -> println("event:$event") }
////        .collect()
////    println("done")
////}
//
///** 使用launchIn末端操作符可以在这里派上用场。不用等待流收集玩，即可立即执行下一步代码*/
//fun main()= runBlocking<Unit> {
//    events().onEach { event-> println("event $event") }
//        .launchIn(this)//在单独协程中执行流
//    println("done")
//}

/** 流取消检测
 * 流构建器对每个发射值执行附加的ensureActive检测以进行取消。这意味着从flow{...}发出的繁忙循环是可以取消的
 * */
//fun foo():Flow<Int> = flow{
//    for(i in 1..5){
//        println("emiting $i")
//        emit(i)
//    }
//}
//fun main()= runBlocking<Unit> {
////    foo().collect { value->
////        if(value==3)cancel()
////        println(value)
////    }
//    (1..5).asFlow().collect { value ->
//        if(value ==3) cancel() //由于性能原因，大部分流操作不会执行取消检测
//        println(value)
//    }
//}

/** 让繁忙的流可取消*/
//fun main()= runBlocking<Unit> {
//    (1..5).asFlow().cancellable().collect { value ->
//        if(value ==3)cancel()
//        println(value)
//    }
//}

/** 流与响应式流 */