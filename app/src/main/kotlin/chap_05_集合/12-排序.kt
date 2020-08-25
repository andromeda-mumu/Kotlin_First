package chap_05_集合

/**
 * Created by wangjiao on 2020/8/11.
 * description:
 */
//fun main(){
//    println(Version(1,2)>Version(1,3))
//    println(Version(2,0)>Version(1,5))
//}
//class Version(val major:Int,val minor:Int):Comparable<Version>{
//    override fun compareTo(other:Version):Int{
//        if(this.major!=other.major)
//            return this.major-other.major
//         else if (this.minor!=other.minor)
//            return this.minor-other.minor
//        else return 0
//    }
//}

fun main(){
//    val lengthComparator = Comparator{str1:String,str2:String-> str1.length-str2.length}
//    println(listOf("aaa","bb","c").sortedWith(lengthComparator))
//
//    /** 定义一个comparator的一种比较简短的方式是标准库中的compareBy()函数。*/
//    println(listOf("aaa","bb","c").sortedWith(compareBy { it.length }))
//
//    /** 自然顺序*/
//    val numbers = listOf("one","two","three","four")
//    println("Sorted ascending:${numbers.sorted()}")
//    println("sorted descending:${numbers.sortedDescending()}")
//
//    /**倒叙*/
//    println(numbers.reversed())
//    val reversedNumbers = numbers.asReversed()
//    println(reversedNumbers)
//    println(numbers)


    /** 随机顺序 */
    val numbers = listOf("one","two","three","four")
    println(numbers.shuffled())

}