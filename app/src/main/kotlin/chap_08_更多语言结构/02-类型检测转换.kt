package chap_08_更多语言结构

import java.io.File

/**
 * Created by wangjiao on 2020/8/27.
 * description:
 */
/** is 与 !is 操作符*/
//fun main() {
//    val obj:String ="A"
//    if (obj is String){
//        println(obj.length)
//    }
//    if(obj !is String){
//        println("Not a String")
//    }else {
//        println(obj)
//    }
//}

/** 智能替换
 * 适用于以下规则
 * - val局部变量 ---总是可以
 * -val属性 -如果属性是privarte或internal，或者该检测在声明属性的同一模块中执行。不适用于open的属性 或具有自定义getter的属性
 * -var局部变量--- 如果变量在检测和使用之间没有修改。没有在会修改它的lambda中捕获，并且不是局部委托属性
 * -var属性 绝不可能
 * */
//fun demo(x:Any){
//    if(x is String){
//        println(x.length) //x 自动转换为字符串
//    }
//}

/** 不安全的转换操作符 as */
//fun main() {
//    val y = 12
//    val x:String? = y  as String?  //as 转换报错异常
//    println(x)
//}

/** 安全的可空转换操作符  .as？ 在失败的时候返回null */
//fun main() {
//    val y = 123
//    val x :String? = y as? String
//    println(x)
//}

/** 类型擦除与泛型检测 */
//inline fun <reified  A,reified B> Pair<*,*>.asPairOf():Pair<A,B>? {
//    if(first !is A || second !is B) return null
//    return first as A to second as B
//}
//
//fun main() {
//    val somePair:Pair<Any?,Any?> = "item" to listOf(1,2,4)
//    val stringToSomething = somePair.asPairOf<String,Any>()
//    val stringToInt = somePair.asPairOf<String,Int>()
//    val stringToList = somePair.asPairOf<String,List<*>>()
//    val stringToStringList = somePair.asPairOf<String,List<String>>()//破坏类型安全！！
//    println(stringToSomething)
//    println(stringToInt)
//    println(stringToList)
//    println(stringToStringList)
// }

/** 非受检类型转换 */
//fun readDictionary(file: File):Map<String,*> = file.inputStream().use {
//    TODO("Read a mapping of strings to arbitrary elements")
//}
//fun main() {
//    val intsFile = File("ints.dictionary")
//    val intsDictionary:Map<String,Int> = readDictionary(intsFile) as Map<String ,Int> //编译不通过
//}

/** */
inline fun <reified  T> List<*>.asListOfType():List<T>?=
    if(all {it is T})
        this as List<T>
    else null

fun main() {
    val a = listOf<Int>(11,22,30)
   val b = a.asListOfType<Char>()
    println(b)
}