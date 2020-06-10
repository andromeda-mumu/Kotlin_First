package chap_01

/**
 * Created by mumu on 2020/6/10.
 * 功能描述：
 */

/** 幕后属性名称
 * 当有相同名称，但不同作用域时，对私有属性使用下划线名称都前缀
 * */
class C{
    private val _elementList = mutableListOf<Element>()
    val elementList:List<Element>
        get()=_elementList

}
class Element{}

/** 类头格式化*/
class Person(
    id:Int,
    name:String,
    surname:String
):Human(id,name)
open class Human(id:Int, name:String){
}

/** 文件注解之后，package语句之前，用一个空白行 与 package分开 （强调其针对文件为而不是包）*/
//@file:JvmName("FooBar")
//
//package foo.bar

/** 如果函数签名不适合单行，使用以下语法*/
fun longMethodName(
    argument:String="hello",
    argument2:Int
):Int{
    return 0;
}

/** 单个表达式构成的函数体*/
fun foo():Int{
    return 1
}
fun foo3()=1

/** 表达式函数体*/
fun f(x:String)=
    x.length

/** 属性格式化，对于简单的只读属性，考虑单行*/
val isEmpty:Boolean get()="a".length==0

/** 对于复杂的熟悉，get与set关键字放在不同的行上*/
val foo2:String
    get(){return ""}

/** 对于具有初始化器的熟悉，如果初始化器很长，那么在等号后增加一个换行并将初始化器缩进四个空格*/
//private val defaultCharser: Charset?=
//    EncodingRegistry.getInstance().getDefaultCharsetForPropertiesFiles(file)

/** 格式化控制流语句*/
/*if(!comonent.isSyncing &&
  !hasAnyKotlinRuntimeInscope(module)
){
    return xxx();
}      */
/** 链式调用换行*/
/*
val anchor = Owner
    ?.firstChild!!
    .siblings(forward = true)
    .dropWhite { it is PsiComent || it is PsiWhiteSpace }

*/

/** 为lambda表达式中声明参数名时，将参数名放在第一行，后跟箭头与换行符 */
/*
appendCommSeparated(properties) { prop ->
    val propertyValue = prop.get(obj)
}*/

/** 如果参数列表太长无法放一行，则将箭头放在单独一行*/
/*
foo4 {
    context : Context,
    environment : Env
    ->
    context.configureEnv(Environment)
}*/

/** 追溯要这样写，加链接*/
/**
 * Returns the absolute value of the given [number]
 */
fun abs(number:Int):Int{return 1}

/**
 * 语言特性得惯用法
 * 不可变性: 优先使用不可变数据。val 声明
 * 总是使用不可变集合接口（Collection,List,Set,Map）来声明无需改变都集合
 * */

/*fun wrong(arg:String,arg2:String):HashSet<String>{}
fun aa(arg:String,arg2:String):Set<String>{}*/

val wrong2 = arrayListOf("a","b","c")
val right = listOf("a","b","c")

/** 优先声明带有默认参数都函数，而不是重载函数
 * 评价：这样两个函数都可以变成一个函数了
 * */
//wrong
fun a1()=a1("a")
fun a1(a:String){}

//right
fun b1(a:String="a"){}


/** 类型别名 */
/*
typealias MouseClickHandler = (Any, MouseEvent) -> Unit
typealias PersonIndex = Map<String, Person>
*/

/** 具体参数 */
//drawSquare(x = 10, y = 10, width = 100, height = 100, fill = true)

/** 使用条件语句*/
//return if(x) foo() else bar()

/*
return when(x){
    0 -> "zero"
    else -> "nonzero"
}*/

/** 条件中使用可空的boolean值*/
//if(value == true )

/** 区间上循环*/
/*
for (i in 0..n-1){} //差
for (i in 0 until n){} //好
*/

/** 优先使用字符串模板
 * 多行字符串维护缩进，当不是缩进时用trimIndent 当使用缩进时用trimMargin
 * */
/*
assertEquals(
"""
    Foo
    Bar
    """.trimIndent(),
value
)

val a = """if(a > 1) {
          |    return a
          |}""".trimMargin()
*/

/**
 * 工厂函数
 * 如果一个对象有多个重载的构造函数，它们并非调用不同的超类构造函数，
 * 并且不能简化为具有默认参数值的单个构造函数，那么优先用工厂函数取代这些重载的构造函数。
 * */