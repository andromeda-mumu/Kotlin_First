package chap_03

import kotlinx.coroutines.channels.consumesAll

/**
 * Created by mumu on 2020/6/20.
 * 功能描述：
 */
/**
 * 用来表示受限的类继承结构：当一个值为有限几种的类型，而不能有任何其他类型时，类似枚举类的扩展：
 * 枚举类型的值集合也是受限的，但每个枚举常量只存在一个实例，而密封的一个子类可以有可包含状态的多个实例
 *
 * 声明一个密封类，需要在类名前面添加sealed修饰符，虽然密封类也可以有子类，但所有子类必须在密封类自身相同的文件中声明
 * */

sealed class Expr
data class Const(val num:Double):Expr()
data class Sum(val el:Expr,val e2:Expr):Expr()
object NotaNumber :Expr()

/**
 * 一个密封类自身是抽象的，不能直接实例化，可以有抽象成员
 * 密封类不允许有非-private构造函数（其构造函数默认为private）
 * 扩展密封类子类的类（间接继承者）可以放在任何位置，而无需在同一个文件中
 * 使用密封类的关键好处在于使用when表达式，如果能够验证语句覆盖了所有情况，就不需要为该语句再添加一个else子句，
 *     必须在when作为表达式 而不是语句时才有用
 * */
fun eval(expr:Expr):Double=when(expr){
    is Const -> expr.num
    is Sum -> eval(expr.el)+ eval(expr.e2)
     NotaNumber -> Double.NaN  //不用is 也是可以的。
}

fun main() {
    var expr = Const(11.0)
    var data = eval(expr)
    println(data)

    var expr2 = NotaNumber
    println(eval(expr2))
}
