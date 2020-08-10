package chat_05_集合

/**
 * Created by wangjiao on 2020/8/10.
 * description:
 */

fun main(){
    /** filter中的谓词只能检查元素的值 */
//    val numbers =listOf("one","two","three","four")
//    val longerThan3 = numbers.filter { it.length>3 }
//    println(longerThan3)
//
//    val numberMap=mapOf("key1" to 1,"key2" to 2,"key3" to 3,"key11" to 11)
//    val filteredMap = numberMap.filter { (key,value) ->key.endsWith("1") && value>18 }
//    println(filteredMap)
//
//    /** filterIndexed 可以检查元素的位置
//     * 用否定条件来过滤集合，使用filterNot() ，它返回一个让谓词产生false的元素列表
//     * */

//    val numbers =listOf("one","two","three","four")
//    val filterdIdx = numbers.filterIndexed{index,s ->(index!=0)&&(s.length<5)}
//    val fitlerdNot = numbers.filterNot { it.length<=3 }
//    println(filterdIdx)
//    println(fitlerdNot)


    /** 缩小元素类型*/
//    val numbers = listOf(null,1,"two",3.0,"four")
//    println("all String elements in upper case:")
//    numbers.filterIsInstance<String>().forEach { println(it.toUpperCase()) }

    /** 返回所有非空元素*/
//    val numbers = listOf(null,"two","four")
//    numbers.filterNotNull().forEach { println(it.length) }

    /** 划分
     * partition()将不匹配的元素放在一个单独的列表中，因此，你得到一个list的pair作为返回值：匹配的集合，剩余集合
     *
     * */
//    val numbers = listOf("one","two","three","four")
//    val (match,rest)=numbers.partition { it.length>3 }
//    println(match)
//    println(rest)
    /** 检验谓词 ，对集合元素进行简单的检测
     * -如果至少有一个元素匹配给定谓词，那么any()返回true
     * -如果没有元素与给定为此匹配，那么none()返回true
     * -所有元素都匹配自定谓词，那么all()返回true.注意：在一个空集合上，使用任何有效的谓词去调用all()都会返回true.
     * */
//    val numbers = listOf("one","two","three","four")
//    println(numbers.any{it.endsWith("e")})
//    println(numbers.none{it.endsWith("a")})
//    println(numbers.all { it.endsWith("e") })
//
//    println(emptyList<Int>().all{it>5})

    /** any 和none也可以不带谓词使用：在这种情况下，它们只是来检查集合是否为空*/
    val numbers = listOf("one","two","three","four")
    val empty = emptyList<String>()

    println(numbers.any())
    println(empty.any())

    println(numbers.none())
    println(empty.none())

}