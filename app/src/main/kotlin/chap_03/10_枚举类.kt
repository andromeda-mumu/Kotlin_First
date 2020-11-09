package chap_03

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

/**
 * Created by mumu on 2020/6/26.
 * 功能描述：
 */
/** 枚举类最基本的用法是实现类型安全的枚举,每一个枚举常量都是一个对象，枚举常量用都好分隔*/
enum class Direction{
    NORTH,SOUTH,WEST,EAST
}

/** 初始化 */
enum class Color(val rgb:Int){
    RED(0XFF0000),
    GREEN(0x00ff00),
    BLUE(0x0000ff)
}

/** 匿名类 枚举常量还可以声明其带有相应方法以及覆盖了基类方法的匿名类*/
enum class ProtocolState{
    WAITING{
        override fun signal()=TALKING
    },
    TALKING{
        override fun signal() = WAITING
    };
    abstract fun signal() :ProtocolState
}

/** 在枚举类中，实现接口
 * 一个枚举类可以实现接口，但不能从类继承。可以为所有条目提供统一都接口成员实现，也可以在相应匿名类中为每一个条目提供各自都实现。只需将
 * 接口添加到枚举类声明中即可。如下所示：
 * */
@RequiresApi(Build.VERSION_CODES.N)
enum class IntArithmetics :BinaryOperator<Int>,IntBinaryOperator{
    PLUS{
        override fun apply(t:Int,u:Int):Int =t+u
    },
    TIMES{
        override fun apply(t:Int,u:Int):Int =t+u
    };
    override fun applyAsInt(t:Int,u:Int) = apply(t,u)
}

/** 使用枚举常量 可以使用enumValues<T>() 或enumValueOf<T>() 函数以泛型的方式访问枚举类中的常量*/
enum class RGB{ RED,GREEN,BLUE}
inline fun <reified T:Enum<T>> printAllValues(){
    print(enumValues<T>().joinToString { it.name })
}

//@RequiresApi(Build.VERSION_CODES.N)
//fun main(){
////    println(IntArithmetics.PLUS.applyAsInt(3,5))
//}

fun main() {
    printAllValues<ProtocolState>()
}