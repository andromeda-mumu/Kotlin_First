package chap_05_集合

/**
 * Created by wangjiao on 2020/8/5.
 * description:
 */

/**
 * iterable 优先执行每个处理步骤，并返回结果（中间集合）,在此集合上执行以下步骤
 * 序列是仅当请求整个处理链的结果时才进行实际计算。
 *
 *操作执行顺序不同：sequence 对每个元素逐个执行所有处理步骤，iterable完成整个集合的每个步骤，然后进行下一步
 *
 * 序列生成中间步骤的结果，提高整个集合处理链的性能
 * 但是序列的延迟性质增加了开销。
 *
 *
 *
 * */

fun main(){
    /** 构造*/
//    val numberSequence = sequenceOf("foir","fhree","two","one")
//    /** 可以通过iterable构造*/
//    val numbers= listOf<String>("one","two","three","four")
//    val numbersSequence = numbers.asSequence()
//    /** 由函数*/
//    val oddumbers = generateSequence(1){it+2}
//    println(oddumbers.take(5).toList())
////    println(oddumbers.count()) 错误 此序列是无限的
//
//    /** 有限序列*/
//    val oddNumbersLessThan0 = generateSequence(1){if(it<10) it+2 else null}
//    println(oddNumbersLessThan0.count())

    /** 由组块 */
//    val oddNumbers = sequence{
//        yield(1)
//        yieldAll(listOf(2,5))
//        yieldAll(generateSequence(7){it+2})
//    }
//    println(oddNumbers.take(10).toList())

//    val words = "The quick brown fox jumps over the lazy dog".split(" ")
//    val lengthList = words.filter { println("filter:$it");it.length>3 }
//        .map { println("length:${it.length}");it.length }
//        .take(4)
//    println("result:$lengthList")

    val words ="The quick brown fox jumps over the lazy dog".split(" ")
    val wordsSequence = words.asSequence()

    val lengthSequence = wordsSequence.filter { println("filter:$it");it.length>3 }
        .map { println("lenght:$it.length");it.length }
        .take(4)

    println("result:${lengthSequence.toList()}")

    /** 由上面两个列子看出，集合是整个函数执行完，才执行下一个函数。而序列是一个元素执行完所有的函数，再让下一个元素执行所有函数，因为只take(4)，所以不执行之后的元素*/


}