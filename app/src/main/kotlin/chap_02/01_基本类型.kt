package chap_02

/**
 * Created by mumu on 2020/6/15.
 * 功能描述：
 */
fun main(){
    funH()
}
fun funA(){
    fun printDouble(d:Double){println(d)}
    val i=1
    val d =1.1
    val f =1.1f
    /** kotlin中的数字没有隐式拓宽转换*/
//    printDouble(f)
//    printDouble(i)
    printDouble(d)

    /** 数字字面值中的下划线*/
    val oneMillion = 1_000_000;
    val creditCardNumber =1234_5678_9012_3456L;
    val socialSecurityNumber = 0Xff_ec_de_5e_aa
    val bytes = 0b110110110_001101_0101001;

    /** 数字装箱不一定保留同一性*/
    val a:Int = 100
    val boxedA:Int?=a
    val anotherBoxedA:Int?=a

    val b :Int = 10000
    val boxedB:Int?=b
    val anotherBoxedB:Int?=b
    println(boxedA === anotherBoxedA)//true
    println(boxedB === anotherBoxedB)//false

    /** 保留了相等性*/
    val a1:Int =10000
    println(a1==a1)//true
    val boxedA1:Int? =a1
    val anotherBoxA1:Int?=a1
    println(boxedA1 == anotherBoxA1)//true

}
fun funB(){
    /** 显性转换,较小类型不是较大类型的子类型*/
//    val a:Int?=1 //一个装箱的Int
//    val b:Long?=a  隐式转换产生一个装箱的Long。但其实并不能
//    println(a==b) //false ，会先检测另一个是不是Int类型

    /** 相等性都会失去，更别提同一性了*/

    /** 不进行显式转换的情况下，不能讲Byte复制给Int变量*/
    val b: Byte = 1
    println(b)
//    val i: Int = b  //错误，编译不过
    val i:Int = b.toInt() //显示拓宽
    println(i)

    /** 算数运算会有重载做适当的转换*/
    val l = 1L + 3 //Long + Int => Long

}

fun funC(){
    /** 位运算*/
    val x = (1 shl 2) and 0x000ff000
    println(x)
    /** 位运算列表，只能用于Int 与 Long
     *  shl(bits) 有符号左移
     *  shr(bits) 有符号右移
     *  ushr(bits) 无符号右移
     *  and(bits) 位与
     *  or(bits)  位或
     *  xor(bits)  位异或
     *  inv()      位非
     *
     *
     * */

}

fun funD(c:Char):Int{
    /** 字符，当需要可用引用时，会被装箱，但不会保留同一性*/
    if(c !in '0'..'9')
        throw IllegalArgumentException("out of range")
    return c.toInt()-'0'.toInt()
}
fun funE(){
    /** 数组，使用Array类来表示，定义了get和set函数以及size函数*/
    /** 通过arrayOf创建数组，也可以通过arrayOfNulls() 创建指定大小的，所有元素为空的数组*/
    val arr = arrayOf(1,3,5,6)
    var nullarr = arrayOfNulls<String>(9)
    for(a in 0..nullarr.size-1)
        println(nullarr[a])

    /** 创建一个Array<String>初始化为["0","1","4","9","16"]*/
    val asc = Array(5){ i -> (i*i).toString()}
    asc.forEach { i-> println(i) }
}

fun funF(){
    /** 无符号整数
     * UByte    无符号8比特整数， 0-255
     * UShort   无符号16比特整数  0-65535
     * UInt     无符号32位比特整数  0-2^32-1
     * ULong    无符号64位比特整数 0-2^64-1
     * */

    /** 字面值 u U标记为无符号*/
    val b:UByte =1u
    val s : UShort =1u
    val l : ULong =1u

    val al = 42u //UInt ,未提供预期类型，常量适于uInt
    val a1= 0xffff_fff_fffu //ULong 不适用于UInt,则自动升

}
fun funG(){
    /** 字符串*/
    val text ="""
        for(c in 'foo')
        println(c)
    """.trimMargin() //这个是去掉前导空格的

    val t ="""
        >Tell me and i forget
        >Teach me and I remeber
        >Involve me and I learn
        >(Beenjamin franklin)
    """.trimMargin(">") //可以去掉每行前面的空格
    println(t)
    val t2 ="""
        |Tell me and i forget
        |Teach me and I remeber
        |Involve me and I learn
        |(Beenjamin franklin)
    """ //也可以去掉每行前面的空格
    println(t2)
}
fun funH(){
    /** 字符串模板*/
    val i =10
    println("i=$i")

    val s= "abc"
    println("$s.length is ${s.length}")//abc.length is 3

    /** 在原始字符串中表示字面量$  原始字符串不支持反斜杠*/
    val m =9.99
    val price ="""
       ${'$'}$m
    """
    println(price)
}