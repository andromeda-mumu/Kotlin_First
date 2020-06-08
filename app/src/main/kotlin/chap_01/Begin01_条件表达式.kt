package chap_01

/**
 * Created by mumu on 2020/6/8.
 * 功能描述：
 */
fun main(){
    print(maxOf(1,9))
    print(minOf(1,9))
}
fun maxOf(a:Int,b:Int):Int{
    if(a>b)return a
    else return b
}

/** 简写的办法*/
fun minOf(a:Int,b:Int)= if(a<b) a else b