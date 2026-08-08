Main.java
└── 解析命令行参数
↓
AgentLauncher.java
├── initConnector() → 注册6种Connector连接器
└── start()
└── TCPComponentLauncher → Connector.connect()
└── 为每一个Connector启动独立线程
↓
Agent.java（每个代理实例）
├── postConnect()【连接仿真服务器成功后回调，仅执行1次】
│   ├─ 初始化 WorldInfo / ScenarioInfo / AgentInfo
│   └─ 设置运行模式：PRECOMPUTE / NON_PRECOMPUTE / PRECOMPUTED
└── processSense(KASense)【每一个仿真tick被回调】
├─ 更新worldInfo世界信息
├─ 调用 think(time, changed, heard)
│   ├─ 第1次tick：初始化CommunicationModule通信模块
│   ├─ 订阅通信频道
│   └─ 调用抽象 think() 方法（用户编写代理决策逻辑）
└── send(Command) → 将动作指令发送给rcrs‑server仿真服务器

2. initConnector()注册的6种Connector与对应角色
连接器	                 仿真角色
AmbulanceTeamConnector	救护救援队（移动救援人员，搬运伤员）
FireBrigadeConnector	消防救援队（灭火、清理废墟）
PoliceForceConnector	警察救援队（清理道路障碍物）
AmbulanceCentreConnector	救护中心（固定建筑，救护队伍的基地）
FireStationConnector	消防站（固定建筑，消防队伍的基地）
PoliceOfficeConnector	警察总局（固定建筑，警察队伍的基地）	

3. postConnect() 和 think()谁先被调用？为什么？
postConnect()优先被调用。
• 原因：postConnect()在TCP连接建立成功之后立刻执行，只执行一次，用于完成代理一次性初始化（加载世界基础信息、设置运行模式）。
• think()是在每一轮仿真tick的processSense()内部才会调用；必须等连接完成、基础信息初始化完毕，仿真器下发感知数据之后，才会进入think决策循环。
• 执行时序：建立TCP连接 → postConnect() → 收到仿真感知 → processSense() → 调用think()。


4. 如果think()内部抛出异常，会发生什么？（参考Agent.java:184）

（1). think()运行在Agent独立工作线程内部，外层有try‑catch捕获异常。
（2). 异常会被框架捕获，打印错误堆栈日志。
（3).当前这个Agent线程不会直接崩溃退出，跳过本次tick剩余逻辑，直接进入下一轮仿真tick。
（4).仅保护单代理线程，不会影响其他Agent、也不会终止整个仿真服务器；但是本次tick的决策动作全部丢失，代理本轮不会向服务器发送任何动作指令。