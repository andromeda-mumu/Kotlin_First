package chap_01

/**
 * Created by mumu on 2020/6/8.
 * 功能描述：
 */
fun main(){
  val items = listOf("a","b","c")
    for(item in items){
        println(item)
    }

    /* 这种方式可以获得下标*/
    for(index in items.indices){
        println("item is $index is ${items[index]}")
    }
}