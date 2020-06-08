package chap_01

/**
 * Created by mumu on 2020/6/8.
 * 功能描述：
 */
fun main(){
    /** 定义只读局部变量，val来定义*/
    var a:Int =1; //立即赋值
    var b =2;//自动推断Int类型
    var c:Int //没有初始值，类型不能省略
    c =3

    /** 可以重新赋值的变量，用var关键字*/
    var x= 5;//自动推断Int类型
    x +=1
    println(x)
    increamentZ()

}

/** 顶层变量*/
val PI = 3.14;
var z= 0
fun increamentZ(){
    z +=1
    println(z)
}