package chap_08_更多语言结构

import kotlin.reflect.KClass

/**
 * Created by wangjiao on 2020/8/28.
 * description:
 */
/** 可调用引用
 * 函数，属性，以及构造函数的引用，除了作为自省程序结构外，还可以用于调用或者用作函数类型的实例
 * 所有可调用引用的公共超类型是KCallable<out R> ,其中R是返回值类型，对于属性是属性类型，对于构造函数时所构造类型
 * */

/** 函数引用
 * 当有个具名函数声明如下
 *
 *
 * */
fun isOdd(x:Int) = x%2==0
//fun main() {
//    val number = listOf<Int>(1,2,3)
//    println(number.filter(::isOdd)) //将其传给filter函数
//}
/**
 * 这里 ::isodd 是函数类型（Int）->Boolean 的一个值
 * 函数引用属于KFunction<out R>的子类型之一，取决于参数个数，如 KFunction3<T1,T2,T3,R>
 * 当上下文中已知函数期望的类型时，::可以用于重载函数。
 * */
fun isOdd(s:String) =s=="brilling"||s=="slitg"||s=="tonve"
//fun main() {
//    val  number = listOf(1,2,3)
//    println(number.filter(::isOdd))
//}
/** 或者可以将方法引用存储在具有显示指定类型的变量中来提供必要的上下文*/
val predicate:(String)->Boolean =::isOdd  //引用到isOdd(x:String)

/** 如果需要使用类的成员函数或扩展函数，它需要是限定的，如 String::toCharArray
 *  即使以扩展函数的引用初始化一个变量，其推断出的函数类型也会没有接收者（它会有一个接收者对象的额外参数） 如需改为带有接收者的函数类型，需指明其类型
 * */
val isEmptyStringList:List<String>.()->Boolean = List<String>::isEmpty

/** 函数组合*/
fun <A,B,C> compose(f:(B) -> C,g:(A)->B):(A)->C{
    return {x->f(g(x))} //g(x)得到一个值，作为f(x)函数的参数。
}
fun length(s:String) = s.length
//fun main() {
//    val oddLength = compose(::isOdd,::length)
//    var strings = listOf<String>("a","ab","abc")
//    println(strings.filter(oddLength))
//}

/** 属性引用
 * 把属性作为kotlin中一等对象来访问，也可以使用::运算符
 *
 * 表达式::x求值为KProperty<Int> 类型的属性对象，它允许我们使用get()读取它的值，或者使用name属性来获取属性名
 * */
val x =1
//fun main() {
//    println(::x.get()) //::X 是一个KProperty属性对象，那么get()方法可以得到其值
//    println(::x.name)//name可以得到KProperty的名字
//}

/** 对于可变属性，如var y =1 ,::y 返回KMutableProperty<Int>类型的一个值，该类型有一个set()方法*/
var y=1
//fun main() {
//    ::y.set(2) //对y这个属性对象设置一个值2
//    println(y)
//
//    //属性引用可以用在预期具有单个泛型参数的函数的地方
//    val str = listOf("a","bv","def")
//    println(str.map(String::length))
//}

/**
 * 要访问类的成员属性
 * */
//class AA2(val p:Int)
//fun main() {
//    val prop = AA2::p //获得p属性
//    println(prop.get(AA2(1))) //获得AA2(1)这个类的p值
//}

/** 对于扩展属性 */
val String.lastChar :Char
    get()=this[length-1]
//fun main() {
//    println(String::lastChar.get("abc"))
//}

/** 与java反思的互操作性
 * 查找一个用作kotlin属性getter的幕后字段或java方法
 * */

class BB(val p:Int)
//fun main(){
//    println(BB::p.javaGetter)
//    println(BB::p.javaField)
//}
/** 获得对应java类的kotlin类，使用.kotlin扩展属性*/
fun getKClass(o:Any): KClass<Any> = o.javaClass.kotlin


/** 构造函数引用
 * 构造函数可以像方法和属性那样引用，它们可以用与期待这样的函数类型对象的任何地方：它与该构造函数接受相同参数并返回相应类型的对象。
 * 通过使用::操作符并添加类名类引用构造函数，
 *
 * */
//class Foo
//fun func(factory:() ->Foo){
//    val x:Foo =factory()
//}
//fun main() {
//    func(::Foo) //调用类Foo的零参数构造函数
//}

/** 绑定的函数与属性引用
 * 可以引用特定对象的实例方法
 * */
//fun main() {
//    val numberRegex = "\\d+".toRegex()
////    println(numberRegex.matches("29"))
////
////    val isNumber = numberRegex::matches
////    println(isNumber("29"))
//
//    val strs = listOf<String>("abc","123","a70")
//    println(strs.filter(numberRegex::matches))
//}

/** 比较绑定的类型和相应的为绑定类型的引用。
 * 绑定的可调用引用有其接收者 附加 到其上，因此接收者的类型不再是参数
 * */
val numberRegex = "\\d+".toRegex()
val isNumber:(CharSequence) ->Boolean = numberRegex::matches
val matches:(Regex,CharSequence) ->Boolean = Regex::matches

//fun main() {
//    //属性引用也可以绑定
//    val prop = "abc"::length
//    println(prop.get())
//}
//kotlin1.2起，无需显示指定this作为接收者，this::Foo 与 ::Foo 是等价的

/** 绑定的构造函数引用
 * inner类的构造函数的绑定的可调用引用可通过提供外部类的实例来获得
 * */
class Outer{
    inner class Inner{
        init {
            println("嘿嘿")
        }
    }
}

fun main() {
    val o =Outer()
    val boundInnerCtor = o::Inner //inner的构造函数
    boundInnerCtor()
}













