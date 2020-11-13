package chap_05_集合

/**
 * Created by wangjiao on 2020/8/5.
 * description:
 */

/**
 * 映射转换 ：从另一个集合的元素结果创建一个集合。基本的映射函数是map()。
 * */
fun main(){
//    val numbers =setOf(1,2,3)
//    println(numbers.map{it*4})
//    println(numbers.mapIndexed { index, value -> index*value })

    /** 过滤null值*/
//    println(numbers.mapNotNull {if(it==2)null else it*4 })
//    println(numbers.mapIndexedNotNull{index, i -> if(index==1)null else i*index })
//
//    val numbersMap = mapOf("key1" to 1,"key2" to 2,"key3" to 3)
//    println(numbersMap.mapKeys { it.key.toUpperCase() })
//    println(numbersMap.mapValues { it.value+it.key.length })


    /**双路合并 zip*/
//    val colors =listOf("red","green","blue")
//    val animals = listOf("fox","bear","wolf")
//    println(colors zip animals)
//
//    val twoAnimals = listOf("fox","bear")
//    println(colors.zip(twoAnimals))
//
//    println(colors.zip(animals){color,animal->"the ${animal.capitalize()}is $color"})

    /** 当拥有pair的list时，可以进行反向转换unzipping---从这些键值中构建两个列表*/
//    val numberPairs = listOf("one" to 1,"two" to 2,"three" to 3,"four" to 4)
//    println(numberPairs.unzip())

    /** 关联转换，允许从集合元素和其关联的某些值构建map,在不同的关联类型中，元素可以是关联map中的键或值
     * associateWith 集合元素作为键
     * associateBy 集合元素作为值
     *
     * 太优秀了，可以构造任意想要的集合
     * */
//    val numbers = listOf("one","two","three","four")
//    println(numbers.associateWith { it.length })
//    println(numbers.associateBy { it.first().toUpperCase() })
//    println(numbers.associateBy(keySelector = {it.first().toUpperCase()},valueTransform = {it.length}))


    /** associate()会生成临时的pair对象。这可能影响性能*/
//    val names = listOf("Alice Adams","Brian Brown","Clara Campbell")
//
//    /** 打平 将嵌套集合组合成一个集合*/
//    val numbersSets = listOf(setOf(1,2,3),setOf(4,5,6),setOf(1,2))
//    println(numbersSets.flatten())
//
//    val containers = listOf(
//        listOf("one","two","three"),
//        listOf("fout","five","six"),
//        listOf("seven","eight")
//    )
//    println(containers.flatMap{it})
//

    /** 将集合转成字符串
     * joinToString() 根据提供的参数从集合元素构建单个String
     * joinTo() 执行相同的操作，但将结果附加到给定的appendable对象
     *
     * */
//
//    val numbers =listOf("one","two","three","four")
//    println(numbers)
//    println(numbers.joinToString())
//
//    val listString = StringBuffer("the list of numbers:")
//    numbers.joinTo(listString)
//    println(listString)

    /** 自定义字符串*/
//    val numbers =listOf("one","two","three","four")
//    println(numbers.joinToString(separator = " | ",prefix = "statr:",postfix = ":end"))

    /** 对于较大的集合，需要指定limit*/
//    val numbers =(1..100).toList()
//    println(numbers.joinToString(limit=10,truncated = "..."))

    /** 自定义元素本身的表示形式，transform函数*/
//    val numbers =listOf("one","two","three","four")
//    println(numbers.joinToString { "element:${it.toUpperCase()}" })

}
