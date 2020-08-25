package chap_05_集合

import kotlin.math.sign

/**
 * Created by wangjiao on 2020/8/14.
 * description:
 */
fun main(){
    /** list按索引取值，防止过界，可以用getOrElse或getOrNull。这也是优于java的。更加灵活*/
//    val num = listOf(1,2,3,4)
//    println(num.get(0))
//    println(num[0])
//    println(num.getOrNull(5))
//    println(num.getOrElse(5,{it}))

    /** 取列表的一部分,厉害，如同java中的string*/
//    val numbers = (0..13).toList()
//    println(numbers.subList(3,6))

    /** 线性查找*/
//    val num = listOf(1,2,3,4,2)
//    println(num.indexOf(2))
//    println(num.lastIndexOf(2))


    /** indexOfFirst() indexOfLast() 接受谓词*/
//    val num = listOf(1,2,3,4,2)
//    println(num.indexOfFirst { it>2 })
//    println(num.indexOfLast { it>3 }) //虽然从后开始索引，但是idx仍然是从左往右开始算的
//    println(num.indexOfLast { it%2==0 })

    /** 在有序列表中 二分查找 */
//    val num= mutableListOf<String>("one","two","three","four","five")
//    num.sort()
//    println(num)
//    println(num.binarySearch("two"))
//    println(num.binarySearch("z"))
//    println(num.binarySearch("two",0,2))

    /** comparator 二分搜索。如果列表不是comparable则应提供一个用于二分搜索的comparator。该列表必须根据此comparator以升续排序*/
//    val productlist = listOf(
//        Product("webStorm",49.0),
//        Product("APPCode",99.0),
//        Product("DotTrace",129.0),
//        Product("ReSharper",149.0)
//    )
////    println(productlist.binarySearch(Product("AppCode",99.0),compareBy<Product>{it.price}.thenBy { it.name }))
//    println(productlist.binarySearch { priceComparison(it,99.0) })

//    val colors = listOf("Blue","green","ORANGE","Red","yellow")
//    println(colors.binarySearch("RED",String.CASE_INSENSITIVE_ORDER))

    /** fill() 简单的将所有集合元素的值替换为指定值*/
//    val num = mutableListOf<Int>(1,2,3,4,2)
//    num.fill(3)
//    println(num)

    /** 排序 */
    val num = mutableListOf<String>("one","two","three","four","five")
    num.sort()
    println("Sort into ascending:$num")
    num.sortDescending()
    println("sort int descending:$num")

    num.sortBy { it.length }
    println("sort into ascending by lenght:$num")
    num.sortByDescending { it.last() }
    println("sort into descending by the last letter:$num")

    num.sortWith(compareBy<String>{it.length}.thenBy { it })
    println("sort by Comparator:$num")
    num.shuffle()
    println("shuffle:$num")

    num.reverse()
    println("reverse $num")


}

/** 比较函数 ，二分搜索*/
data class Product(val name:String,val price:Double)
fun priceComparison(product: Product,price:Double)= sign(product.price-price).toInt()
