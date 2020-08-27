package chap_08_更多语言结构


/**
 * Created by wangjiao on 2020/8/27.
 * description:
 */
/** 把一个对象 解构 成很多变量会很方便*/
//class person
//fun main(){
//    val (name,age)=person
//    //可以独立使用
//    println(name)
//    println(age)
//
//    /** 任何表达式都可以出现在解构声明的右侧，只要对它调用所需数量的component函数即可。*/
//    for((a,b) in collection){}
//    /** 变量a,b的值，取自集合中元素上调用component1() 和 component2()的返回值*/
//
//}

/** 从函数中返回两个变量 */
//data class Result(val result:Int,val status :Status)
//fun method():Result{
//    return Result(result,stauts)
//}
//fun main(){
//    val (result,status) = method()
//}

/**
 * 下划线用语未使用的变量 ，以这种方式跳过组件，不会调用相应的componentN()操作符函数
 *
 * val(_,status) = getResult()
 * */

/** 在lambda表达式中的解构
 * map.mapValues{entry -> "${entry.value}!"}
 * map.mapValues{ (key,value) -> "$values!"}
 *
 * 声明两个参数和声明一个解构对来取代单个参数之间的区别：
 * {a //->...}// 一个参数
 * {a,b // -> ..}//两个参数
 * {(a,b),c -> ...}//一个解构对 和其他参数
 *
 * map.mapValues{ (_,value) ->"$value!"}
 *
 * 可以指定整个解构的参数的类型 或者分别指定特定组件的类型
 * map.mapValues{(_,value):Map.Entry<Int,String> -> "$value"}
 * map.mapValues{(_,value:String) -> "$value!"}
 * */