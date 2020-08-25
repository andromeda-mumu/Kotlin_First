package chap_05_集合

/**
 * Created by wangjiao on 2020/8/11.
 * description:
 */
fun main(){
    /** 按位置取 */
//    val numbers = listOf("one","two","three","four","five","six")
//    println(numbers.elementAt(3))
//    val numbersSortedSet = sortedSetOf("one","two","three","four")
//    println(numbersSortedSet.elementAt(0))//元素以升序存储
//
//    println(numbers.first())
//    println(numbers.last())

    /**
     * 当指定元素超出集合范围时，采用elementAtOrNull()返回null.
     * elelmentAtOrElse() 当越界时，返回对给定值调用的lambda表达式的结果
     * */
//    val numbers = listOf("one","two","three","four","five","six")
//    println(numbers.elementAtOrNull(5))
//    println(numbers.elementAtOrElse(7){index->"the value for index $index is undeifined"})
//    /** 按条件取 */
//    println(numbers.first{it.length>3})
//    println(numbers.last{it.startsWith("f")})
//
//    /** 如果没有与谓词匹配的，会抛出异常，为了避免它们，改用firstOrNull  lastOrNull.*/
//    println(numbers.firstOrNull{ it.length >6 })

    /** 也可以使用find() 或 findLast 代替 firstOrNull  lastOrNull */
//    val numbers = listOf(1,2,3,4)
//    println(numbers.find{it%2==0})
//    println(numbers.findLast { it%2==0 })

    /** 随机取元素   random()*/

    /** 检查存在与否 */
    val numbers = listOf("one","two","three","four","five","six")
    println(numbers.contains("four"))
    println("zego" in numbers)
    println(numbers.containsAll(listOf("four","two")))
    println(numbers.containsAll(listOf("one","zero")))
    println(numbers.isEmpty())
    println(numbers.isNotEmpty())

    val empty = emptyList<String>()
    println(empty.isEmpty())
    println(empty.isNotEmpty())


}