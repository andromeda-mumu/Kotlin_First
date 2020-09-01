package chap_09_java互操作

import org.jetbrains.annotations.NotNull
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by wangjiao on 2020/8/31.
 * description:
 */

fun demo(source:List<Int>){
    val list = ArrayList<Int>()
    for(item in source){
        list.add(item)
    }
    for(i in 0..source.size-1){
        list[i] = source[i]
    }
}

/** 如果java类只有一个setter,它在kotlin中不会作为属性可见，因为kotlin不支持只写（set-only）*/
fun calendarDemo(){
    val calendar = Calendar.getInstance()
    if(calendar.firstDayOfWeek==Calendar.SUNDAY){
        calendar.firstDayOfWeek = Calendar.MONDAY
    }
    if(!calendar.isLenient){
        calendar.isLenient = true
    }
}

/** 将kotlin中是关键字的java标识符进行转义
 * 如 in ,object ,is 如果一个java库使用了 kotlin关键字作为方法，你仍然可以通过反引号（`）字符转义它来调用该方法
 * */
//foo.`is`(bar)

/** 空安全与平台类型
 * 当调用平台类型变量的方法时，kotlin不会在编译时报告可空性错误，但在运行时调用可能会失败。因为空指针异常或者kotlin生成的阻止空值传播的断言
 * 如果选中非空类型，编译器会在赋值时触发一个断言，防止kotlin的非空变量保存空值，当我们把平台值传递给期待非空值等的kotlin函数时，也会触发断言。
 * 总的来说，编译器会尽力阻止空值通过程序向远传播（尽管鉴于泛型的原因，这不可能完全消除）
 * */
//fun main(){
//    val list = ArrayList<String>()
//    list.add("item")
//    val size = list.size //非空 （原生 int）
//    val item = list[1] //推断为平台类型（普通java对象）
////    item.substring(1)
//
//    val nullable:String?=item //允许，没有问题
//    val notNull:String =item  //允许，运行时可能失败
//}
/**平台类型表示法
 * - T！ 表示“T 或者 T？”
 * -（Mutable）Collection<T>! 表示“可以可变或不可变、可空或不可空的T的java集合”
 * - Array<(out)T>! 表示“可空或者不可空的T（或T的子类型）的java数组”
 * */

/**可空性注解
 * 具有可空性注解的java类型并不表示为平台类型，而是表示为实际可空或非可空的kotlin类型。编译器支持多种可空性注解，包括：
 * -JetBrains  （@Nullable @NotNull）
 * -Android
 * -JRS-305
 * -FindBugs
 * -Eclipse
 * -Lombok
 * */

//java声明的注解
//@NotNull
//Set<@NotNull String> toSet(@NotNull Collection<@NotNull String> elements){}

//在kotlin中可见的以下签名
//fun toSet(elements:(Mutable)Collection<String>):(Mutable)Set<String>{}

//如果没有@NotNull注解，类型参数会是平台类型
//fun toSet(element:(Mutable)Collection<String!>):(Mutable)Set<String!>{}


/**类型限定符默认值
 * ElementType.METHOD 用于方法的返回值
 * elementType.PARAMETER 用于值参数
 * elementType.FIELD 用于字段
 * elemetType.Type_use  用于任意类型
 * */

/**kotlin中的java泛型
 * -java中通配符转换成类型投影
 *  -Foo<? extend Bar>转换成 Foo<out Bar!>!
 *  -Foo<? super Bar> 转换成 Foo<in Bar!>!
 *
 * -java的原始类型转换成星投影
 *  -list转换成 list<*>! 即 list<out Any?>!
 * */

/**java反射，可以使用instance::class.java ,ClassName::clas.java  或者instance.javaClass 通过java.lang.Class来进行java反射
 *
 * */










































