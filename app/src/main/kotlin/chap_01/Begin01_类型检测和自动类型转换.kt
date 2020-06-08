package chap_01

/**
 * Created by mumu on 2020/6/8.
 * 功能描述：
 */
fun main(){
    print(getStringLength3("abh"))
}
fun getStringLength(obj:Any):Int?{
    if(obj is String){
        /*obj 在该条件下，自动转换成String*/
        return obj.length
    }
    /* 离开类型检测分支，obj任然是Any类型*/
    return null
}

fun getStringLength2(obj:Any):Int?{
    if(obj !is String) return null
    /* obj 在这一个分支自动转换成String*/
    return obj.length
}
fun getStringLength3(obj:Any):Int?{
    /* obj在 ‘&&’右边自动转成了String类型*/
    if(obj is String && obj.length>0)
        return obj.length
    return null
}