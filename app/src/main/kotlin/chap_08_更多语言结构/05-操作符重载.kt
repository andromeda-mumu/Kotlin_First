package chap_08_更多语言结构

/**
 * Created by wangjiao on 2020/8/27.
 * description:
 */
/** 重载一元减运算符的实例*/
//data class Point(val x:Int,val y:Int)
//operator fun Point.unaryMinus() = Point(-x,-y)
//val point =Point(10,20)
//fun main() {
//    println(-point)
//}

/** 二元运算符*/
data class Counter(val dayIndex:Int){
    operator fun plus(increment:Int):Counter{
        return Counter(dayIndex+increment)
    }
}

fun main() {
    println(Counter(4)+6)
}