package chap_01

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.constraintlayout.solver.widgets.Rectangle
import java.io.File
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by mumu on 2020/6/9.
 * 功能描述fun
 */
@RequiresApi(Build.VERSION_CODES.O)
fun main(){
//    "hello world".spaceToCamelCase()
//    notnull()
//    getFun()
//    enablenull()
//    println(transform("Blue"))
//    test()
//    foo(2)
//    val arr = arrayOfMinusOnes(3)
//    for(a in arr){
//        println(a)
//    }

//    println(theAnswer())
//    callObj();
//   println(argFun())
//    tryFun()
//    boolfun()
//    change()
    calcTaxes()
}

/** 函数的默认参数*/
fun foo(a:Int ,b:String =""){}

/** 过滤list*/
fun filterFun(){
    val list = listOf(1,3,5,3)
    val positive = list.filter { x-> x>3 }
    //可以更短
    val positive2 = list.filter { it > 0 }
}

/**检查元素是否存在于集合中*/
fun checkContainList(){
    val emailList = listOf("xxx","xx")
    if("john@example.com" in emailList){}
    if("xxx" !in emailList){}
}

/** 字符串内插*/
fun strFun(){
    val name = "jone"
    println("Name $name")
}

/** 类型判断*/
fun typeFun(){
    val x="a"
    when(x){
        is String -> println("is String")
        else -> println("else ")
    }
}

/** 遍历map/pair型list*/
fun foreachFun(){
    val map = hashMapOf<Integer,String>()
    map.put(Integer(1),"a")
    map.put(Integer(2),"b")
    map.put(Integer(3),"c")
    map.put(Integer(4),"d")
    for((k,v) in map){
        println("$k - $v")
    }
}

/** 使用区间 */
fun rangefun(){
    for(i in 1..100){}//闭区间，包含100
    for(i in 1 until 100){}//半开区间，不包含100
    for(x in 2..10 step 2){}
    for(x in 10 downTo 1){}
    val x=2
    if(x in 1..10){}
}

/** 只读list */
fun readFun(){
    val list = listOf(1,2,3)
    val map = mapOf("a" to 1,"b" to 2,"v" to 3)
    //访问map
    println(map["a"])
   map["a"] to 4;
    println(map["a"])

}

/** 延迟属性*/
fun  lazyfun(){
    val p:String by lazy{
        "a"
    }
    println(p)
}

/** 扩展函数
 * 这个厉害哦，其实是封装的上面再封装
 * */
fun String.spaceToCamelCase(){
   println(toUpperCase())
}
/** 创建单例*/
object Resource{
    val name ="name"
}

/** if not null缩写*/
fun notnull(){
    val files = File("Test").listFiles();
    println(files?.size)
    //if not null and else
    println(files?.size?:"empty")

    val values=mapOf("aa" to 1,"vv" to 2)
    val emial =values["emial"]?:throw IllegalStateException("email is missing")
}

/** 有可能会空的集合中取第一个元素*/
fun getFun(){
    val value = 12
    value?.let {
        //执行到这里，data不为空
        println(it)
    }
}
/** 映射可空值*/
fun enablenull(){
//    val value =mapOf("a" to 1,null to 2)
    val value =null
    val defaultValue ="def"
    val mapped = value?.let {
        transformValue(it)
    }?:defaultValue
    println(mapped)
}
fun transformValue(v:Any){
    println(v)
}

/** 返回when表达式*/
fun transform(color:String):Int{
    return when(color){
        "Red" ->0
        "Green" ->1
        "Blue" ->2
        else ->3
    }
}

/** try catch 表达式*/
fun test(){
    val result = try{
        println(2/0)
    }catch (e:ArithmeticException){
        throw java.lang.IllegalStateException(e)
    }
}

/** if表达式
 * 评价：一般，不算新，只是换了方式。但是结构比较清晰吧
 * */
fun foo(params:Int){
    val result = if(params==1){
        "one"
    }else if(params==2){
        "two"
    }else {
        "three"
    }
    println(result)
}

/** 返回类型为unit的方法的builder风格用法
 * 评论：不是返回unit呀，这是数组初始化么？
 * */
fun arrayOfMinusOnes(size:Int):IntArray{
    return IntArray(size).apply { fill(4) }
}

/** 单表达式函数*/
fun theAnswer()=43
//等价于
fun theAnswer2():Int{
    return 43
}

/** 单表达式函数与其他惯用法一起使用能简化代码，如和when表达式一起使用
 * 评论：语法糖
 * */
fun transform2(color:String):Int=when(color){
    "Red"->0
    "Blue"->1
    "Green"->2
    else ->4
}

/** 对一个对象实例调用多个方法 （with）*/
class Turtle{
    fun penDown(){
        println("pendown")}
    fun penUp(){
        println("penUp")
    }
    fun turn(degree:Double){
        println("turn")
    }
    fun forward(pixels:Double){
        println("forward")
    }

}
/**
 * 评价：只是减少了 '对象.' 而已，语法糖
 * */
fun callObj(){
    var myTurtle = Turtle()
    with(myTurtle){
        penDown()
        for(i in 1..4){
            forward(100.0)
            turn(90.0)
        }
        penUp()
    }
}
/** 配置对象属性 。用于配置未出现在对象构造函数中的属性非常有用。
 *  评价：不会用？
 * */
fun argFun():Rectangle{
    val myRect = Rectangle().apply {
       val length =3
       val breadth =4
       val color = 0xFAFAFA
    }
    return myRect
}

/** java7的try with resource*/
@RequiresApi(Build.VERSION_CODES.O)
fun tryFun(){
    val stream = Files.newInputStream(Paths.get("/some/file.txt"))
    stream.buffered().reader().use{
            reader->
        println(reader.readText())
    }
}

/** 对于需要泛型信息的泛型函数的适宜形式*/
//public final class Gson{
//    public <T> fromJson(JsonElement json,Class<T> classOfT) throw JsonSyntaxException{
//
//    }
//}
//inline fun <reified T:Any> Gson.fromJson(json:JsonElement):T =this.fromJson(json,T::class.java)

/** 使用空布尔 */
fun boolfun(){
    val b:Boolean ?=null
    if(b==true){
        println("true")
    }else{
        println(b)
    }
}

/** 交换两个变量
 * 评价：省去了tmp,很方便
 * */
fun change(){
    var a=1
    var b=2
    a=b.also { b=5 }
    println(a)//2
    println(b)//5
    b = a.also { a=3 }
    println(b)//2
}
/** 将代码标记为不完整 。会在TODO工具窗口看到这行代码
 * 评论：新功能，好东西。
 * */
fun calcTaxes():BigDecimal = TODO("waiting for feedback from accounting")

