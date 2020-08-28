package chap_08_更多语言结构

import java.util.*
import kotlin.random.Random

/**
 * Created by wangjiao on 2020/8/28.
 * description:
 */
/**kotlin标注库包含几个函数，它们唯一的目的是在对象的上下文中执行代码块，当对一个对象调用这样的函数并提供一个lambda表达式时，它会
 * 形成一个临时作用域，在此作用域中，可以访问该对象而无需其名称。这些函数称为作用域函数
 * 共有五个：let,run,with,apply ,also
 *
 * 这样函数基本上做了同样的事情：在一个对象上执行一个代码块，不同的是 这个对象在块中如何使用，以及整个表达式的结果是什么
 * */
//class PersonA(name:String,age:Int,pos:String){
//    fun moveTo(s:String){
//
//    }
//    fun increameetAge(){
//        ((::age).get())++
//    }
//}
//
//fun main() {
//    PersonA("Alice",20,"Ad").let{
//        println(it)
//        it.moveTo("london")
//        it.increameetAge()
//        println(it)
//    }
//
//    //如果不使用let，那就需要引入新的变量
//    val alice = PersonA("alice",20,"china")
//    println(alice)
//    alice.moveTo("london")
//    alice.increameetAge()
//    println(alice)
//}

/** 区别
 * 每个作用域函数本质上都很相似，主要有两个区别：
 * - 引用上下文对象的方式
 * - 返回值
 *
 * 上下文对象：this 还是 it
 * 在作用域函数的lambda表达式里，上下文对象可以不使用其实际名称而使用一个更简短的引用来访问。每个作用域函数都使用一下两种方式之一来访问上下文对象。
 * 作为lambda表达式的接收者（this）或者作为lambda表达式的参数(it) 两者都提供同样的功能，因此我们将针对不同的场景描述两者的优缺点
 * */
//fun main() {
//    var str ="hello"
//    //this
//    str.run{
//        println("length:${this.length}")
//    }
//
//    //it
//    str.let{
//        println("length ${it.length}")
//    }
//}
/**
 * this ：run with apply通过this引用上下文，因此他们的lambda表达式可以像在普通的类函数中一样访问上下文对象。
 * */
//val adma = PersonA("adma").apply {
//    age=20
//    city="london"
//}

/** it：let also 将上下文对象作为lambda表达式参数。如果没有指定参数名，对象可以隐式默认名称it访问。
 * it比this简短，更易阅读。然鹅，当调用对象函数或属性时，不能像this这样隐式地访问对象。因此当上下文对象在作用域中主要用作函数调用中的参数时，使用it作为上下文对象会更好。
 *
 * */
//fun getRandomInt():Int{
//    return Random.nextInt(100).also{
//        println("getRandomInt() generated value $it")
//    }
//}
//fun main() {
//    val i = getRandomInt()
//    println(i)
//}
/** 此外，将上下文对象作为参数传递时，可以为上下文对象指定在作用域内的自定义名称*/
//fun getRandomInt():Int{
//    return Random.nextInt(100).also { value ->
//        println("value $value")
//    }
//}

/**
 * 返回值
 * 根据返回值结果：作用域函数可以分为以下两类：
 * -apply 及also返回上下文对象
 * - let run with 返回lambda表达式结果
 * */
/** 上下文对象  链式调用*/
//val numberList = mutableListOf<Double>()
//fun main() {
//    numberList.also {
//        println("populating the list")
//    }.apply {
//        add(1.3)
//        add(3.4)
//        add(5.3)
//    }.also {
//        println("sorting the list")
//    }.sort()
//}

/** 还可以用在返回上下文对象的函数的return语句中*/
fun getRandomInt():Int{
    return Random.nextInt(100).also{
        println(" value $it")
    }
}

/** lambda表达式结果 */
//fun main() {
//    val numbers = mutableListOf<String>("one","two","three")
//    val countEndsWithF = numbers.run{
//        add("four")
//        add("five")
//        count{ it.endsWith("e")}
//    }
//    println("there are $countEndsWithF element that end with e")
//}

