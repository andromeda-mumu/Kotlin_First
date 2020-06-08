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

    /* 使用in 判断集合内是否包含某个实例*/
    when{
        "v" in items -> println("V")
        "c" in items -> println("c")
    }
    println()
    /* 使用lambda表达式过滤与映射集合*/
    val fruits = listOf("anana","orange","apple","pair")
    fruits.filter{it.startsWith("a")}
        .sortedBy { it }
        .map{it.toUpperCase()}
        .forEach{ println(it)}
}

