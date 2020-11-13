package chap_05_集合

/**
 * Created by wangjiao on 2020/8/11.
 * description:
 */
fun main(){
    /**
     * min() max() 最小 最大值
     * average() 平均值
     * sum() 元素总和
     * count() 返回集合中元素的数量
     * */
//    val numbers = listOf(4,2,5,6)
//    println(numbers.min())
//    println(numbers.max())
//    println(numbers.average())
//    println(numbers.count())
//    print(numbers.sum())

    /** 选择器函数/自定义 comparator 检索最小和最大元素
     * maxBy() minBy()
     * maxWith()  minWith() 接受一个comparator对象
     * */
//    val numbers = listOf(3,42,10,4,9)
//    val min3Remainder = numbers.minBy { it%3 }
//    println(min3Remainder)
//    val strings = listOf("one","two","three","four")
//    val longestString = strings.maxWith(compareBy { it.length })
//    println(longestString)

    /** 高级求和
     * sumBy() 使用对集合元素调用返回int值的函数
     * sumByDouble() 与返回Double的函数一起使用
     * */
//    val numbers = listOf(5,42,10,4)
//    println(numbers.sumBy { it*2 })
//    println(numbers.sumByDouble { it.toDouble()/2 })

    /**
     * reduce()与fold() 操作有2个参数：先前的累积值和集合元素
     * reduce是第一个和第二个元素作为参数，因此第一个元素不会加倍
     * */
//    val numbers= listOf(5,2,10,4)
//    val sum = numbers.reduce{sum,element -> sum+element}
//    println(sum)
//    val sumDoubled = numbers.fold(3){sum,element -> sum+element*2}
//    println(sumDoubled)

    /** reduceRight() foldRight() 操作参数会改变顺序，第一个参数为元素，第二个参数为累积值*/
//      val numbers = listOf(5,2,10,4)
//      val sumDoubledRight = numbers.foldRight(0){element,sum->sum+element*2}
//      println(sumDoubledRight)

    /** reduceIndex() foldIndex() 索引可以作为参数 */
    val numbers = listOf(5,2,10,4)
    val sumEven = numbers.foldIndexed(0){idx,sum,element -> if(idx%2==0) sum+element else sum}
    println(sumEven)//15
    val sumEvenRight = numbers.foldRightIndexed(0){idx,element,sum -> if(idx%2==0) sum+element else sum}
    println(sumEvenRight)//6


}