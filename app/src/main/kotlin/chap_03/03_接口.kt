package chap_03

/**
 * Created by mumu on 2020/6/16.
 * 功能描述：
 */
/** 可以既包含抽象方法的声明 也包含实现 */
interface MyInterface{
    fun bar()
    fun foo(){
        println(prop)
    }

    /** 在接口中声明的属性要么是抽象的，要么提供访问器的实现，在接口中声明的属性不能有幕后字段,因此接口中声明的访问器不能引用它们*/
    val prop :Int
    val propertyWithImplementation:String
        get() ="foo"

}

class Child:MyInterface{
    override val prop:Int =4
    override fun bar() {

    }
}

/** 接口继承 */
interface Named{
    val name:String
}
interface My:Named{
    val firstName:String
    val lastNamee:String
    override val name:String get()="$firstName $lastNamee"
}

//class mmc(override val firstName: String, override val lastNamee: String) :My{
//
//}

class mmc:My{
    override val firstName: String
        get() = "m"
    override val lastNamee: String
        get() = "c"

}

fun main(){
//    var child = Child()
//    child.foo()
    var m= mmc()
    println(m.name)
}