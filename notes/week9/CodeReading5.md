根据提供的 start.sh 脚本，服务器的启动顺序如下：
1.初始化环境与信号处理
设置 trap 捕获 INT 信号（如 Ctrl+C），确保退出时执行清理操作（运行 kill.sh）
加载 functions.sh 中的函数
2.处理命令行参数
前置准备
删除旧日志文件
执行 kill.sh 确保之前的进程已终止
3.核心组件启动（按顺序）
startKernel --nomenu：启动内核（Kernel）
startSims：启动仿真器（Sims）
startViewer：启动查看器（Viewer）
startViewerEventLogger：启动查看器事件日志器（ViewerEventLogger）
4.后续操作
提示用户 "Start your agents"（启动代理程序）
等待内核日志中出现 "Kernel has shut down" 消息（超时 30 秒）
终止所有启动的进程并再次执行 kill.sh 清理



包名及作用分析
所有文件均属于同一个包： sample_team.module.complex
包内各类的作用
1. SampleAmbulanceTargetAllocator
   继承：AmbulanceTargetAllocator
   作用：实现救护队的目标分配逻辑，负责为救护队分配需要救援的目标（如被困平民）。
2. SampleBuildingDetector
   继承：BuildingDetector
   作用：实现建筑物检测逻辑，主要用于识别需要关注的建筑物（如着火建筑），通常结合聚类算法（Clustering）优先处理本集群内的目标。
3. SampleFireTargetAllocator
   继承：FireTargetAllocator
   作用：实现消防队的目标分配逻辑，负责为消防队分配灭火目标（如着火建筑）。
4. SampleHumanDetector
   继承：HumanDetector
   作用：实现人员检测逻辑，用于识别需要救援的人员（如被困平民），会过滤无效目标（如已死亡、已在避难所的人员），并优先处理本集群内的目标。
5. SamplePoliceTargetAllocator
   继承：PoliceTargetAllocator
   作用：实现警察部队的目标分配逻辑，负责为警察分配任务目标（如需要疏通的道路或维持秩序的区域）。
6. SampleRoadDetector
   继承：RoadDetector
   作用：实现道路检测逻辑，用于识别需要清理的道路（如被堵塞的道路），结合路径规划（PathPlanning）和聚类算法，优先处理本集群内的关键区域（如避难所、加油站附近道路）。
7. SampleSearch
   继承：Search
   作用：实现搜索任务逻辑，负责规划搜索路径，搜索未探查的建筑物（排除避难所），优先处理本集群内的目标，结合路径规划算法确定最优搜索顺序。
   包的整体作用
   sample_team.module.complex 包是 ADF（Agent Development Framework）框架中复杂模块的实现，为不同类型的智能体（救护队、消防队、警察部队）提供核心任务逻辑，包括目标检测、目标分配、路径规划、区域搜索等，是智能体执行具体救援任务的关键组件。