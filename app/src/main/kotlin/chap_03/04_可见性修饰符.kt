package chap_03

/**
 * Created by mumu on 2020/6/20.
 * 功能描述：
 */

/** 在kotlin中，有四种可见性修饰符 private protected internal public .如果没有指定，则默认可见性是public */

/** 包 */
private fun foo(){}//只在当前文件可见
public var bar:Int =5 //随处可见
    private set     //setter只在当前文件可见

internal val baz = 6 //相同模块内可见

/** 类与接口 */
open class Outer{
    private val a=1
    protected  open val b=2
    internal val c =3
    val d = 4
    protected class Nested{
        public val e:Int = 5
    }
}
class Subclass :Outer(){
    //a不可见
    //b c d 可见
    //Nested  e 可见
    override val b = 5 //b 还是protected
}
class Unrelated(o:Outer){

    //o.a o.b 不可见
    //o.c o.d 可见
    //outter.Nested Nested::e 都不可见
}

/** 构造函数*/
class CC private constructor(a:Int){}

/** 局部变量，函数 类 不能有可见性修饰符 */

/** 模块,一个模块是编译在一起的一套kotlin文件
 *  一个IntelliJ IDEA 模块
 *  一个Maven项目
 *  一个Gradle原集（例外的是 test源集可以访问main的internal声明）
 *  一次<kotlinc> Ant任务执行所变化一的一套文件
 * */


