package chap_01

/**
 * Created by mumu on 2020/6/8.
 * 功能描述：
 */
fun main(){
        println(describe(1))
    println(describe("hello"))
    println(describe(100L))
    println(describe(12))
    println(describe('c'))

}
/* 这种比switch厉害 */
fun describe(obj:Any):String=
    when(obj){
        1->"one"
        "hello"->"Greeting"
        is Long->"Long"
        !is String -> "not a String"
        else ->"unknow"
    }
