package chap_01

/**
 * Created by mumu on 2020/6/8.
 * 功能描述：
 */
fun main(){
    val items = listOf("a","b","d")
    var index =0
    while (index<items.size){
        println("item at $index is ${items[index]}")
        index++
    }
}