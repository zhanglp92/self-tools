## 小白学习JVM

### JVM是什么

* JDK Java Development Kit
* JRE Java Runtime Environment
* JVM Java Virtual Machine
* JDK包含JRE, JRE包含JVM

### 从 HelloWorld 开始

```java
@Log4j2
public class JVMTest {
    public static void main(String[] args) {
        // 10:20:25.968 [main] INFO org.example.codeTest.JVMTest - hello world -- args = []
        log.info("hello world -- args = {}", JSON.toJSONString(args));
    }
}
```

### 方法区(线程共享区域)

一堆看不见的初始化后, 进程启动了线程要开始执行main方法了

线程: 我开始执行了, 看见了实参, 看见了log.info, 结束了.

小白: 你怎么知道我的main在哪和里边的内容.

线程: 因为上帝把你的代码转换成字节码后, 放在了方法区, 并告诉了我一个入口, 说到了入口你就知道接下来该做什么了. 果然走一步看一步就看到了你全部的代码. 

小白: 哦.

Tips: 方法区存储了 已经被虚拟机加载的类信息, 常量, 静态变量, 即时编译器编译后的代码(如动态代理)

### Java虚拟机栈(线程独享区域)

线程: 我去找一块内存, 把你的实参放进来.

小白: 去哪啊?

线程: 我去Java虚拟机栈, 创建一个栈桢, 把你的实参放到栈桢里.

小白: 啥是栈桢?

线程: 我就知道它是个数据结构(活动记录), 用两个寄存器来划定范围, ESP(栈指针,栈底), EBP(桢指针,栈顶)

小白: 哦.

Tips: Java虚拟机栈存放了局部变量表, 操作栈, 动态链接, 方法出口信息. 每一个方法的执行过程, 就是栈桢在虚拟机中从入栈到出栈的过程.

![栈桢](https://pic4.zhimg.com/80/v2-3d901f980d242f6c5ce09fb2175cfa1a_720w.jpg)

### 本地方法栈(线程独享区域)

```java
/**
 * See https://www.jianshu.com/p/1ba925157f7d
 */
@Log4j2
public class JVMTest {
    public native void sayHelloWorld();

    static {
        System.loadLibrary("HelloWorldImpl");
    }

    public static void main(String[] args) {
        JVMTest helloWorld = new JVMTest();
        helloWorld.sayHelloWorld();
    }
}
```

小白: 我之前用C++开发, 比较留念. 我写了一个C++ HelloWorld库, 用java去执行, 你给我讲讲这次是咋运行的.

线程: 这次我需要多加载一个C++库, 之后和刚才一样走到了native方法调用处. 这次需要去本地方法区拿一个栈桢, 执行过程和刚才几乎一样一样的.

小白: 哦.

Tips: 作用和Java虚拟机栈几乎一样, 非Java代码的方法需要从这里获取栈帧.

### 程序计数器(线程独享区域)

线程: 我走一步看一起, 其实都是听从程序计数器大哥的指挥.

Tips: 当前线程所执行的字节码的行号指示器.

### 堆(线程共享区域)

小白: 刚才我创建了一个JVMTest实例你没说怎么出来的.

线程: 你通过new创建的实例, 我需要去堆拿一块内存.

小白: 堆? 堆是什么?

线程: 存放对象实例的一段内存. 对象一生的长短各不相同, 有些对象"朝生夕死", 有些"长生不老". 为了提升GC回收效率大概的将对分为新生代和老年代.

线程: 同时不同的区域也可以使用不同GC策略.

#### 新生代

小白: 哪新生代是什么?

线程: 一般情况新出生的对象会先放到新生代, 默认经历15次垃圾清理, 依然健在的对象会被移到老年代, 也可能是新生代放不下的大对象直接到老年代. 

线程: 新生代又被划分为3个区域: Eden / From Survivor / From Survivor

小白: 继续吧.

![新生代](https://img-blog.csdn.net/20160920101202448)

##### Eden(伊甸园)

线程: 一般对象降生看到的第一片天空. 每次GC大军来扫荡的时候, 已经"夭折的"(无引用的对象)直接清理, 其余的年龄+1并被迁移到To Survivor, 大军过后Eden空空如也.

#### From Survivor(幸存者区域)

线程: 此刻的From Survivor是上一次GC时的To Survivor. GC大军扫荡时同Eden幸存者迁移到ToSurvivor, 其余直接清理, 大军过后空空如也.

#### To Survivor(幸存者区域)

线程: 同时GC大军来清理, 健在的年龄+1, 其余全部清理, 大军过后这个区域更名为From Survivor, 而之前的From Survivor更名为To Survivor.

Tips: Eden / FromSurvivor / ToSurvivor 是被同一批GC大军扫荡的(Young GC)

#### 老年代

小白: 哪老年代又是什么?

线程: Survivor区域的对象被来回倒腾, 成年之后(默认15次GC)健在的直接步入老年区.

线程: 在这里仿佛时间也变慢了, N久之后才会被GC大军整顿一次.

#### 永久代

小白: 永久代又是什么?

线程: Java8已经没有这个区域了.

### GC

小白: 什么样实例才算健在呢?

线程: 对其他实例价值的(被引用的). 判断有没有价值的两个方法: 引用计数法 / 根搜索算法.

#### 垃圾标记算法 -- 引用计数法

线程: 实例a只要被别的实例(b)引用, a的引用计数器+1, b失效时(引用计数器为0)a的引用计数器-1. 

#### 垃圾标记算法 -- 根搜索算法

线程: 根集合. 根集合中的每个实例和其直接或间接链接的实例组成一颗树, 这N棵树之外的节点均被标记为垃圾对象.

#### 垃圾回收算法 -- 标记清除算法

线程: 用垃圾标记算法标记出垃圾实例, 再将其清空.(会产生垃圾碎片问题) 

![标记清除算法](https://mmbiz.qpic.cn/mmbiz_png/9ptCsLpiaicQOxLFgAKJa6UOdckxbKeEOrgvjyRhVkujibfwqYZR7jWzc4DkTcic9objGCDKyonyynW4ibJBibibmBYdg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

#### 垃圾回收算法 -- 复制算法

#### 垃圾回收算法 -- 标记压缩