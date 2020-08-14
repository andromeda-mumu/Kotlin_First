package chap_05_集合

/**
 * Created by wangjiao on 2020/8/11.
 * description:
 */
fun main(){
    /** 可以把集合当做字符串处理 */
//    val numbers = listOf("one","two","three","four","five","six")
//    println(numbers.slice(1..3))
//    println(numbers.slice(0..4 step 2))
//    println(numbers.slice(setOf(3,5,0)))

    /** take 获得部分元素 与 drop 去掉某个元素*/
//    val numbers = listOf("one","two","three","four","five","six")
//    println(numbers.take(3))
//    println(numbers.takeLast(3))
//    println(numbers.drop(1))
//    println(numbers.dropLast(5))

    /**可以使用谓词来定义要获得或去除的元素的数量
     * takeWhile()是带有谓词的take():不停获得元素，直到排除与谓词匹配的首个元素，如果首个集合元素与谓词匹配，则结果为空
     * takeLastWhile()与takeLast() 类似：它从集合末尾获取与谓词匹配的元素区间，
     * dropWhile()
     * dropLastWhile()
     * */
//    val numbers = listOf("one","two","three","four","five","six")
//    println(numbers.takeWhile { !it.startsWith('f') })
//    println(numbers.takeLastWhile { it!="three" })
//    println(numbers.dropWhile { it.length==3 })
//    println(numbers.dropLastWhile { it.contains('i') })

    /** chunked 要将集合分解为给定大小的块。*/
//
//    val numbers = (0..13).toList()
//    println(numbers.chunked(3))
//    println(numbers.chunked(3){it.sum()})

    /** windowed 可以检索给定大小的集合元素中所有可能区间。*/
//    val numbers = listOf("one","two","three","four","five","six")
//    println(numbers.windowed(3))

//    val numbers = (1..10).toList()
//    println(numbers.windowed(3,step=2,partialWindows = true))
//    println(numbers.windowed(3){it.sum()})

    /** 要构建两个元素的窗口，有一个单独的函数-zipWithNext()。它创建接收器集合的相邻元素对。zipWithNext()不会将集合分成挤兑。*/
    val numbers = listOf("one","two","three","four","five","six")
    println(numbers.zipWithNext())
    println(numbers.zipWithNext(){s1,s2->s1.length > s2.length})
}