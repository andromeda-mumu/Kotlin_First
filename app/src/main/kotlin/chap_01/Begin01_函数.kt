package chap_01

/**
 * Created by mumu on 2020/6/8.
 * 功能描述：
 */
fun main(){
    println("hello world")
    printSum(1,2)
    printSum2(3,8)
}

fun sum(a:Int,b:Int):Int{
    return a+b
}

/** 将表达式作为函数体，返回值类型自动推断的函数*/
fun sum2(a:Int,b:Int) =a+b;

/**函数返回无意义都值*/
fun printSum(a:Int,b: Int):Unit{
    println("sum of $a is ${a+b}")
}

/** unit可以省略*/
fun printSum2(a:Int,b:Int){
    println("sum of $a and $b is ${a+b}")
}

