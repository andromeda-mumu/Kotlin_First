package chap_01

/**
 * Created by mumu on 2020/6/8.
 * 功能描述：
 */
fun main(){
    printProduct("a","7")
    printProduct2("b","u")
}

/** 当某个变量的值可以为Null时，必须在声明处的类型后添加？来标识该引用可以为空
 * return : 如果str的内容不是数字，则返回null
 * */
fun parseInt(str:String):Int?{
    try{
        return Integer.parseInt(str)
    }catch (e:Exception){
        return null;
    }

}
/** 当使用可能返回空值的函数时，要进行非空判断 */
fun printProduct(arg1:String,arg2:String){
    val x= parseInt(arg1)
    val y = parseInt(arg2)
    if(x!=null && y!=null){
        print(x*y)
    }else{
        println("'$arg1' or '$arg2' is not a number")
    }
}

/** 也可以先进行判空检测，能走到后面，就肯定不是null*/
fun printProduct2(arg1:String,arg2:String){
    val x= parseInt(arg1)
    val y = parseInt(arg2)
    if(x==null){
        println("wrong number format in arg1:$arg1")
        return
    }
    if(y==null){
        println("wrong number format in arg2:$arg2")
        return
    }
    println(x*y)
}