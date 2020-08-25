## Redis vs MemCache

### Redis 

#### 快的原因

* 基于内存
* 单线程
* IO多路复用

#### 高吞吐原因

* 单线程操作内存数据, 同时不需要考虑并发带来的锁等问题.(这里的缺点: 但进程不能充分使用CPU资源, 解决方案多进程部署)
* 非阻塞I/O多路复用+内部实现的事件, 减少了线程切换时上下文切换和竞争.(单线程处理多个socket请求, 其内部请求处理依然为串行)

#### 数据结构

简单的数据操作和数据结构的优化, 也加快了Redis的处理速度.

* HyperLogLog 统计基数. 标准误差在0.81%但内存占用12K可以统计2^64个数据. 例如统计日活/月活场景 (例: set/bitmap 统计集合的个数) 
* hash中的渐进式rehash, 同时持有两张hash表. 1.维护rehashIdx, 2.有增删改查操作或定时事件迁移数据, 3.老的hash表只删不增.  
* skipList 跳表和红黑树的查找速度都是log(n), 不采用红黑树的理由可能是AVL调整的过程影响性能.
* zipList 压缩列表使用偏移实现链表. (但那个节点的长度不是相同的, 所以新增/删除/修改都有可能出发连锁更新)
* quickList 双向链表, 节点为 zipList或LZF压缩节点.
* 双向链表
* SDS 简单动态字符串 
* intSet
* 数字常数池 

五大数据结构

* String - int / raw(大于39字节) / embStr(小于39字节) (raw和embStr都指向SDS, 唯一的区别 redisObject和SDS有没有一起分配内存)
* Hash - hashTable(拉链解决冲突) / zipList. 所有元素长度都小于64B或节点数量少于512时使用zipList, 否则使用HashTable
* List - zipList / 双向链表. 列表对象大小都小于64B或节点数量少于512时使用zipList, 否则使用双向链表
* Set - intSet / hashTable. 所有元素都是数字且小于512时使用intSet, 否则使用hashTable
* ZSet - zipList / skipList. 所有元素都小于64且个数小于128时使用zipList, 否则使用skipList

#### 淘汰策略

* noEviction - 默认不回收. 写操作直接返回错误.
* LRU - 最近不常使用. 内部分为两种, 1.从全部KEY中淘汰, 2.从设置了过期时间的KEY中淘汰.
* LFU - 最近最少使用. 内部分为两种, 1.从全部KEY中淘汰, 2.从设置了过期时间的KEY中淘汰.
* random - 随机回收. 内部分为两种, 1.从全部KEY中淘汰, 2.从设置了过期时间的KEY中淘汰.
* ttl - 在设置了过期时间的KEY中, 更具KEY的过期时间, 越早过期的被淘汰.

#### 持久化

* RDB 保存某个时刻的数据状态到RDB文件中, 可以手动执行, 也可以定时执行.
* AOF 将服务器执行的命令保存到AOF文件中, 还原时执行一边命令即可.

#### scan命令

* 功能: 迭代全局KEY, 分批返回. (设置的count参数并不是实际的返回个数, 猜想是hash拉链的补全)
* 维护: 全局KEY使用HashTable维护, 定会设计到rehash
* 游标: 游标设计从高位倒着自增. (为了不受rehash的影响, 每次rehash都是成倍增长/减小, 所以也能保证迭代过的不会在被迭代)

## 关键字解释

| 关键词 | 解释 | 备注 |
| -- | -- | -- |
| 多路复用 | 多路: 多个socket链接, 复用: 复用同一个线程 |


## 相关资料

* [Redis为什么快](https://zhuanlan.zhihu.com/p/52600663)
* [Redis数据结构描述](https://i6448038.github.io/2019/12/01/redis-data-struct/)
* [Redis持久化](https://blog.csdn.net/ljheee/article/details/76284082)