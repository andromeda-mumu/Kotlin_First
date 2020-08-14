package chap_05_集合

import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Created by wangjiao on 2020/8/14.
 * description:
 */
@RequiresApi(Build.VERSION_CODES.N)
fun main(){
    /** 取键和值*/
//    val num = mapOf("one" to 1 ,"two" to 2,"three" to 3)
//    println(num.get("one"))
//    println(num["one"])
//    println(num.getOrDefault("four",10))
//    println(num["five"])
//
//    println(num.keys)
//    println(num.values)

    /** 过滤 */
//    val num = mapOf("key1" to 1 ,"key2" to 2,"key3" to 3,"key11" to 11)
//    val filter = num.filter { (key,value) -> key.endsWith("1") && value>10 }
//    println(filter)
//
//    val filterKeyMap = num.filterKeys { it.endsWith("1") }
//    val filterValueMap = num.filterValues { it<10 }
//    println(filterKeyMap)
//    println(filterValueMap)

    /** plus 与minus */
//    val num = mapOf("one" to 1,"two" to 2,"three" to 3)
//    println(num+Pair("four",4))
//    println(num+Pair("one",10))
//    println(num+ mapOf("five" to 5,"one" to 11))
//
//    println(num-"one")
//    println(num- listOf("two","four"))

    /** map写操作。键不变，值可以更新。*/
    val num = mutableMapOf("one" to 1,"two" to 2,"three" to 3)
//    num.put("five",5)
//    println(num)
//    num.put("one",11)
//    println(num)
//    num.putAll(setOf("four" to 4,"six" to 6,"two" to 20))
//    println(num)
//     num["three"]=30
//    num+= mapOf("four" to 4,"ten" to 10)
//    println(num)
//
//    num.remove("one")
//    println(num)
//    num.remove("three",4)
//    println(num)

//        num.keys.remove("one")
//        println(num)
//        num.values.remove(3)
//        println(num)
//        num.keys.remove("four")
//        println(num)

        num-="two"
    println(num)
    num-="five"
    println(num)
}