package chap_08_更多语言结构

import java.util.*

/**
 * Created by wangjiao on 2020/8/31.
 * description:
 */
/**
 * kotlin标准库提供了一种机制，用于要求并明确同意使用API的某些元素。通过这种机制，库开发人员可以将使用其API需要选择加入的特定条件告知用户，
 * 如，某API处于实验状态，将来可能会更改
 *
 * 为了避免潜在问题，编译器会向此类API的用户发出警告，告知它们这些条件，并要求它们在使用API之前选择加入
 *
 *
 *
 * */

/** 传播选择加入
 * 在使用供第三方库使用的API时，在API主体中声明中添加注解
 * */
//库代码
//@RequiresOptIn(message ="This api is experimental ,It may be changed in the future without not")
//@Retention(AnnotationRetention.BINARY)
//@Target(AnnotationTarget.ANNOTATION_CLASS,AnnotationTarget.FUNCTION)
//annotation class MyDateTime  //要求选择加入的注解
//
//@MyDateTime
//class DateProvider // 要求选择加入的类
//
////客户端代码
//
////fun getYear():Int{
////    val dateProvider:DateProvider //:错误，DateProvider要求选择加入
////}
//
//@MyDateTime
//fun getDate():Int{
//    val dateProvider:DateProvider // ok:该函数需要选择加入
//}
//
//fun  displayDate(){
//    println(getDate())
//}

/** 非传播的用法
 * 在不公开其自身API的模块中，你可以选择使用API而无需将选择加入的要求传播到代码中。这样的情况下，使用@OptIn标记你的声明，并以要求选择加入的注解作为参数
 * */

//库代码
//@RequiresOptIn(message ="This api is experimental ,It may be changed in the future without not")
//@Retention(AnnotationRetention.BINARY)
//@Target(AnnotationTarget.ANNOTATION_CLASS,AnnotationTarget.FUNCTION)
//annotation class MyDateTime  //要求选择加入的注解
//
//@MyDateTime
//class DateProvider // 要求选择加入的类
//
////客户端代码
//@OptIn(MyDateTime::class)
//fun getDate(): Date {
//    val dateProvider:DateProvider // 使用dateProvider不传播选择加入的要求
//}
//fun displayDate(){
//    println(getDate())
//}
////当有人调用函数getDate()时，不会通知他们的函数主体中使用的选择加入API的要求
///** 要在一个文件的所有函数和类中使用要求选择加入的API，请在文件的顶部，文件包说明和导入声明前添加文件级注解@file:OptIn*/

/**模块范文的选择加入
 * 使用-Xopt-in 进行编译，使用-Xopt-in = org.mylibrary.OptInAnnotation 指定该API使用的要求选择加入注解的标准名称。
 * 在gradle中进行添加
 * */

/**
 * 要求选择加入的注解
 * 如果想获得使用者使用你的模块API的明确泳衣，请创建一个注解类，作为要求选择加入的注解，这个类必须使用@RequiresOptIn注解
 * */
@RequiresOptIn
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION)
annotation class MyDateTime
/**
 *要求加入的注解必须满足一下几个要求
 * - BINARY retention
 * - targets中没有EXPRESSION 与 FILE
 * - 没有参数
 *
 * 选择加入的要求可以具有以下两个严格级别之一：
 * - RequiresOptIn.level.ERROR  选择加入是强制性的，否则，使用标记API的代码将无法编译，默认级别
 * - RequiresOptIn.level.WARNING  不是强制性的，而是建议使用的。
 *
 * 要设置所需的级别，请指定 @RequiresOptIn 注解的level参数
 * 另外，你可以提供一个message来通知用户有关使用该API的特定条件。编译器会将其显示给该API但为选择加入的用户
 * */
@RequiresOptIn(level = RequiresOptIn.Level.WARNING,message = "this api is experiment")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION)
annotation class ExperimentalDateTime

/** 标记API元素,要在使用API时选择加入，则给他的声明添加要求选择加入的注解*/
//@MyDateTime
//class DateProvider
//
//@MyDateTime
//fun getTime():Time{}

/**
 *
 * */
































