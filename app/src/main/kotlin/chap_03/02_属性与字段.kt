package chap_03

/**
 * Created by mumu on 2020/6/16.
 * 功能描述：
 */
/** 显示初始化器，隐含默认getter setter*/
class A{
    var size =1
    var name:String
        get()=this.toString()
        set(value){
            name = value
        }

    /** 如果可以从getter推断出类想，则可以省略 */
    val isEmpty get()=this.size==0

    /** 改变访问器的可见性 或 对其注解*/
    var setterVisiblility:String ="abc"
        private set

    val setterWithAnnotation:Any?=null
//        @Inject set //用inject注解此setter

    /** 幕后字段，不能直接声明字段，但可以用field标识符在访问器中引用*/
    var counter=0
        set(value){
            if(value >=0 ) field =value
        }

    /** 幕后属性 */
    private var _table:Map<String,Int>?=null
    public val table:Map<String,Int>
        get(){
            if(_table==null){
                _table = HashMap()
            }
            return _table?:throw AssertionError("set to nul by another thread")
        }




}
/** 编译器常量
 *  1.位与顶层或object声明 或  companion object的一个成员
 *  2.以String或原生类型值初始化
 *  3.没有自定义getter
 *
 * */
//    const val PATH :String="this is const"

/** 延迟初始化属性和变量 一般属性声明为非空类型必须在构造函数中初始化
 * 可以使用关键字lateinit，只用于实体类中的属性（不是在主构造函数中声明的var属性，并且仅当该属性没有自定义getter 或setter时）
    也用于顶层属性与局部变量。该属性或变量必须为非空类型，并且不能是原生类型
 * */
public class MyText{
    lateinit var subject:A
//    @SetUp fun setup(){
//        subject = A()
//    }
//    @Test fun test(){
//        subject.hashCode()  //直接引用
//    }

    /** 检测是否初始化 */
//    if(subject.isInitialized){
//        println("")
//    }
}