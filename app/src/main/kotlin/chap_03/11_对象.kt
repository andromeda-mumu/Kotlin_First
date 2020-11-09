package chap_03

/**
 * Created by mumu on 2020/6/26.
 * 功能描述：
 */
/** 如果超类型有一个构造函数，则必须传递适当的构造函数参数给它。多个超类型可以由跟在冒号后面的逗号分隔的列表指定*/
open class A1(x:Int){
    public open val y:Int =x
}
interface B1{}
val ab:A1 = object : A1(1),B1{
    override val y = 15
}

val ab2:A1 =object : A1(1),B1{}
//fun main() {
//    println(ab.y)
//    println(ab2.y)
//}


/** 任何时候，如果我们只需要一个对象而已，并不需要特殊超类型，那么可以简单写*/
fun foo2(){
    val adHoc = object {  //对象用obejct 来声明匿名对象吧。
        var x:Int =0
        var y:Int = 0
    }
    print(adHoc.x+adHoc.y)
}

/** 请注意，匿名对象可以用作只在本地和私有作用域中声明的类型。如果使用匿名对象作为共有函数的返回类型或者作用公有属性的类型，那么
 * 该函数或属性的实际类型会是匿名对象声明的超类型，如果没有声明任何超类型，则会是any,在匿名对象中添加的成员将无法访问
 * */
class C1{
    //私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object{ //这个函数的返回类型，是一个匿名对象。
        val x:String ="X"
    }
    //公有函数，返回类型是Any
    fun publicFoo()=object {
        val x:String ="x"
    }
    fun bar(){
        val x1 = foo().x
//        val x2 = publicFoo().x //错误，未能解析的引用“x"
    }
}

/** 对象表达式中的代码可以访问来自包含它的作用域的变量*/
//fun countClicks(window:JComponent){
//    var clickCount = 0
//    var enterCount =0
//    window.addMouseListener(object:MouseAdapter(){
//        override fun mouseClicked(e:MouseEvent){
//            clickCount++
//        }
//        override fun mouseEntered(e:MouseEvent){
//            enterCount++
//        }
//    })
//}

/** 对象声明， */
//object DataProvierManaer{
//    fun register(provider:DataProvier){}
//    val allDataProviders :Collection<DataProvider>
//        get() = //
//}

/** 这就是对象声明，并且它总是在object关键字后跟一个名称，就像变量声明一样，对象声明不是一个表达式，不能用在赋值语句的右边
 * 对象声明的初始化过程是线程安全得  并且在首次访问时进行
 * 如需引用该对象，我们直接使用其名称即可
 * */
//DataProvierManaer.register()

/** 这些对象可以有超类型*/
//object DefaultListener:MouseAdapter(){
//    override fun mouseClicked(e:MouseEvent){}
//    override fun mouseEntered(e:MouseEvent){}
//}

/** 对象声明不能在局部作用域那（即直接嵌套在函数内部）,但是它们可以嵌套到其他对象声明或非内部类中*/

/** 伴生对象 类内部的对象声明可以用companion关键字标记 */
class myClass{
    companion object Factory{ //伴生对象，其实就是类内部的对象。
        fun create():myClass = myClass()
    }
}
/** 该伴生对象的成员可以通过只是用类名作为限定符来调用*/
val instance = myClass.create() //没有限制，很方便
/** 可以省略伴生对象名称，这种情况将使用名称 Cmpanion*/
class myClass2{
    companion object {}
}
val x = myClass2.Companion
/** 其自身所用的类的名称（不是另一个名称的限定符）可用作对该类的伴生对象（无论是否具名）的引用*/
class d1{
    companion object Named{}
}
val d = d1
class d2{
    companion object{}
}
val y = d2

/** 注意：即使伴生对象的成员看起来像其他语言都静态成员，在运行时他们仍然是真实对象都实例成员，而且，例如还可以实现接口*/
interface Factory<T>{
    fun create() :T
}
class cls{
    companion object:Factory<cls>{
        override fun create(): cls = cls()
    }
}
val f :Factory<cls> = cls

/** 对象表达式与对象声明的语义差异
 * - 对象表达式是在使用他们的地方立即执行（及初始化）的
 * - 对象声明在第一次被访问到时延迟初始化的
 * - 伴生对象的初始化是在相应的类被加载（解析）时，与java静态初始化器的语义相匹配
 * */