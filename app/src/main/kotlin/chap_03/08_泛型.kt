//package chap_03
//
///**
// * Created by mumu on 2020/6/20.
// * 功能描述：
// */
///** 与java类似*/
//
//class Box<T>(t:T){
//    var value = t
//}
//
///** 创建实例，需要提供类型参数*/
//val box:Box<Int> = Box<Int>(1)
///** 如果类型参数可以推断出来，如从构造函数的参数或者其他途径，允许省略类型参数*/
//val box2 = Box(2)
//
///** 型变 java中有通配符，kotlin没有，相反，它有两个其他的东西，声明处形变 和 类型投影
// * <? extends Object> 带extends限定上界的通配符类型使用类型是协变的
// * <? super String > 是list<Object> 的一个超类。这称为 逆变性
// *
// * 只能读取的对象为生产者 extends，只能写入的对象为消费者 super
// * 但是list<? extends Foo> 在这个对象不允许调用add() 或set() ，但并不意味着该对象是不可变的，如，没什么阻止你调用clear()
// * ，因为clear()无需任何参数。通配符保证的唯一的事情是类型安全，不可变性完全是另一回事。
// * */
//
///** 声明处形变 假设有一个泛型接口Source<T> ,该接口中不存在任何以T 作为参数的方法，只是方法返回T类型值*/
////interface T Source<T>{
////    T nextT()
////}
///** 在source<Object>中存储source<String>实例的引用是安全的，但java并不知道这一点，并且任然禁止这样的操作*/
////void demo(Source<String> strs){
////    Source<Object> obj =str;
////}
///**
// * 为了修正这一点，必须声明对象的类型为<? extends Object> 这是毫无意义的，因为我们可以像以前一样在该对象上调用所有相同的方法。
// * 所以更复杂的类型并没有带来价值，但编译器并不知道
// *
// * 在kotlin中，有一种方法向编译器解释这种情况，这称为声明处形变：我们可以标注Source的类型参数T来确保它仅从Source<T>成员中返回
// * （生产）,为此，我们提供out修饰符
// * */
//interface Source<out T>{  //声明处型变
//    fun nextT():T
//}
//fun demo(strs:Source<String>){
//    val objs :Source<Any> = strs //这是没问题的，因为T是一个out 参数
//}
//
///** 一般原则是：当一个类C的类型参数T被声明为out时，他就只能出现在C的成员的输出位置，但回报是C<Base>可以安全的
// * 作为C<Derived>超类.
// * 简单说，类C在参数T上是协变的，或者说T是一个协变的类型参数。你可以认为C是T的生产者，而不是T的消费者
// * out修饰符称为型变注解，并且由于它在类型参数声明处提供，也称为声明处型变。这与java的使用处型变相反，其类型用途通配符使得类型协变
// *
// * 另外除了out,kotlin又补充了一个型变注释：IN .它使得一个类型参数逆变：只可以被消费而不可以被生产。逆变类型的一个很好栗子是Comparable:
// *
// * */
//interface Comparable<in T>{
//    operator fun compateTo(other:T):Int
//}
//
//fun demo(x:Comparable<Number>){
//    x.compateTo(1.0)
//    val  y:Comparable<Double> = x
//}
//
///** 类型投影，
// * 使用处型变：类型投影
// * 将类型参数T声明成out非常方便，并且能避免使用处子类型化的麻烦，但是有些类实际上不能限制为只返回T
// * */
////class Array<T>(val size:Int){
////    fun get(index:Int):T{}
////    fun set(index:Int ,value:T){}
////}
///** 考虑下述函数*/
//fun copy(from:Array<Any>,to : Array<Any>){
//    assert(from.size==to.size)
//    for(i in from.indices){
//        to[i]=from[i]
//    }
//}
//val ints:Array<Int> = arrayOf(1,2,3)
//val any = Array<Any>(3){""}
//fun a(){
////    copy(ints,any) //错误，期望的类型是any,但此时类型是int
//}
//
///**
// * 这里遇到熟悉的问题：Array<T>在T上是不型变的，因此Array<Int>和Array<Any> 都不是另一个的子类。为什么？因为copy可能做坏事，它可能尝试
// * 写一个string到from，并且如果我们实际上传递一个Int的数组，一段时间后，将会抛出一个ClassCastException异常
// * 那么，我们唯一要确保的是copy不做坏事，我们阻止它写到from
// * */
///** 这里发生了类型投影，from不仅仅是一个数组，而是一个受限制的（投影的）数组：我们只可以调用返回类型为类型参数T的方法，这意味着只能调用get()
// * 这就是使用处型变的用法，并且是对应于Java的Array<? extends Obejct>。*/
//fun copy2(from:Array<out Any>,to:Array<Any>){} //使用处型变，填子类。
//
//val ints2 :Array<Int> = arrayOf(1,2,4)
//val any2 = Array<Any>(3){""}
//fun aa(){
//    copy2(ints2,any2)
//}
//
///** 也可以使用in投影一个类型,array<in String> 对应于Java中的Array<? super String> ，也就是说，你可以传递一个charSequence数组
// * 或一个Object数组给fill函数*/
//fun fill(dest:Array<in String>,value:String){} // in 填父类
//
///** 星投影 当对类型参数一无所知，但任然希望以安全的方式使用它，这里定义泛型类型的这种投影，该泛型类型的每个具体实例化将是该投影的子类型
// * kotlin 为此提供了所谓的 星投影 语法
// * - 对于Foo< out T:TUpper> ，其中T是一个具有上届TUpper的协变类型参数，Foo<*> 等价于Foo<out TUpper>。这表示T是未知的，你可以安全的从Foo<*>读取TUpper的值
// * - 对于Foo<in T>,其中T是一个逆变类型参数，Foo<*> 等价于Foo<in Nothing>.这意味着T是未知的，没有什么可以以安全的方式写入Foo<*>
// * - 对于Foo<T:TUpper> ，其中T是一个具有上届TUpper的不型变类型参数，Foo<*>对于读取值时等价于Foo<out TUpper> .对于写值时等价于Foo<in nothing>
// *
// *   Function<in T,out U> ，可以想象以下星投影
// *   - Function<*,String> 表示Fuction<in nothing,String>
// *   - function<Int,*> 表示Function<Int,out any?>
// *   - Function<*,*> 表示Function<in Nothing,out Any?>
// *
// *   星投影非常像Java原始类型，但是安全的
// * */
//
///** 泛型函数，不仅类可以有类型参数，函数也可以有，类型参数要放在函数名称之前*/
//fun <T> singletonList(item :T):List<T>{}
//fun <T> T.basicToString():String{
//    //扩展函数
//}
//
///** 调用泛型函数，在调用处函数名之后指定类型参数即可*/
//val l = singletonList<Int>(1)
////也可以省略，从上下文中可以推断出
//val a = singletonList(1)
//
///** 泛型约束
// * 上届：与java的extends对应
// * */
//fun <T : Comparable<T>> sortA(list:List<T>){}
///** 冒号之后指定的类型是上届，只有comparable<T> 的子类型可以替代T */
//fun b(){
////    sort(listOf(1,2,3)) //错误？Int不是Comparable<Int>的子类型？ 文档是成功的，是子类型
////    sort(listOf(HashMap<Int,String>())) //错误
//}
//
///* 默认的上届（如果没有声明） 是Any? 在尖括号中，只能指定一个上届。如果同一类型参数需要多个上届，需要一个单独的where子句*/
//fun <T> copyWhenGreater(list:List<T> ,threshold:T):List<String>
//    where T :CharSequence,
//          T : Comparable<T>{
//    return list.filter{ it > threshold }.map { it.toString() }
//}
///** 传递的类型必须同时满足where子句的所有条件。在上述示例中，类型T 必须既实现了CharSequence 也实现了 Comparable.*/
//
///**类型擦除，Foo<Bar> 与 Fo0<Baz?> 的实例都会被擦除为Foo<*>
// * 因此，并没有一个通用的方法在运行时检测一个泛型类型的实例是否通过指定类型参数所创建。
// * */
//
//
//
//
//
//
//
//
//
