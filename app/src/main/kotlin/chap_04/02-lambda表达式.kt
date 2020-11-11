package chap_04

/**
 * Created by wangjiao on 2020/7/31.
 * description:
 */

/**
 * lambda表达式与匿名函数是“函数字面值”，即未声明的函数，但立即作为表达式传递。
   max(strings,{a,b -> a.length< b.length})
   max是一个高阶函数，第二个参数时一个函数表达式，等价于
  fun compare(a:int,b:int):Boolean =a.length<b.length
 * */


/** lambda表达式完整语法如下
    val sum:(Int:Int)-> Int = {x:Int,y:Int -> x+y}
    val sum={x:Int,y:Int -> x+y}

 */


/**
 * 传递末尾的lambda表达式
 * 在kotlin有个约定，如果函数最后一个参数时函数，那么作为相应参数传入lambda表达式可以放在圆括号之外
 * val product = items.fold(1){acc,e -> acc*e}  也称为拖尾lambda表达式
 *
 * 如果lambda表达式是调用时唯一的参数，那么圆括号可以省略
 * run { println("..") }
 *
 * */

/**
 * it单个参数的隐式名称
 * 如果编译器可以识别出签名，也可以不用声明唯一的参数并忽略 -> 。该参数会隐式声明为it:
 * ints.filter {it > 0 }  字面量是“ (it:Int) -> Boolean ”类型的
 * */

/**
 * lambda表达式返回一个值
 * 可以显式的指定返回，也可以隐式的返回最后一个表达式
 * 这两个是等价的
 *
 * ints.filter{
 *  val shouldFilter = it>0
 *  shouldFilter
 * }
 *
 * ints.filter{
 *  val shouldFilter = it>0
 *  return@filter shouldFilter
 * }
 *
 * 所以LINO风格的代码：
 * strings.filter { it.length==5 }.sortedBy { it }.map { it.toUpperCase() }
 * */

/**
 * 下划线可以表示 未被使用的参数
 * map.forEach { _,value -> println("$value") }
 * */

/**
 * 匿名函数
 * lambda表达式中，大部分情况是可以推断出返回类型的，而有些需要显示指定，可以使用另一种语法：匿名函数
 * fun(x:Int,y:Int):Int =x+y
 * 匿名函数看起来非常像一个常规函数声明，除了名称省略。函数体可以是表达式或代码块
 * fun(x:Int,y:Int):Int{
 *  return x+y;
 * }
 * ints.filter(fun(item)=item>0)
 *
 * 对于具有表达式函数体的匿名函数将自动推断返回类型，而具有代码块函数体的返回类型必须显示指定
 *
 * lambda表达式与匿名函数之间的一个区别:非局部返回的行为。
 *  - 一个不带标签的return 语句总是在用fun关键字声明的函数中返回。
 *  - lambda表达式中的return将从包含它的函数返回，而匿名函数中的return将从匿名函数自身返回
 * */

/**
 * 闭包
 *  lambda表达式或匿名函数可以访问闭包，即在外部作用域中声明的变量，在lambda表达式中可以修改闭包中捕获的变量
 *  val sum =0
 *  ints.filter { it>0 }.forEach {
 *      sum += it
 *  }
 *  println(sum)
 * */

/**
 * 带有接受者的函数字面值
 * 如 A.(B) -> C 可以用特殊形式的函数字面值实例化
 * 在这样函数字面值内部，传给调用的接受者对象称为隐式this.以便访问接受者对象的成员而无需额外的限定符，可使用this表达式 访问接受者对象
 * 这种行为与扩展函数类似，扩展函数也可以在函数体内部访问接收者对象的成员
 * val sum:Int.(Int) -> Int ={ other-> plus(other) }
 * val sum = fun Int.(other:Int):Int=this+other
 *
 * 当接收者类型可以从上下文推断，lambda表达式可以用作带接收者的函数字面值。
 * class HTML {
 *  fun body(){}
 * }
 *
 * fun html(init:HTML.()->Unit):HTML{
 *      val html = HTML() 创建接收者对象
 *      html.init() //将该接收者对象传给lambda表达式
 *      return html
 * }
 *
 * html{ //带接收者的lambda表达式由此开始
 *      body()  //调用该接收者对象的一个方法
 * }
 * */


