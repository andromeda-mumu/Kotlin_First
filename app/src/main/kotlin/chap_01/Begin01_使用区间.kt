package chap_01

/**
 * Created by mumu on 2020/6/8.
 * 功能描述：
 */
fun main(){
//    val x= 10;
//    val y =9
//    /** 使用in某数字是否在指定区间内*/
//    if( x in 1..y+1){
//        println("fits in range")
//    }
//
//    /*是否在区间外*/
//    val list = listOf("a","b","c")
//    if(-1 !in 0..list.lastIndex){
//        println("-1 is out of range")
//    }
//    if(list.size !in list.indices){
//        println("list size is out of valid list indices range ,too")
//    }

    /*区间迭代*/
    for(x in 1..5){
        print(x)
    }
    println()
    /*数列迭代*/
    for(x in 1..10 step 2){
        print(x)
    }
    println()
    for(x in 9 downTo 0 step 3){
        print(x)
    }
}
