# CodeReading6 阅读报告
## 问题1：Agent 和 Server 之间用的是什么协议？数据是什么格式？
1. 传输层协议：**TCP协议**
   rcrs-server 默认在27931端口开启TCP监听，Agent通过TCP长连接和仿真服务端通信。
2. 数据序列化格式：**Protobuf（Protocol Buffers）**
   rescuecore2 内部预先定义proto文件，编译生成 RCRSProto.java，所有指令、感知消息都序列化为Protobuf二进制数据在网络传输。

整体通信流程：
Agent调用think()生成指令 → 对象序列化为Protobuf二进制 → TCP发送 → Kernel接收解析 → Simulator仿真世界更新。

## 问题2：数据为什么要序列化？直接传Java对象行不行？
### 序列化的作用
1. 将内存中的对象转为二进制字节流，支持网络传输、持久化存储；
2. Protobuf二进制体积小，网络传输开销低，解析速度快；
3. 跨语言兼容，服务端、客户端即便不用Java语言，也能解析数据。

### 不能直接传输Java对象
1. Java原生序列化`ObjectOutputStream`是Java语言专属，跨语言无法解析；
2. Java对象包含JVM内存地址、依赖虚拟机环境，网络另一端JVM无法直接还原；
3. Java默认序列化产生的数据冗余大，传输效率差；
4. 两端类版本不一致时，极易出现反序列化失败异常。