
package chap_03

/**
 * Created by mumu on 2020/6/20.
 * 功能描述：
 */
/** kotlin 能扩展一个类的新功能而无需继承该类或者使用像装饰者这样的设计模式。这通过叫扩展的特殊声明完成。
 *  eg:你可以为一个你不能修改的，来自第三方库的类编写一个新的函数，这个新增的函数就像那个原始类本来就有的函数一样，
 *  可以用普通的方法调用。
 *  扩展函数 和 扩展数学系
 * */

/** 扩展函数，声明一个扩展函数，我们需要用一个接收者类型也就是被扩展的类型来作为他的前缀，下面为MutableList<Int>添加一个swap函数*/
//fun MutableList<Int>.swap(index1:Int,index2:Int){
//    /** this关键字在扩展函数内部对应到接收者对象（点符号前的对象）*/
//    val tmp = this[index1];//this对应该列表
//    this[index1]=this[index2]
//    this[index2] = tmp
//}

/** 这个函数对任何MutableList<T>都起作用，泛化它*/
fun <T> MutableList<T>.swap(index1:Int,index2:Int){
    val tmp = this[index1];//this对应该列表
    this[index1]=this[index2]
    this[index2] = tmp
}

/** 扩展是静态的解析的。并不能真正修改所扩展的类。并没有向类中插入新成员。
 *  扩展函数是静态分发的，他们不是根据接收者类型的虚方法，调用的扩展函数是由函数调用所在的表达式的类型来决定的，
 *  而不是又表达式运行时求值结果决定的
 * */
open class Shape
class Rect2 :Shape()
fun Shape.getName()="shape"
fun Rect2.getName()="rect"
fun printClassName(s:Shape){
    /** 这里只会输出shape,因为调用的扩展函数只取决于参数s的声明类型，该类型是shape*/
    println(s.getName())
}

/** 如果一个类定义有一个成员函数与一个扩展函数，而这两个函数又有相同的接收者类型，相同的名字，并且都适用给定的参数，
 *  这种情况总是取成员函数
 * */
class Example{
    fun printFunctionType(){
        println("class method")}
}
fun Example.printFunctionType(){
    println("extension  function ")
}
/** 扩展函数可以重载*/
fun Example.printFunctionType(i:Int){
    println("Extension function")
}

/** 可空接收者
 * 可以为可空接收者定义扩展。这样的扩展可以在对象变量上调用，即使值为Null,可以在函数体内检测this==null
 * 这能让你在没检测null的时候调用kotlin的toString() 检测发生在扩展函数的内部
 * */
fun Any?.toString():String {
    if(this==null) return "null"
    return toString()
}

/** 扩展属性
 * 扩展没有实际将成员插入类中，因此对扩展属性来说 幕后字段是无效的。这就是为啥 扩展属性不能有初始化器，他们的行为
 * 只能由显示提供的getter/setter定义
 * */
val <T> List<T>.lastIndex :Int
    get() = size-1

//val House.number = 1  错误，不能有初始化器

/** 伴生对象的扩展
 * 如果一个类定义有一个伴生对象，也可以为伴生对象定义扩展函数与属性。就像伴生对象的常规成员一样，可以只使用类名作为限定符
 * 类调用伴生对象的扩展成员
 * */
class MyClass{
    companion object{}
}
fun MyClass.Companion.printCompanion() { println("companion")}

/** 扩展声明为成员
 * 在一个类内部可以为另一个类声明扩展。在这样的扩展内部，有多个隐式接收者，其中的对象成员可以无需通过限定符访问。扩展
 * 声明所在的类的实例称为 分发接收者 ，扩展方法调用所在的接收者类型的实例称为 扩展接收者
 * */
class Host(val hostname:String){
    fun printHostname(){
        println(hostname)}
}
class Connection(val host:Host,val port:Int){
    fun printPort(){print(port)}
    fun Host.printConnectionString(){
        printHostname()
        printPort()
    }
    /** 对于分发接收者 与 扩展接收者的成员名字冲突的情况，扩展接收者优先，要引用分发接收者可以使用限定的this语法*/
    fun Host.getConnectionString(){
        toString() //调用hsot的toString
        this@Connection.toString()// 调用connection的toString

    }
    fun connect(){
        host.printConnectionString() //调用扩展函数
    }
}

/** 声明为成员的扩展可以声明为open并在子类中覆盖，这意味着这些函数的分发对于分发接收者类型是虚拟的
 *  但对于扩展接收者类型是静态的
 * */

open class Base2{}
class Derived2 :Base2(){}
open class BaseCaller{
    open fun Base2.printFunction(){
        println("base extension function in baseCaller")
    }
    open fun Derived2.printFunction(){
        println("deived extension function in baseCaller")
    }
    fun call(b:Base2){
        b.printFunction()
    }
}
class DerivedCaller:BaseCaller(){
    override fun Base2.printFunction(){
        println("base extension function in derivedCaller ")
    }
    override fun Derived2.printFunction(){
        println("Derived extension function in DerivedCaller")
    }
}


fun main(){
    BaseCaller().call(Base2())
    DerivedCaller().call(Base2()) //分发接收者虚拟解析
    DerivedCaller().call(Derived2()) //扩展接收者静态解析  其实这里还是跟传入Base2一样的结果

//     Connection(Host("kotl.in"),443).connect()
//    MyClass.printCompanion()

//    Example().printFunctionType(1)

//    printClassName(Rect2())

//    val list = mutableListOf<Int>(1,2,4)
//    list.swap(2,0)
//    println(list)
}

