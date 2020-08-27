package chap_07_多平台程序设计


/**
 * Created by wangjiao on 2020/8/27.
 * description:
 */
/** 预期声明 和实际声明的机制 */
//公共模块的一部分
//expect class Foo(bar:String){
//    fun frob()
//}
//fun main(){
//    Foo("hello").frob()
//}
//
////而相应的JVM模块
//actual class Foo actual constructor(val bar:String){
//    actual fun frob(){
//        println("Frobbing the $bar")
//    }
//}

/**要点：
 * - 公共模块的预期声明与其对应的实际声明始终具有完全相同的完整限定名
 * - 预期声明标有expect关键字；实际声明标有actual 关键字
 * - 与预期声明的任何部分匹配的所有实际声明都需要标记为actual
 * - 预期声明不包含任何实现代码
 * */
/** 预期声明并不限于接口与接口成员，在本例中，预期的类有一个构造函数，于是可以直接在公共代码中创建该类，还可以将except修饰符应用于其他声明，包括顶层声明以及注解*/
//公共
//expect fun formatString(source:String,vararg args:Any):String
//expect annotation class Test
//
////JVM
//actual fun formatString(source:String,vararg args:Any)=
//    String.format(source,*args)
//
//actual typealias Test = org.junit.Test
//
///** 编译器会确保每个预期声明在实现相应公共模块的所有平台模块中都具有实际声明，并且如果缺少任何实际声明，编译器都会报错*/
//
///** 如果有一个希望用在公告代码中的平台相关库，同时为其他平台提供自己的实现，那么可以将现有类的别名作为实际声明：*/
//except class AtomicRef<V>(value:T){
//    fun get():V
//    fun set(value:V)
//    fun getAndSet(value:V):V
//    fun compareAndSet(except:V,update:V):Boolean
//}
//actual typealias AtomicRef<V> = java.util.concurrent.atomic.AtomicReference<V>