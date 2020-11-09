package chap_03

import androidx.appcompat.app.ActionBarDrawerToggle
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Created by mumu on 2020/6/27.
 * 功能描述：
 */
/**
 * 有一些常见的属性类型，虽然每次可以在需要的时候手动实现它们，但如果能为大家把他们只实现一次并放入一个库会更好 ，如
 * - 延迟属性
 * - 可观察属性：监听器会收到有关此属性变更的通知
 * - 把多个属性储存在一个映射中，而不是每个存在单独的字段中
 *
 * 为了涵盖这些，kotlin支持委托属性
 * */
class Example2{
    var p :String by DelegateM()
}
/** 语法： by后面的表达式是委托。因为属性对应的get() 与set() 会被委托给它的getValue()与setVaule方法。属性的委托不必实现任何的接口
 * 但是需要提供一个getValue()函数
 * */

class DelegateM{
    operator fun getValue(thisRef:Any?,property : KProperty<*>):String{
        return "$thisRef ,thank you for delegating '${property.name}' to me "
    }

    operator fun setValue(thisRef: Any?,property: KProperty<*>,value:String){
        print("$value has been assigned to '${property.name}' in $thisRef .")
    }
}
/** 当从委托到一个Delegate实例的p读取时，将调用Delegate中的getValue()函数，所以它第一个参数是读出p的对象。第二个参数保存了对p自身的描述*/
//fun main(){
//    val e = Example2()
//    println(e.p)
//   e.p= "new"
//}


/** 延迟属性：lazy（）是接受一个lambda并返回一个Lazy<T>实例的函数，返回的实例可作为实现延迟属性的委托：第一次调用get()会执行已传递给
 * lazy()的lambda表达式并记录结果，后续调用get()只是返回记录的结果
 * */
val lazyValue :String by lazy{
    println("completed")
    "hello"
}

//fun main(){
//    println(lazyValue)
//    print(lazyValue)
//}
/** 默认情况下，对于lazy属性的求值是同步锁的：该值只在一个线程中计算，并且所有线程会看到相同的值。如果初始化委托的同步锁不是必须的，这样
 * 多个线程可以同时执行，那么将LazyThreadSafetyMode.PUBLICATION 作为参数传递给lazy()函数。如果你确定初始化将总是发生在于属性
 * 使用位于相同的线程，那么可以使用LazyThreadSafetyMode.NONE模式：它不会有任何线程安全的保证以及相关开销*/

/** 可观察属性 Observable
 * Delegates.observable()接受两个参数：初始值与修改时处理程序。每当给属性赋值时会调用处理程序。它有三个参数：被赋值属性，旧值，新值
 * */
class User5{
    var name:String by Delegates.observable("<no name>"){
        prop,old,new ->
        println("$old -> $new")
    }
}
//fun main(){
//    val user = User5()
//    user.name = "first"
//    user.name ="second"
//}

/** 把属性存储在映射中
 * 一个常见的用例是在一个映射里存储属性的值。像解析JSON 。在这种情况下，可以使用映射实例自身作为委托来实现委托属性
 * */
class User6(val map:Map<String,Any?>){
    val name:String by map
    val age:Int by map
}
//fun main(){
//    val user6 = User6(mapOf(
//        "name" to "Jhon",
//        "age" to 25
//    ))
//    println(user6.name)
//    println(user6.age)
//}

/** 局部委托属性 ，可以将局部变量声明为委托属性，如 ，可以使一个局部变量惰性初始化*/
fun exam(computeFoo:()->Foo){
    val memoizedDoo by lazy(computeFoo)
//    if(someCondition && memoizedDoo.isValid()){
//        memorizedFoo.doSomething()
//    }
}


/** 属性委托要求  对于一个只读属性，委托必须提供一个操作符函数getValue() ,该函数具有以下参数
 * - thisRef  必须与 属性所有者类型（对于扩展属性--指被扩展的类型）相同或者是超类型
 * - property  必须是类型 KProperty<*> 或超类型
 *
 * 对于一个可变属性，委托必须额外提供一个操作符函数setValue()，该函数具有以下参数：
 * -thisRef  必须与 属性所有者类型（对于扩展属性-指被扩展的类型）相同或者是其超类型
 * -property  必须是类型KProperty<*> 或其超类型
 * -value    必须与属性类型相同（或者是其超类型）
 * */

