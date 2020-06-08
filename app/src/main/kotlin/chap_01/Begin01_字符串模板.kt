package chap_01

/**
 * Created by mumu on 2020/6/8.
 * 功能描述：
 */
fun main(){
    var a =1
    var s1 = "a is $a"
    a =2
    val s2 = "${s1.replace("is","was")},but now is $a"
    print(s2)
}

