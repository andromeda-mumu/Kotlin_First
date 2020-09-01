package chap_09_java互操作

import chap_03.Derived
import java.io.IOException

/**
 * Created by wangjiao on 2020/8/31.
 * description:
 */

/** 多个文件使用相同 java类名，需要使用@JvmMultifileClass注解*/
////oldutils.kt
//@file:JvmName("Utils")
//@file:JvmMultifileClass
//
//package org.example
//fun getTime(){}
//
////newutils.kt
//@file.JvmName("Utils")
//@file:JvmMultifileClass
//package org.example
//fun getDate(){}
//
////java
//org.example.Utils.getTime()
//org.example.Utils.getDate()

/**实例字段
 * 当需要在java中将kotlin属性作为字段暴露，则需要使用@JvmField注解。
 * 如果一个属性有幕后字段，非私有，没有Open/override/const修饰，不是被委托的属性。则可以使用@JvmField
 * */
//class User(id:String){
//    @JvmField val ID=id
//}
////java
//class JavaClient{
//    public String getID(User user){
//        return user.ID;
//    }
//}

/**静态字段
 *@JvmStatic
 * */

/** 接口中的默认方法*/



/** 用@JvmName解决签名冲突
 * 有时我们想让一个kotlin中的具名函数在字节码中有另一个JVM名称。最突出的例子是类型擦除引发的
 * */
//fun list<String>.filterValid():List<String>
//fun List<Int>.filterValid():List<Int>
/** 这两个函数不能同时定义，因为他们JVM签名是一样的。如果真希望他们在kotlin中用相同名称，我们需要用@JvmName去标注其中一个或两个，并
 * 指定不同名称作为参数
 * */
//fun list<String>.filterValid():List<String>
//
//@JvmName("filterValidInt")
//fun List<Int>.filterValid():List<Int>
/** 在kotlin中可以用相同的名称filterValid来访问，而在java中，它们分别是filterVaild 和 filterValidInt */

/** 同样的技巧也适用于属性X和函数getX()共存*/
//val x:Int
//    @JvmName("getC_prop")
//    get()=15
//
//fun getX()=10

/** 如需在没有显示实现getter与setter的情况下更改属性生成的访问器方法的名称，也可以使用@get:JvmName 与 @set:JvmName */
@get:JvmName("x")
@set:JvmName("changeX")
var x:Int =23



/** 生成重载
 * @JvmOverloads
 * 适用于 构造函数，静态函数等
 * */
class Circle @JvmOverloads constructor(centerX:Int,centerY:Int,radius:Double=1.0){
    @JvmOverloads fun draw(label:String,lineWidth:Int=1,color:String="red"){}
}
/**对于每一个有默认值的参数，都会生成一个额外的重载，这个重载会把这个参数和它左右的所有参数都移除掉。在上例中，会生成以下代码*/
//构造函数
//Circle(int centerX,int centerY,double radius)
//Circlr(int centerX,int centerY)
//
////方法
//void draw(String label,int lingWidth,String color){}
//void draw(String label,int lineWidth){}
//void draw(String label){}


/** 受检异常
 * kotlin没有受检异常，所以，kotlin函数的java签名不会声明抛出异常，假设我们有一个这样的函数
 * */
//fun writeToFile(){
//    throw IOException()
//}
//想在java中调用它并捕获这个异常
//java
//try{
//    writeToFile();
//}catch(IOException e){ //错误，writeToFile()未在throws列表中声明IOException
//
//}
/** 为了解决这个问题，要在kotlin中使用@Throws注解*/
@Throws(IOException::class)
fun writeToFile(){
    throw IOException()
}



/** 通配符  @JvmWildcard  和 @JvmSuppressWildcards */
//fun boxDerived(value:Derived):Box<@JvmWildcard Derived> = Box(value)
//将被转换成
//Box<? extends Derived> boxDerived(Derived value){.....}

//fun unboxBase(box:Box<@JvmSuppressWildcards> Base>):Base = box.value
//会翻译成
//Base unboxBase(Box<Base> box){....}


/** nothing类型*/
fun emptyList():List<Nothing> = listOf()
//会被翻译成
//List emptyList(){}




