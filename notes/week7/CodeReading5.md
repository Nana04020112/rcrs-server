int connectedCount = this.connectors.stream().mapToInt(Connector::getCountConnected).sum();
ConsoleOutput.finish("Done connecting to server (" + connectedCount + " agent" + String.valueOf(connectedCount > 1 ? 's' : "") + ")");
if (this.config.getBooleanValue("adf.launcher.precompute", false)) {
System.exit(0);
1. connectors.stream()
   将存储Connector对象的集合转为Stream流，开启流式遍历，逐个处理集合内每一个Connector实例。

2. .map(connector -> new Thread(() -> {...}))
   map是流转换操作：遍历每一个Connector，为每一个Connector创建一个对应的Thread线程对象；
   内层Lambda表达式作为线程运行任务，线程启动后会执行Connector连接服务端的逻辑。

所有Connector线程尝试和Server建立连接之后
1. 统计一共有多少Agent成功连上仿真服务器；
2. 在控制台打印连接完成的汇总信息；
3. 判断预计算模式开关：
◦ 预计算模式开启：连接建立完成就直接退出程序（只做初始化连接，不参与后续仿真运行）；
◦ 预计算关闭：程序保留运行，各个Agent持续参与救援仿真任务。


问题2：为什么要给每个Connector启动一个独立线程？串行连接会有什么问题？

使用独立线程的原因

1. RCRS仿真场景要求多个Agent同时运行，每个Agent依靠Connector和服务端通信，天然需要并发执行；

2. 与Server建立网络连接属于阻塞IO操作，多线程可以让各个Connector的连接过程互不干扰、并发执行；

3. 创建独立线程后，可以统一管理所有连接任务，配合join()实现主线程等待所有Agent通信流程完成。

串行连接存在的问题

1. 执行效率极低：必须等待前一个Connector完整完成连接流程后，才能执行下一个，总耗时随Connector数量线性增长；

2. 违背RCRS业务需求：多个Agent无法并行接入服务器，失去多智能体同时仿真的核心特性；

3. 容易发生整体阻塞：若其中某个Connector出现网络超时、长时间阻塞，后续所有Connector的连接任务都会被卡住。