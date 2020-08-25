package chap_04

/**
 * Created by wangjiao on 2020/7/31.
 * description:
 */

/**
 * 高阶函数会带来运行时的效率损失，每一个函数都是一个对象，并且会捕获一个闭包，即在那些函数体内会访问到的变量，内存分配（对于函数对象和类）
 * 和虚拟调用会引入运行时间开销
 *
 * 但是有时候可以通过内联话lambda表达式可以消除这类的开销。如lock()函数可以很容易地在调用处内联，考虑下面的情况：
 * lock(l){ foo() }
 *
 * 编译器没有为参数创建一个函数对象并生成一个调用。取而代之，编译器可以生成一下代码：
 * l.lock()
 * try{
 *  foo()
 * }finally{
 *    l.unlock()
 * }
 *
 * 为了让编译器这么做，我们需要使用inline标识符标记lock()函数
 * inline fun <T> lock(lock:Lock,body:() -> T):T{...}
 *
 * inline修饰符影响函数本身 和 传给它的lambda表达式：所有这些都将内联到调用处
 *
 *
* */

/**
 * 禁用内联
 * 如果只希望一部分传给内联函数的lambda表达式参数，那么可以用noinline修饰符标记不希望内联的函数参数
 * inline fun foo(inlined:() ->Unit,noinline notInlined:() -> Unit){...}
 *
 * 可以内联的lambda表达式只能在内联函数内部调用 或者 作为可内联的参数传递，但是 noinline 的可以任意方式操作：存储在字段中，传送它等。
 * */

/**
 * 非局部返回
 * 退出一个lambda表达式，需要使用一个标签。并且在lambda表达式内部禁止使用裸return，因为lambda表达式不能使包含他的函数返回：
 * fun foo(){
 *    func{
 *      return //错误，不能使foo 在此处返回
 *    }
 * }
 *
 * 如果lambda表达式传给的函数时内联的，该return也可以内联，所以它是允许的
 * fun foo(){
 *      inlined{
 *          return //ok 该lambda表达式是内联的
 *      }
 * }
 * 这种返回（位于lambda表达式中，但退出包含它的函数）称为非局部返回。
 * 一般会在循环中用这样的结构
 *
 * for hasZeros(ints:List<Int>):Boolean {
 *      ints.forEach{
 *          if(it==0) return true //从hasZeros返回
 *      }
 *      return false
 * }
 *
 * inline fun f(crossinline body:() -> Unit){
 *      val f = object:Runnable {
 *          override fun run() = body()
 *      }
 * }
 *
 * break 和 continue 在内联的lambda表达式中还不可用
 * */

/**
 * 具体化的类型参数
 * fun <T> TreeNode.findParentOfType(clazz:Class<T>):T?{
 *      var p parent
 *      while(p!=null && !clazz.isInstance(p)){
 *          p=p.parent
 *      }
 *      @Suppress("UNCHECHED_CAST")
 *      return p as T?
 * }
 * 调用不优雅
 * treeNode.findParentOfType(MyTreeNode::class.java)
 * 我们想要的调用：
 * treeNode.findParentOfType<MyTreeNode>()
 *
 *
 * 为了能这么做，内联函数支持 具体化的类型参数 ，于是这样写：
 * inline fun <reified T> TreeNode.findParentOfType():T?{
 *      var p = parent
 *      while(p!=null && p!is T){
 *          p=p.parent
 *      }
 *      return p as T?
 * }
 *
 * 使用reified 修饰符限定类型参数，可以在函数内访问它。由于函数时内联的，不需要反射，正常的操作符如！is 和 as现在都能用了。此外，还可以安装上面提到的方式调用它
 *
 * inline fun <reified T> membersOf() = T::class.members
 *
 * fun main(s:Array<String>){
 *    println(membersOf<StringBuilder>().joinToString("\n"))
 * }
 * */

/**
 * 内联属性
 * inline修饰符可以用于没有幕后字段的属性的访问器，可以标注独立的属性访问器
 *
 * val foo:Foo
 *      inlie get()=Foo()
 *
 * val bar:Bar
 *      get()= ...
 *      inline set(v){...}
 *
 * 也可以标注整个属性，将它的两个访问器都标记成内联的
 * inline var bar:Bar
 *  get() =
 *  set(v){...}
 *
 *  在调用处，内联访问器如同内联函数一样内联
 *
 * */


