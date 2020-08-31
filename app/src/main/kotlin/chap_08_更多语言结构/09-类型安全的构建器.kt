package chap_08_更多语言结构

import java.lang.StringBuilder

/**
 * Created by wangjiao on 2020/8/31.
 * description:
 */
/**通过使用命名得当的函数作为构建器，结合带有接收者的函数字面值，可以在kotlin中创建类型安全，静态类型的构建器。
 *
 * 类型安全的构建器可以创建基于kotlin的适用于采用半声明方式构建复杂层次数据结构领域专用语言。以下是应用场景
 * -使用kotlin代码生成标记语言，如HTML XML
 * -以编程的方式布局UI组件  anko
 * -为web服务器配置路由 ：Ktor
 * */
//fun result()={
//    html{
//        head{
//            title{+"XML encoding with kotlin"}
//        }
//        body{
//            h1 {+"XML encoding with kotlin"}
//            p {+"this format can be used as an alternative markup to xml"}
//
//        }
//    }
//}

//html实际上是一个函数调用，它接受一个lambda表达式 作为参数
//for html(int:HTML.() -> Unit):HTML{
//    val html = HTML()
//    html.init()
//    return html
//}
/**
 * 这个函数接受一个init的参数，该参数本身是个函数。该函数的类型是HTML.()->Unit.它是一个带接收者的函数类型。这意味着 需要想函数传递一个HTML类型的实例（接收者）
 * 并且我们可以在函数内部调用该实例的成员，该接收者可以通过this关键字访问
 * */
//html{
//    this.head{}
//    this.body{}
//    像往常一样，this省略
//        head{}
//        body{}
//}


//fun head(inti:Head.() -> Unit) :Head{
//    val head = Head()
//    head.init()
//    children.add(head)
//    return head
//}
//fun body(init:Body.()->Unit):Body{
//    val body = Body()
//    body.init()
//    children.add(body)
//    return body
//}

/** 实际上，这两个函数做同样的事情，因此可以写一个泛型版本 ，initTag*/
//protected fun <T:Element> initTag(tag:T,init:T.()->Unit):T{
//    tag.init()
//    children.add(tag)
//    return tag
//}
//fun head(init:Head.()->Unit) = initTag(Head(),init)
//fun body(init:Body.()->Unit) = initTag(Body(),init)

/** 如何像标签体中添加文本 */
//html{
//    head{
//        title {+"文本文本"}
//    }
//}
/** 基本上，我们只是把一个字符串放进一个标签体内部，但是它签名有一个小的+ ，所以这是一个函数调用，调用一个前缀unaryPlus操作。该操作实际上是由一个扩展函数
 * unaryPlus定义的，该函数是tagWithText抽象类（title的父类）的成员
 * */
//operator fun String.unaryPlus(){
//    children.add(TextElement(this))
//}
/** 所以，这里的前缀+所做的事情，就是把一个字符串包装到一个TextElement实例中。并添加到children集合中，以使其成为标签树的一个适当的部分*/

/**--------------作用域控制----------------*/
//htlm{
//    head{
//        head{}//错误: 外部接收者的成员
//    }
//}
//
//html{
//    head{
//        this@html.head{} //可以
//    }
//}

/**--------------com.example.html包的完整定义----------------*/
//大量使用了扩展函数和带有接受者的lambda表达式

interface Element{

    fun render(builder:StringBuilder,indent:String)
}
class TextElement(val text:String):Element{
    override fun render(builder:StringBuilder,indent:String){
        builder.append("$indent$text\n")
    }
}
@DslMarker
annotation class HtmlTagMarker

@HtmlTagMarker
abstract class Tag(val name:String):Element{
    val children = arrayListOf<Element>()
    val attributes = hashMapOf<String,String>()

    protected fun <T:Element> initTag(tag:T,init:T.() -> Unit):T{
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent<$name${renderAttributes()}\n")
        for(c in children){
            c.render(builder,indent+" ")
        }
        builder.append("$indent</$name>\n")
    }
    private fun renderAttributes():String{
        val builder = StringBuilder()
        for((attr,value) in attributes){
            builder.append("$attr=\"$value\"")
        }
        return builder.toString()
    }

    override fun toString(): String {
        val builder=StringBuilder()
        render(builder,"")
        return builder.toString()
    }
}
abstract class TagWithText(name:String):Tag(name){
    operator fun String.unaryPlus(){
        children.add(TextElement(this))
    }
}

class HTML:TagWithText("html"){
    fun head(init:Head.() -> Unit) = initTag(Head(),init)
    fun body(init:Body.() -> Unit) = initTag(Body(),init)
}
class Head:TagWithText("head"){
    fun title(init:Title.()->Unit) = initTag(Title(),init)
}
class Title:TagWithText("title")

abstract class BodyTag(name:String):TagWithText(name){
    fun b(init:B.() -> Unit) = initTag(B(),init)
    fun p(init:P.() ->Unit) = initTag(P(),init)
    fun h1(init:H1.() -> Unit) = initTag(H1(),init)
    fun a(href:String,init:AA.() -> Unit) {
        val a = initTag(AA(),init)
        a.href = href
    }
}
class Body:BodyTag("body")
class B:BodyTag("b")
class P:BodyTag("p")
class H1:BodyTag("h1")

class AA:BodyTag("a"){
    var href:String
        get()=attributes["hreaf"]!!
        set(value){
            attributes["href"]=value
        }
}

fun html(init:HTML.()->Unit):HTML{
    val html=HTML()
    html.init()
    return html
}