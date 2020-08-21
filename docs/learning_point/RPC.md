## RPC (Remote Procedure Call)

RPC是一种远程过程调用的思想.
* RPC Server (服务提供方)
* Registry (服务注册与发现)
* RPC Client (消费方)

![三者之间的关系](https://user-gold-cdn.xitu.io/2019/3/20/1699bcc02ed24e7f?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)

![调用模型](https://user-gold-cdn.xitu.io/2019/3/20/1699bcd59de9fb4b?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)

### 同步调用流程

1. 服务消费方（client）调用以本地调用方式调用服务；
2. client stub接收到调用后负责将方法、参数等组装成能够进行网络传输的消息体；
3. client stub找到服务地址，并将消息发送到服务端；
4. server stub收到消息后进行解码；
5. server stub根据解码结果调用本地的服务；
6. 本地服务执行并将结果返回给server stub；
7. server stub将返回结果打包成消息并发送至消费方；
8. client stub接收到消息，并进行解码；
9. 服务消费方得到最终结果。

RPC框架封装了2-9

### stub 

需要实现的技术:
* 序列化/反序列化
* 查找目标服务

#### 常用的注册中心

* Consul
* Zookeeper
* Etcd
* Redis

## 参考资料

[RPC 原理及实现](https://juejin.im/post/44903801044860935)

[序列化技术对比](https://github.com/eishay/jvm-serializers/wiki)

开源框架

| 名称 | 地址 |
| -- | -- | -- | -- |
| 阿里巴巴 Dubbo | https://github.com/apache/dubbo |
| 新浪微博 Motan | https://github.com/weibocom/motan |
| gRPC | github.com/grpc/grpc |
| rpcx | https://github.com/smallnest/rpcx |
| Apache Thrift | https://thrift.apache.org/ |