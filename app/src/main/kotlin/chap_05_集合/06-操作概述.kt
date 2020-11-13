package chap_05_集合

/**
 * Created by wangjiao on 2020/8/5.
 * description:
 */
fun main(){
//    val numbers = listOf("one","two","three","four")
//    numbers.filter { it.length>3 }
//    println("numbers are still:$numbers")
//    val longerThan3 =numbers.filter { it.length>3 }
//    println("longthan3:$longerThan3")

    /** 含有目标对象 .*/
    val numbers = listOf("one","two","three","four")
    val filterResult = mutableListOf<String>()
    numbers.filterTo(filterResult){it.length>3}
    numbers.filterIndexedTo(filterResult){index,_ -> index==0 }
    println(filterResult)//包含两个操作的结果


//    val numbers = mutableListOf<String>("one","two","four","three")
//    val sortedNumbers =numbers.sorted()//创建新集合
//    println(numbers==sortedNumbers)//false
//    numbers.sort()
//    println(numbers==sortedNumbers)//true
}