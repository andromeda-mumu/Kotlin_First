package chap_03

/**
 * Created by mumu on 2020/6/27.
 * 功能描述：
 */
/** 委托模式已经证明是实现继承的一个很好的替代方式。而kotlin可以零样本代码地原生支持它。Derived 类可以通过将其所有共有成员
 * 都委托给指定对象来实现一个接口 Base */
interface Base5{
    fun print()
}
class BaseImpl (val x:Int):Base5{
    override fun print() {print(x)}
}

class Derived5(b:Base5):Base5 by b

//fun main(){
//    val b = BaseImpl(10)
//    Derived5(b).print()
////    b.print()
//}

/** Derived 的超类型列表中的by- 子句表示 b将会在Derived中内部存储。并且编译器将生成转发给b的所有base的方法*/

/** 覆盖由委托实现的接口成员
 * 覆盖符合预期：编译器会使用override覆盖的实现而不是委托对象中的。如果将override fun printMessage(){print("bac")}
 * 添加到Derived，那么当调用printMessage时，程序会输出abc 而不是10
 * */
interface Base6{
    fun printMessage(){}
    fun printMessageLine(){}
}
class Base6Impl(val x:Int):Base6{
    override fun printMessage() {
        print(x)
    }

    override fun printMessageLine() {
        print(x)
    }
}

class Derived6(b:Base6):Base6 by b{
    override fun printMessage() {
        print("abc")
    }
}
//fun main(){
//    val b = Base6Impl(10)
//    Derived6(b).printMessage()
//    Derived6(b).printMessageLine()
//}

/** 请注意，以这种方式重写的成员不会在委托对象的成员中调用，委托对象的成员只能访问其自身对接口成员实现。*/
interface Base7{
    val message:String
    fun print()
}

class Base7Impl(val x :Int):Base7{
    override val message = "Baseimpe :x =$x"
    override fun print(){println(x)}
}
class Derived7(b:Base7):Base7 by b{
    override val message ="Message of Derived "
}

fun main(){
    val b = Base7Impl(10)
    val derived = Derived7(b)
    derived.print()
    println(derived.message)

}
/** 当使用带有default方法的接口进行委托时，即使实际的委托类型提供了其自身的实现也会调用默认实现。*/


