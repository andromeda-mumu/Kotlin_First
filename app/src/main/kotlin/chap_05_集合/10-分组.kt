package chap_05_集合

/**
 * Created by wangjiao on 2020/8/11.
 * description:
 */

fun main(){
//    val numbers = listOf("one","two","three","four","five")
//    println(numbers.groupBy{it.first().toUpperCase()})
//    println(numbers.groupBy(keySelector = {it.first()},valueTransform = {it.toUpperCase()}) )
//    结果
//    {O=[one], T=[two, three], F=[four, five]}
//    {o=[ONE], t=[TWO, THREE], f=[FOUR, FIVE]}

    /**
     * grouping 支持以下操作：
     * -eachCount()计算每个组中的元素
     * -fold()与reduce()对没个组分别执行fold与reduce操作，作为一个单独的集合并返回结果
     * -aggregate() 随后将给定操作应用于每个组中的所有元素并返回结果。
     *
     * */
    val numbers = listOf("one","two","three","four","five")
    println(numbers.groupingBy{it.last()}.eachCount())
    //结果：{e=3, o=1, r=1}

}