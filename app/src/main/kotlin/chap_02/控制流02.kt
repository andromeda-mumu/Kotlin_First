package chap_02

import chap_01.parseInt

/**
 * Created by mumu on 2020/6/15.
 * 功能描述：
 */
fun main(){

}
 fun funI(){
    /** if作为表达式，有返回值*/
    val a=4
     val b=3
    val max = if(a>b) a else b
     /** if分支可以是代码块，最后的表达式作为该块的值*/
     val max2 = if(a>b){
         println("choose a")
         a
     }else{
         println("choose b")
         b
     }

     /** when取代了switch */
     when(a){
         1-> println("x==1")
         2-> println("x==2")
         else -> println("x is neither 1 or 2")
     }
     /** 如果很多分支需要相同的方式处理，可将多个分支条件放在一起*/
     when(a){
         0,1->println("x==0 or x==1")
         else -> print("otherwise")
     }
     /** 可以任意表达式，而不是常量*/
     when(a){
         parseInt("a") -> println("s encodes x")
         else-> print("emm ")
     }

     /** 区间检测*/
     when(a){
         in 1..10 -> println("x is in the range")
//         in validNumbers -> print("s is valid")
         !in 20..30 -> print("x is out of range")
         else -> print("else")
     }

     /** 检测类型,且会智能转换*/
     val x :Any ="s"
     when(x){
         is String -> x.startsWith("prefix")
         is Int -> a-2
         else -> print("false")
     }

     /** when取代if else*/
     val m:Any =3
     when{
//         m.isOdd()-> println("true")
//         a.isEven()-> print("false")
         else -> print("else")
     }

     /** 可以将when的主语捕获到变量中*/
//     fun DownloadManager.Request.getBody()=
//         when(val response = executeRequest()){
//             is Success ->response.body
//             is HttpError -> throw HttpException(response.status)
//         }

     /** 通过索引遍历list或数组*/
     val arr = arrayOf(1,3,5,6)
     for(i in arr.indices )
         println(arr[i])

     /** 或者是withIndex 库函数*/
     for((index,value) in arr.withIndex()){
         println("the element at $index is $value")
     }
 }

