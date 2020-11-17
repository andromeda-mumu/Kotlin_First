package chap_08_更多语言结构

/**
 * Created by wangjiao on 2020/8/27.
 * description:
 */

/**
 * - 在类成员中，this指的是该类的当前对象
 * - 在扩展函数或者带有接受者的函数字面值中，this表示在点左侧传递的 接受者 参数
 * */

/** 限定的this */
//class A {//隐式标签@A
//    inner class B{//隐式标签@B
//        fun Int.foo(){//隐式标签@foo
//            val a = this@A
//            val b = this@B
//             val c = this //foo的接收者，一个int
//            val c1 = this@foo //如上
//            val funLit = lambda@ fun String.(){
//                val d = this //funLit 的接收者
//            }
//
//            val funLit2 = {s:String ->
//                //foo的接收者，因为它包含的lambda表达式没有任何接收者
//                val d1 =this
//            }
//
//        }
//
//    }
//
//}

/** implicit this ，当对this调用成员函数时，可以省略” this. “的部分。但是如果有同名的非成员函数时，要谨慎使用*/
fun printLine(){
    println("Top-level function")}
class A {
    fun printLine(){
        println("Member function")}
    fun invokePrintLine(omitThis:Boolean = false){
        if(omitThis) printLine()
        else this.printLine()
    }
}

fun main() {
    A().invokePrintLine()
    A().invokePrintLine(true) //和文档结果不同，这里打印的是 Member function
}