class Resource
class Owner{
    val valResource:Resource by ResourceDelegate()
}
class ResourceDelegate(private var resource:Resource = Resource()){
    operator fun getValue(thisRef: Owner,property:KProperty<*>):Resource{
        return Resource()
    }
    operator fun setValue(thisRef: Owner,property: KProperty<*>,value: Any?){
        if(value is Resource){
            resource = value
        }
    }
}
/**
 * getValue()或/与setValue() 函数可以通过委托类的成员函数提供或者由扩展函数提供。当你需要委托属性到原本未提供的这些函数的对象时后者
 * 会更便利。两函数都需要用Operator关键字来进行标记
 *
 * 委托类可以实现包含所需operator方法的ReadOnlyProperty 或 ReadWriteProperty 接口之一。这两接口是在Kotlin标准库中声明的：
 * */
interface ReadOnlyProperty<in R,out T>{
    operator  fun getValue(thisRef:R ,property:KProperty<*>):T
}
interface ReadWriteProperty<in R,T>{
    operator fun getValue(thisRef: R,property: KProperty<*>):T
    operator fun setValue(thisRef:R,property:KProperty<*>,value:T)
}

/**
 * 在每个委托属性的实现的背后，kotlin编译器都会生成辅助属性并委托给它。例如，对于属性prop,生成隐藏属性prop$delegate,而访问器的代码只是
 * 简单的委托给这个附加属性
 * */
//class C2{
//    var prop: Type by MyDelegate()
//}
////编译器生成的响应代码
//class C3{
//    private val prop$delegate = MyDelegate()
//    var prop:Type
//        get() =prop$delegate.getValue(this,this::prop)
//        set(value:Type) = prop$delegate.setValue(this,this:prop,value)
//}
/** kotlin编译器在参数中提供了关于prop的所有必要信息：第一个参数this引用到外部类C的实例而this::prop 是KProperty类型
 * 的反射对象，该对象描述prop自身*/


/** 提供委托
 * 通过定义provideDelefate操作符，可以扩展创建属性实现所委托对象的逻辑，如果by右侧所使用的对象将provideDelegate定义为成员或扩展函数，
 * 那么会调用该函数来创建属性委托实例
 *
 * provideDelegate 的一个可能的使用场景是在创建属性是检测属性一致性
 *
 * 例如，如果要绑定之前检测属性名称，可以这样写：
 * */
//class ResoureDelegate<T>:ReadOnlyProperty<MyUI,T>{
//    override fun getValue(thisRef:MyUI,property: KProperty<*>):T{}
//}
//class ResourceLoader<T>(id:ResourceID<T>){
//    operator fun provideDelegate(
//        thisRef:MyUI,
//        prop:KProperty<*>
//    ):ReadOnlyProperty<MyUI,T>{
//        checkProperty(thisRef,prop.name)
//        return ResourceDelegate()
//    }
//    private fun checkProperty(thisRef:MyUI,name:String){}
//}
//class MyUI{
//    fun <T> bindResource(id :ResourceID<T>):ResourceLoader<T>{}
//    val image by bindResource(ResourceId.image_id)
//    val text by bindResourc(ResourceID.text_id)
//}
/** provideDelegate的参数与getValue相同：
 * - thisRef  必须与属性所有者类型（对于扩展属性-只被扩展的类型）相同或者是它的超类型
 * - property 必须是类型KProperty<*> 或其超类
 *
 * 在创建MyUI实例期间，为每个属性调用provideDelegate方法，并立即执行必要的验证，如果没有这种拦截属性与其委托之间的绑定的能力，
 * 为了实现相同的功能，你必须显示传递属性名，不是很方便
 *
 * */

//检测属性名不使用provideDelegate功能
//class MyUI{
//    val image by bindResource(ResourceID.image_id,"image")
//    val text by bindResource(ResourceID..text_id,"text")
//}
//fun <T> MyUI.bindResource(
//    id:ResourceId<T>,
//    propertyName:String
//):ReadOnlyProperty<MyUI,T>{
//    chekProperty(this,propertyName)
//}

/** 当有provideDelegate方法初始化辅助的prop$delegate属性*/
//class C{
//    var prop :Type by MyDelegate()
//}

//编译器生成
//class C{
////    //调用provideDelegate来创建额外的delegate属性
////    private val prop$delegate = MyDelegate().provideDelegate(this,this::prop)
////    var prop:Type
////        get() = prop$delegate.getValue(this,this::prop)
////        set(value:Type) = prop$delegate.setValue(this,this::prop,value)
////}
/** 注意，provideDelegate方法只影响属性的创建，并不影响为getter或setter生成的代码*/