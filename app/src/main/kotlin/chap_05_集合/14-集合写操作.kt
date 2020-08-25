package chap_05_集合

/**
 * Created by wangjiao on 2020/8/14.
 * description:
 */
fun main(){
    /** 可变集合支持更改集合内容的操作。列入添加或删除元素。*/
//    val numbers = mutableListOf(1,2,3,4)
//    numbers.add(5)
//    println(numbers)
//
//    numbers.addAll(arrayOf(7,8))
//    println(numbers)
//    numbers.addAll(2, setOf(3,4))
//    println(numbers)

    /** 比起java,kotlin的集合可以想java中的基础类型一样，进行加减操作。方便很多*/
//    val numbers = mutableListOf<String>("one","two")
//    numbers+="three"
//    println(numbers)
//    numbers+= listOf<String>("four","five")
//    println(numbers)

//     val numbers = mutableListOf<Int>(1,2,3,4,5)
//     numbers.remove(3)
//    println(numbers)
//    numbers.remove(6)
//    println(numbers)

    /** 删除多个元素
     * removeAll()删除所有，或者移除谓词为true的元素
     * retainAll()与上一个相反，与谓词一起使用，只保留与谓词匹配的元素
     * clear()置空
     * */
//    val num = mutableListOf<Int>(1,2,3,4)
//    println(num)
//    num.removeAll { it>=3 }
//    println(num)
//    num.clear()
//    println(num)
//
//    val num2 = mutableListOf<String>("one","two","four","five")
//    num2.removeAll(setOf("one","two"))
//    println(num2)


    /** minusAssign（-=）.又能直接减，也能减集合。单个元素，多个元素都有办法。好用*/
     val num = mutableListOf<String>("one","two","three","four","five")
    num-="three"
    println(num)
    num-=listOf("four","five")
    println(num)

}
