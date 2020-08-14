package chap_05_集合

/**
 * Created by wangjiao on 2020/7/31.
 * description:
 */

fun printAll(strings:Collection<String>){
    for (s in strings ) print("$s")
    println()
}

//fun main(){
//    val stringList = listOf("one","two","two")
//    printAll(stringList)
//
//    val stringSet= setOf("one","two","two")
//    printAll(stringSet)
//}


fun List<String>.getShortWordsTo(shortWords:MutableList<String>,maxLength:Int){
    this.filterTo(shortWords){
        it.length<=maxLength
    }
    val articals = setOf("a","A","bb","dd","the","ane")
    shortWords -=articals
}
//fun main(){
//    val words = "A LONG time ago in a galaxy far far away".split(" ")
//    val shortWords = mutableListOf<String>()
//    words.getShortWordsTo(shortWords,3)
//    println(shortWords)
//}

