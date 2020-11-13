package chap_05_集合

/**
 * Created by wangjiao on 2020/7/31.
 * description:
 */
fun main(){
//    val numbersMap = mapOf("key1" to 1 ,"key2" to 2) //性能不好
//    val numbersMap2 = mutableMapOf<String,String>().apply{this["one"] ="1";this["two"]="2"}
//
//    /** 空集合*/
//    val empty = emptyList<String>()
//
//    /** list的初始化函数*/
//    val doubled  = List(3,{it*2}) //0，2，4
//    println(doubled)

//    /** 具体类型构造函数*/
//    val linkedlist = LinkedList<String>(listOf("one","two","three"))
//    val presizedSet = HashSet<Int>(32)
//
//    /** 复制*/
//    val source = mutableListOf<Int>(1,2,3)
//    val copy = source.toMutableList()
//    val readOnly = source.toList()
//    source.add(4)
//    println("-------------")
//    println(source.size)
//    println(copy.size)
//    println(readOnly.size)

    /** 将集合转换成其他类型 */
//    val sourceList = mutableListOf(1,2,3)
//    val copySet = sourceList.toMutableSet()
//    copySet.add(3)
//    copySet.add(4)
//    println(copySet)//[1，2，3，4]

    /** 集合初始化可以限制可变性 */
//    val sourceList = mutableListOf<Int>(1,2,3)
//    val referenceList:List<Int> = sourceList
////    referenceList.add()  编译不通过
//    sourceList.add(4)
//    println(referenceList)
//    println(referenceList)

    /** */
//    val numbers = listOf<String>("one","two","three")
//    val longerThans = numbers.filter { it.length>3 }
//    println(longerThans)

//    val numbers= setOf<Int>(1,2,3)
//    println(numbers.map { it*3 })
//    println(numbers.mapIndexed { index, i -> index*i })

      val numbers = listOf<String>("one","two","three")
      println(numbers.associateWith { it.length })
}
