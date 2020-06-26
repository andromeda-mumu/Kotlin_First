package chap_03

/**
 * Created by mumu on 2020/6/26.
 * 功能描述：
 */
 /** 类可以嵌套在其他类中*/
class Outer2{
     private val bar:Int =1
     class Nested{
         fun foo()=2
     }
 }
val demo = Outer2.Nested().foo()

/** 标记为inner的嵌套类能够放昂为其外部类的成员 ，内部类会带有一个对外部类的对象的引用*/
class Outer3{
    private val bar :Int =2
    inner class Inner2{
        fun foo() = bar
    }
}
val demo2 = Outer3().Inner2().foo()

/** 匿名内部类 使用对象表达式创建匿名内部类实例 */
//window.addMouseListener(object: MouseAdapter()){
//    override fun mouseClicked(e:MouseEvent){}
//    override fun mouseEntered(e:MouseEvent){}
//}

/** 在JVM平台，如果对象是函数式Java接口（具有单个抽象方法的java接口）的实例，可以使用带接口类型前缀的lambda表达式创建它*/
//val listener = ActionListener { println("clicked")}