package chap_05_集合

/**
 * Created by wangjiao on 2020/8/3.
 * description:
 */
fun main(){
//    val numbers = listOf("one","two","three","four")
//    val numbersIterator = numbers.iterator()
//    while (numbersIterator.hasNext()){
//        println(numbersIterator.next())
//    }

//    for(item in numbers)
//        println(item)

//    numbers.forEach{
//        println(it)
//    }

//    val listIterator = numbers.listIterator()
//    while (listIterator.hasNext()) listIterator.next()
//    println("iteating backwards:")
//    while (listIterator.hasPrevious()){
//        println("index:${listIterator.previousIndex()}")
//        print(",value:${listIterator.previous()}")
//    }


//    val numbers = mutableListOf("one","two","three","four")
//    val mutableIterable = numbers.iterator()
//    mutableIterable.next()
//    mutableIterable.remove()
//    println("after move:$numbers")

    val numbers = mutableListOf<String>("one","four","four")
    val mutableListIterator = numbers.listIterator()
    print(numbers)

    mutableListIterator.next()
    mutableListIterator.add("two")
    mutableListIterator.next()
    mutableListIterator.set("three")
    println(numbers)

}