/** 此外，可以忽略返回值，仅使用作用域函数为变量创建一个临时作用域*/
//fun main() {
//    val numbers =  mutableListOf<String>("one","two","three")
//    with(numbers){
//        val fitstItem = first()
//        val lastItem = last()
//        println("first item :$fitstItem ,last item $lastItem")
//    }
//}

/** let
 * 上下文对象作为lambda表达式的参数it 来访问，返回值是lambda表达式结果
 * */
//fun main() {
//    val numbers= mutableListOf<String>("one","two","three")
////    val resultList = numbers.map{it.length}.filter { it>3 }
////    println(resultList)
//
//    //改用let
////    numbers.map{it.length}.filter { it>3 }.let {
////        println(it)
////    }
//
//    //若代码块仅包含it作为参数的单个函数，则可以使用方法引用::代替lambda表达式
//    numbers.map{it.length}.filter { it>3 }.let(::println)
//}

/** let经常用于使用非空值执行代码块，如需对非空对象执行操作，可对其使用安全调用操作符?.  并调用let在lambda表达式中执行操作*/
//fun main() {
//    val str:String?="hello"
//    val length =str?.let{
//        println("let() called on $it")
////        processNonNummString(it) //‘it’ 在‘?.let{}’中必不为空
//        it.length
//    }
//}
/** 使用let的另一种情况，引用作用域受限的局部变量以提高代码的可读性。如需为上下文对象定义一个新变量，可提供其名称作为lambda表达式参数来替默认的it*/
//fun main() {
//    val  numbers= mutableListOf<String>("one","two","three")
//    val modifiedFirstItem = numbers.first().let{
//        firstItem ->
//        println("first item of the list is $firstItem")
//        if(firstItem.length>=5) firstItem else "!"+firstItem+"!"
//    }.toUpperCase()
//
//    println(modifiedFirstItem)
//}

/** with
 * 一个非扩展函数：上下文对象作为参数传递，在lambda表达式内部，它可以作为接收者使用。返回值是lambda表达式结果
 * */
//fun main() {
//    val number =mutableListOf<String>("one","two","three")
//    with(number){
//        println("argument $this")
//        println("it comtains $size elements")
//    }
//    //with另一个使用场景，引入一个辅助对象，其属性或函数将用于计算一个值
//    val firstAndLast = with(number){
//        "the first is ${first()}"+
//                " the last is ${last()}"
//    }
//    println(firstAndLast)
//}
/** run
 * 上下文对象作为接收者this来访问，返回值是lambda表达式
 * run和with做同样的事，但调用方式和let一样 --作为一个上下文对象的扩展函数
 * 当lambda表达式同时包含对象初始化和返回值的计算时，run很有用
 * */
//fun main() {
//    val service = MultiportService("http://www.baidu.com",80)
//    val result = rservice.run{
//        port = 8080
//        query(perpareResult()+"to port $port")
//    }
    //使用let
//    val letResult = service.let{
//        it.port =8080
//        it.query("xxx")
//    }
//}

/** 除了在接收者对象上调用run之外，还可以将其用作非扩展函数。可以在需要表达式的地方执行一个由语句组成的块*/
//fun main() {
//    val hex = run{
//        val digit = "0-9"
//        val hexDigits ="A-Fa-f"
//        val sign ="+-"
//        Regex("[$sign]?[$digit$hexDigits]+")
//    }
//    for (match in hex.findAll("+1234 -FFFF not-a-number")){
//        println(match.value)
//    }
//}

/** apply
 * 上下文对象作为接收者 this来访问。返回值是上下文对象本身
 * */
//fun main() {
//    val dama = Person("adam").apply{
//        aga =31
//        city ="london"
//    }
//    println(dama)
//}

/** also
 * 上下文对象作为lambda表达式的参数it来访问。返回值是上下文对象本身
 *
 * */
//fun main() {
//    val numbers = mutableListOf("one", "two", "three")
//    numbers.also { println("add new one $it") }.add("four")
//}

/** takeIf 与 taleUnless */










