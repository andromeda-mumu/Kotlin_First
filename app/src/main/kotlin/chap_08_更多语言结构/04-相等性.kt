package chap_08_更多语言结构

/**
 * Created by wangjiao on 2020/8/27.
 * description:
 */
/**
 * -结构限定 equal
 * -引用相等
 *
 * 结构相等由==（！=）操作判断，a==b 会翻译成 a?.equals(b) ?:(b===null)
 * 这句话意思是，如果a不是null,则调用equal(Any?)函数，否则（即a=null）检测b是否与null引用相等
 *
 * 结构相等 与 comparable<...>接口定义无关，因此只有自定义的equals(Any?)实现可能会影响该操作符的行为
 * */

