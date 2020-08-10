package chat_05_集合

/**
 * Created by wangjiao on 2020/8/4.
 * description:
 */
fun main(){
//    for(i in 1..3)
//        println(i)
//    for(i in 4 downTo 1)
//        print(i)
//    for(i in 1..10 step 2 ){
//        print(i)
//    }
//    for(i in 10 downTo 1 step 2)
//        print(i)
//
//    /** 不包含结束元素*/
//    println()
//    for (i in 1 until 10)
//        print(i)

    /** 区间 */
//    val versionRange = Version(1,10)..Version(1,30)

    /** 数列
     * 三个基本属性：first元素，last元素，非零的step
     * */
//    for(int i=first;i<=last;i+=step){
//
//    }
    for(i in 1..9 step 3)
        print(i)


    /** 数列实现了iterator<N> 因此可以在集合中使用*/
    println((1..10).filter { it%2==0 })

}
