package chap_05_集合

/**
 * Created by wangjiao on 2020/8/11.
 * description:
 */
fun main(){
    /** plus 和 minus 操作符，这利用在集合中，是对集合的增减*/
    val numbers = listOf("one","two","three","four")
    val plusList = numbers +"five"
    val minusList = numbers-listOf("three","four")
    println(plusList)
    println(minusList)

    /** 还有赋值操作符，plusAssign(+=) minusAssign(-=),只可以用在只读集合中，可变集合会修改集合。*/
}