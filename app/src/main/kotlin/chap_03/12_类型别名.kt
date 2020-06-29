package chap_03

/**
 * Created by mumu on 2020/6/27.
 * 功能描述：
 */
/** 类型别名为现有类型提供替代名称。有助于缩短较长的泛型类型*/
//typealias NodeSet = Set<Network.Node>
//typealias FileTable<K> = MutableMap<K,MutableList<File>>
//
///** 为函数别名提供另外的别名*/
//typealias  MyHandler = (Int,String,Any) ->Unit
//typealias Predicate<T> = (T) -> Boolean
//
///** 可以为内部类 和 嵌套类创建新名称 */
//class A{
//    inner class Inner
//}
//class B{
//    inner class Inner
//}
//typealias AInner = A.Inner
//typealias BInner = B.Inner

/** 类型别名不会引入新类型 */
typealias  Predicate<T> = (T) ->Boolean
fun foo(p:Predicate<Int>) = p(40)
fun main(){
    val f:(Int)->Boolean = {it > 0}
    print(foo(f))
    val p:Predicate<Int> ={it>0}
    println(listOf(1,-2).filter(p))
}
