package chap_02

/**
 * Created by mumu on 2020/6/15.
 * 功能描述：
 */
fun main(){
   d()
}

/** 这里直接返回a() 导致end没有打印*/
fun a(){
    listOf(1,2,4,5).forEach {
        if(it==4)return
        print(it)
    }
    println("end")
}
/** 这里给出标签，指定返回lambda。所以会打印end*/
fun b(){
    listOf(1,2,4,6).forEach lit@ {
        if(it==4) return@lit
        print(it)
    }
    println("end")
}

/** 标签接收lambda同名*/
fun c(){
    listOf(1,2,4,6).forEach  {
        if(it==4) return@forEach
        print(it)
    }
    println("end")
}
/** 匿名函数替代lambda表达式，return会从匿名函数返回*/
fun d(){
    listOf(1,2,3,5,6).forEach (fun(value:Int){
        if(value==3)return
        println(value)
    } )
    println("end")
}

/** 模拟break */
fun e(){
    run loop@{
        listOf(1,3,5,6).forEach {
            if(it==3)return@loop //从传入run的lambda表达式非局部返回
            print(it)
        }
    }
    println("end")

//    return@a 1  //代表返回1到@a
}