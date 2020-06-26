package chap_03

/**
 * Created by mumu on 2020/6/20.
 * 功能描述：
 */

/** 数据类 标记为data
 * 编译器自动从主构造函数中声明的所有属性导出以下成员
 * equals() /hashcode()
 * toString() 格式是User(name=John,age=42)"
 * componentN()函数
 * copy()函数
 * */
data class User(val name:String,val age:Int)

/**
 * 为了确保生成的代码的一致性以及有意义的行为，数据类必须满足以下条件：
 * 主构造函数需要至少有一个参数
 * 主构造函数的所有参数需要标记为val 或 var
 * 数据类不能是抽象、开放、密封或者内部的
 * （在1.1之前）数据类只能实现接口
 *
 * 此外，成员生成遵循关于成员继承的这些规则：
 * - 如果数据类体中有显示的equals hashcode toString ，或者这些函数在父类中有final实现，则不会生成这些函数，而会使用现有函数
 * - 如果超类型具有Open的componentN()函数并返回兼容的类型，那么会为数据类生成相应的函数，并覆盖超类的实现。
 *   如果超类型的这些函数由于签名不兼容或者是final而导致无法覆盖，那么会报错.
 * - 从一个已具有copy()函数且签名匹配的类型派生一个数据类在Kotlin 1.2中已弃用，并且在kotlin 1.3中已禁用
 * - 不允许为componentN() 以及copy()函数提供显示实现
 * */

/** 在JVM中，如果生成的类需要含有一个无参的构造函数，则所有的属性必须指定默认值 */
data class Student2(val name:String="",val age:Int =0)

/** 在类体重声明的属性，对于那些自动那你给生成的函数，编译器只是用在主构造函数内部定义的属性，
 * 如需在生成的实现中排除一个属性，需要将其声明在类体中
 * 在toString() equals() hashCode() copy() 的实现中只会用到name属性，并且只有一个component函数component1()
 * 虽然两个Person对象可以有不停的年纪，但它们会视为相等
 * */
data class Person4(val name:String){
    var age:Int =0
}

/** 复制  copy 只改变部分属性 */
class User2(val name: String,val age:Int) {
    fun copy(name:String = this.name,age:Int =this.age) = User(name,age)
}

/** 数据类 与 解构声明 ,为数据类生成的Component函数 使它们可以在解构声明中使用 */
fun main(){
    val jane2 = User("Jane",35)
    val (name,age) = jane2
    println("$name,$age years of age")



//    val jack = User2(name="jack",age=1)
//    val olderJack =jack.copy(age = 40)
//    println(jack.name+" "+jack.age)
//    println(olderJack.name+" "+olderJack.age)


//    val person1 =Person4("John")
//    val person2 = Person4("Merry")
//    person1.age = 10
//    person2.age =20
//    println(person1 == person2)
//    println(person1 === person2)
}

