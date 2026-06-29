一、为什么 Agent 要声明为 abstract 抽象类？

1. 存在未实现的核心抽象方法
Agent 是所有智能体（Platoon、Office、消防队等）的顶层父类，里面定义了 think() 这类所有智能体都必须实现、但不同智能体逻辑完全不同的行为方法，因此将 think() 声明为 abstract 抽象方法。
包含抽象方法的类，语法强制要求类必须用 abstract 修饰。

2. 不允许直接实例化 Agent
Agent 只是通用智能体模板，本身没有独立业务逻辑，不能直接 new Agent() 使用，只能作为父类被子类继承。抽象类天然禁止实例化，符合设计意图。

3. 抽取公共代码，统一规范子类
所有智能体共有的属性、通用工具方法写在 Agent 中复用；抽象方法强制所有子类（Platoon、Office、消防队Agent等）必须重写业务逻辑，约束子类统一行为接口，降低代码维护成本。

二、Tactics.java 的 think() 与 Agent.java 的 think() 关系

1. 职责分层，互不重写，属于分工协作关系

• Agent#think()：智能体顶层入口方法，是整个智能体决策周期的总调度。负责环境感知、数据更新、调用策略模块、执行动作输出，是决策流程的外壳。

• Tactics#think()：策略内部计算逻辑，只封装具体救援规划、任务分配、路径、灭火、救援等业务算法，只处理纯策略计算。

2. 调用从属关系
Agent 的 think() 主流程内部会创建/持有 Tactics 策略对象，主动调用 tactics.think() 获取决策结果；
Tactics 的 think() 不会重写、也不会覆盖父类 Agent 的 think()，二者不属于重写/重载，是上层调度方法调用下层策略计算方法。

3. 接口分离解耦
Agent 只关心“什么时候跑策略”，Tactics 只关心“怎么算策略”，把智能体生命周期和业务算法拆分开，方便替换不同战术。

三、什么是策略模式？本项目哪里体现？

1. 策略模式定义

策略模式属于行为型设计模式：

• 定义统一抽象策略基类，封装一套可互换的算法接口；

• 多个具体策略子类实现不同算法逻辑；

• 主体对象（上下文，此处为Agent）持有策略对象，将算法执行委托给策略类，运行时可无缝切换不同策略，无需修改主体代码，符合开闭原则。

核心：把多变的算法逻辑抽离独立类，和业务主体解耦。

2. 在RoboCup救援项目中的体现

1. 抽象策略基类：Tactics.java
public abstract class Tactics 是顶层抽象策略，定义统一 think() 算法接口，规定所有战术必须实现决策计算逻辑，对应策略模式的Strategy抽象策略。

2. 多个具体策略实现类
DefaultTacticsFireBrigade、消防队/指挥部/队伍专属战术类，继承 Tactics 并重写 think()，分别实现灭火、搜救、人员调度、物资分配等不同算法，对应ConcreteStrategy具体策略。

3. 上下文 Context：Agent（Platoon/消防队Agent）
各类智能体（Platoon、SampleFireBrigade等）作为上下文，内部持有 Tactics 成员变量；在自身 think() 流程中调用 tactics.think() 完成决策。

• 如需更换救援算法，只需要替换 Tactics 子类（比如把默认消防队战术换成自定义搜救战术），Agent 主体代码完全不用修改；

• 新增一套全新救援战术时，只需要新建 Tactics 子类，不改动Agent原有代码，完美契合策略模式开闭原则。

4. 额外补充：Search搜索模块同理
AbstractModule → Search → SampleSearch 也是策略模式变体：Search 抽象搜索策略，SampleSearch 是具体搜索算法，Agent 可切换不同搜索算法，逻辑一致。