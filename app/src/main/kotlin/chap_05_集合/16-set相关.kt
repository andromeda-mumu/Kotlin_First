package chap_05_集合

/**
 * Created by wangjiao on 2020/8/14.
 * description:
 */
fun main(){
    /** set常用操作：查找交集 并集 差集
     * a union b 并集
     * intersect() 交集
     * subtract() 差集
     *
     * list也支持set操作，但是对list进行set操作的结果任然是set.因此会删除所有重复元素
     * */
    val num = setOf<String>("one","two","three")
    println(num union setOf("four","five"))
    println(setOf("four","five") union  num)

    println(num intersect setOf("two","one"))
    println(num subtract setOf("three","four"))
    println(num subtract setOf("four","three"))


}