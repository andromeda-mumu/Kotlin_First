package chap_03

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Created by mumu on 2020/6/16.
 * 功能描述：
 */
/** 构造函数 */
class Person constructor(name:String){}

/** 主构造函数没有任何注解或者可见性修饰符，可省略constructor关键字 */
class Person2(name:String){}

/** 主构造函数不能包含任何代码，初始化的代码可以放在init关键字作为前缀的初始化块中*/
class Student(name:String){
    val firstProperty = "first property:$name".also(::println)
    init{
        println("first initializzer block that prints ${name}")
    }
    val secondProperty = "Second property:${name.length}".also(::println)

    init{
        println("second initializer block that prints ${name.length}")
    }
}

/** 如果构造函数有注解 或可见性修饰符，constructor关键字是必须的.并且修饰符唉前面*/
class Customer public  constructor(name:String){}

/** 次构造函数*/
class Person3{
    var children:MutableList<Person3> = mutableListOf()
    constructor(parent:Person3){
        parent.children.add(this)
    }
}

/** 委托给主构造函数或其他构造函数,用this*/
class B (name:String){
    var children :MutableList<B> = mutableListOf()
    constructor(name:String,parent:B) :this(name){
        parent.children.add(this)
    }
}

/** 如果主构造函数所有参数有默认值，JVM会生成一个额外的无参构造函数*/
class C(val name:String =""){

}
/** 类默认是final的，不能被继承，如果想要被继承，需要使用open*/
open class Base(p:Int){}
/** 申明显示的超类*/
class Derived(p:Int):Base(p)

/** 如果派生类有主构造函数，基类必须用派生类主构造函数的参数就地初始化*/
/** 如果派生类没有主构造函数，那么每个次构造函数必须使用super关键字初始化其基类型，或委托另一个构造函数做到这一点*/
class MyView: View {
    constructor(ctx: Context):super(ctx)
    constructor(ctx:Context,attrs:AttributeSet):super(ctx,attrs)
}

/** 覆盖
 * 基类必须是open声明的，且方法必须是open声明，覆盖的方法也必须显示写override
 * */
open class shape{
    open fun draw(){}
    fun fill(){}
}
class Circle:shape(){
    override fun draw(){}
}
/** 如果不想被子类覆盖，则加上final关键字*/
open class Rect:shape(){
    final override fun draw(){}
}
/** var属性覆盖一个val属性，反之不行。因为一个val本质是声明一个get，而将其覆盖成var只是在子类中额外声明一个set方法*/
/** 注意：可以在主构造函数中使用override关键字作为属性声明的一部分*/
interface Shapee{
    val count:Int
}
class rect(override val count:Int =4):Shapee
class Polygon:Shapee{
    override var count:Int =4  //以后可以设置成任意数
}
/** 派生类初始化顺序  设计基类时，避免在构造函数，属性初始化器，init块中使用open成员*/
open class AA(val name:String){
    init{
        println("init AA ")
    }
    open val size:Int =
        name.length.also(::println)
}
class BB(
    name:String,
    val lastName:String
):AA(name.capitalize().also{ println("init size in AA $it")}){
    init{println("init BB")}
    override val size:Int=
        (super.size+lastName.length).also { print("init size in BB $it") }
}

/** 调用超类实现 派生类的代码可以使用super关键字调用起超类的函数与属性访问器的实现*/
open class RectB{
    open fun draw(){
        println("drawing a rect")
    }
    val borderColor:String get()="black"
}
class FilledRect:RectB(){
    override fun draw(){
        super.draw()
        println("filling the rect")
        var filler = Filler()
        filler.drawAndFill()
    }
    val ficcColor:String get()=super.borderColor

    /** 内部类中访问外部类的超类，可以通过由外部类名限定super关键字来实现 super@Outer*/
    inner class Filler{
        fun fill(){}
        fun drawAndFill(){
            super@FilledRect.draw()  //调用rect的draw
            fill()
            println("drawn a filled rect with color ${super@FilledRect.borderColor}")
        }
    }

}

/** 覆盖规则 ，为了表示从哪个超类继承的实现，使用 super<Base>限定*/
open class DD{
    open fun play(){}
}
interface Poly{
    fun play(){}
}
class Square:DD(),Poly{
    override fun play() {
        super<DD>.play();//调用DD的paly
        super<Poly>.play() //调用poly的play
    }
}

/** 抽象类 不需要用open标注抽象类*/
open class EE{
    open fun draw(){}
}
abstract class FF:EE(){
    abstract override fun draw()
}

fun main(){
//    val student = Student("mmc")
//    var c = C()

//    var B = BB("hello","world")
    var r = FilledRect()
    r.draw()
    println(r.ficcColor)





}