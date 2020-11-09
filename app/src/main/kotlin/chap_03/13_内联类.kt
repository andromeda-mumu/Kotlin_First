package chap_03

/**
 * Created by mumu on 2020/6/27.
 * 功能描述：
 */
/** 有时候，业务逻辑需要围绕某种类型创建包装器。然鹅，由于额外的堆内存分配问题，它会引入运行时的性能开销。此外，如果被包装的类型是原生类型，
 * 性能的损失是很糟糕的，原生类型通常在运行时就进行了大量优化，，然而他们的包装器却没有得到特殊的处理
 *
 * 为解决这个问题，kotlin引入了一种内联类 的特殊类 .
 * */
inline class Password(val value:String)

/**
 * 内联类必须含有唯一的一个属性在主构造函数中初始化。在运行时，将使用这个唯一属性来表示内联类的实例
 * */
val securePassword = Password("don't try this in production") //一个假容器，主要是里面的string.因此这是securePassword包含string,而不存在password这个对象。消除包装器。
/** 不存在‘password’ 类的真实实例对象。
 *  在运行时，‘securePassword'仅仅包含’String'
 *  这就是内联类的主要特性：类的数据被‘内联’到该类使用的地方（内联函数中的代码被内联到该函数调用的地方）
 * */

/** 成员：内联类支持普通类中的一些功能，特别是，内联类可以声明属性与函数*/
inline class Name(val s:String){
    val length :Int
        get() = s.length
    fun greet(){
        println("hello $s")
    }
}
//fun main(){
//    val name= Name("kotlin")
//    name.greet()
//    println(name.length)
//}
/** 内联类的成员有一些限制：不能含有init代码块 ，不能含有幕后字段 。因此内联类只能含有简单的计算属性（不能含有延迟初始化/委托属性）*/

/** 继承：允许继承接口*/
interface Printable{
    fun prettyPrint():String
}
inline class Name2(val s:String):Printable{
    override fun prettyPrint():String ="let's $s"
}
/** 禁止内联类参与到类的继承关系结构中，这以为着 内联类不能继承其他的类而且必须是final*/  //不能继承其他类，并且自身必须是final.
//fun main(){
//    val name = Name2("kotlin")
//    print(name.prettyPrint())
//}

/** 表示方式：在生成的代码中，kotln编译器为每个内联类保留一个包装器，内联类的实例可以在运行时表示为包装器或者基础类型。
 * 为了生成性能最优的代码，kotlin编译更倾向于使用基础类型而不是编译类型。但是，有时候使用包装器是必要的，
 * 一般说，只要将内联类用作另一种类型，它们就会被装箱
 * */

interface I
inline class Foo(val i:Int):I

fun asInline(f:Foo){}
fun <T> asGeneric(x:T){}
fun asInterface(i:I){}
fun asNullable(i:Foo?){}

fun <T> id(x:T):T =x

//fun main(){
//    val f = Foo(42)
//    asInline(f) //拆箱操作：用作Foo本身
//    asGeneric(f) //装箱操作：用作泛型类型T
//    asInterface(f) //装箱操作：用作类型I
//    asNullable(f) //装箱操作：用作不同于Foo的可空类型Foo?
//
//    val c = id(f) //先装箱，再拆箱
//}
/** 因为内联函数既可以表示为基础类型又可以表示为包装类型，引用相等是毫无意义的，所以这也是被禁止的*/

/** 名字修饰：由于内联类被编译为基础类型，因此可能会导致各种模糊的错误，如意想不到的平台签名冲突：*/
inline class UInt(val x:Int)
//在JVM平台上被表示为 public final void compute(int x)
fun compute(x:Int){}
//同理，在JVM平台上也被表示为 public final void compute (int x)
fun compute(x:UInt){}
/** 为了缓解这个问题，一般会通过在函数名后面拼接一些稳定的哈希码来重命名函数，因此，fun compute(x:UInt)
 * 将被表示为 public final void compute-<hashcode>（int x) 以此来解决这个问题
 * */
//在java中，‘-’是一个无效符号，也就是说在java中不能调用使用内联类作为形参的函数

/** 内联类与类型别名
 * 关键区别在：类型别名与其基础类型（以及具有相同基础类型的其他类型别名）是赋值兼容的，而内联类却不是这样。
 * 换句话说，内联类引入了一个真实的新类型，与类型别名正好相反，类型别名仅仅是为了现有的类型取了一个新的替代名称
 * */
typealias NameTypeAlias = String
inline class NameInlineClass(val s:String)

fun acceptString(s:String){}
fun acceptNameTypeAlise(n:NameTypeAlias){}
fun acceptNameInlineClass(p:NameInlineClass){}

fun main(){
    val nameAlias : NameTypeAlias =""
    val nameInlineClass :NameInlineClass = NameInlineClass("")
    val str:String =""
    acceptString(nameAlias) //正确，传递别名类型的实参 替代函数中基础类型的形参
//    acceptString(nameInlineClass) //错误：不能传递内联类的实参替代函数中基础类型的形参

    acceptNameTypeAlise(str) //正确 传递基础类型的实参 替代函数中别名类型的形参
//    acceptNameInlineClass(str) //错误 不能出传递基础类型的实参 替代函数中内联类类型的形参

    var ni = NameInlineClass(str)
    acceptNameInlineClass(ni) //可以
}

/** 内联类的实验性状态
 * 需要在gradle中启用内联类
 * */
//complieKotlin{
//    kotlinOptions.freeCompilerArgs +=["-Xinline-classes"]
//}

