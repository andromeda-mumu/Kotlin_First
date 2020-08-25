package chap_04

/**
 * Created by mumu on 2020/7/2.
 * 功能描述：
 */
/** 默认参数 函数参数可以有默认值，当省略相应的参数时使用默认值，与其他语言想比，减少重载数量*/
fun read(b:Array<Byte> ,off :Int=0,len:Int=b.size){
}
/** 覆盖方法总是使用与基类型方法相同的默认参数值。当覆盖一个带有默认参数值的方法时，必须从签名中省略默认参数值*/
open class A{
    open fun foo(i:Int =10){}
}
class B:A(){
    override fun foo(i:Int){} // 不能有默认值
}

/** 如果一个默认参数在一个无默认值的参数之前，那么该默认值只能通过使用 具名参数 调用该函数来使用*/
fun f(bar:Int =0,baz:Int){}

/** 如果在默认参数之后的最后一个参数lambda表达式，那么它既可以作为具名从参数在括号内传入，也可以在括号外传入*/
fun f2(bar:Int =0,baz:Int=1,quex:()->Unit){}

/** 具名参数 可以在调用函数时使用具名参数*/
fun f3(str:String,normal:Boolean=true,letter:Boolean = true,home:Boolean = false,word :Char=' '){}

/** 当一个函数调研混用位置参数与具名参数时，所有位置参数都要放在第一个具名参数之前，如允许调研f(1,y=2) 不允许f(x=1,2)
 *  可以通过星号 操作符将 可变数量参数  以具名形式传入
 * */
fun f4(vararg strs:String){}

//fun main(){
//    f4(strs = *arrayOf("a","b","C"))
//
//    f3("aa",word = 'a')
//    f3("aa",true,true,false,'_')//不具有可读性
//    f3("aaa")
//
//    f2(1){ println("hello")}//使用默认值baz
//    f2(quex = { print("hello")})//使用两个默认值
//    f2{ print("hello")}//使用两个默认值
//    f(baz = 1)
//}
//


/** 返回unit的函数 */
fun printHello2(name :String):Unit{
    if(name != null){
        println("ello $name")
    }else{
        println("hi here")
    }
}
/** Unit可选，可以不写*/
fun printhello(name:String?){}

/** 单表达式函数 ,函数返回单个表达式*/
fun double(x:Int):Int =x+2
/** 当返回值类型可由编译器推断时，显示声明返回类型是可选的*/
fun double2(x:Int) =x+2

/** 可变数量的参数（Varargs)。函数的参数（通常是最后一个）可以用vararg修饰符标记 */
fun <T> asList(vararg ts:T):List<T>{
    val result = ArrayList<T>()
    for(t in ts)
        result.add(t)
    return  result
}

/** 中缀表示法
 *  标有infix 关键字的函数也可以使用中缀表示法调用。中缀函数必须满足以下要求：
 *  - 他们必须是成员函数或扩展函数
 *  - 他们必须只有一个参数
 *  - 其参数不得接受 可变数量的参数 且 不能有默认值
 * */
infix fun Int.shl(x:Int){}

/** 中缀函数调用的优先级低于算术操作符，类型转换以及rangeTo 操作符。以下表达式是等价的：
 * - 1 shl 2+3  等于 1 shl (2+3)
 * - 0 until n*2 等价于 0 until (n*2）
 * - xs union ys as Set<*> 等价于 xs union (ys as Set<*>)
 *
 * 另一方面，中缀函数调用的优先级高于布尔操作符 && 与||、is -与 in-检测以及其他一些操作，这些表达式也是等价的
 * - a && b xor c 等价于 a && (b xor c)
 * - a xor b in c 等价于 （a xor b) in c
 * */

class my{
    infix fun add(s:String){}
    fun build(){
        this add "abc" //正确
        add("abc")  //正确
//        add "abc" 错误：必须指定接收者
    }
}

/** 函数作用域 顶层函数，局部作用域，成员函数，扩展函数*/

/** 局部函数，一个函数在另一个函数内部*/
fun dfs(graph:Int){
    var school:String ="good"
    fun dfs(name:String,age:Int){
        if(age>school.length){//局部函数可以方位外部函数的局部变量
            return
        }
    }
}

/** 成员函数 在类或对象内部定义的函数 */
class Sample{
    fun foo(){}
}

/** 泛型函数*/
//fun <T> singletonList(item:T):List<T>{}

/** 内联函数，扩展函数 ，高阶函数 lambda表达式*/
/** 尾递归函数  无堆栈溢出风险*/
val rps = 1E-10
tailrec fun findFixPoint(x:Double = 1.0):Double
    = if(Math.abs(x-Math.cos(x))<rps) x else findFixPoint(Math.cos(x))

fun main(){
    //用中缀表示法调用该函数
    1 shl 2 //等同于
    1.shl(2)

    /** 如果在有一个数组并希望将其内容传递给该函数，我们使用伸展操作符（在数组前面加*）*/
    val a = arrayOf(1,2,4)
    val l = asList(-1,0,*a,3)

    val list = asList(1,2,3)
}



