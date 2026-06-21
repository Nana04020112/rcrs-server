问题1：SampleFireBrigade.java 的 getTargets() 方法作用
该方法会返回当前场景中所有待救援的人类对象集合（容器/列表），封装目标数据，供外部代码遍历、读取每个人的位置、状态等信息，是获取救援目标的统一入口。

问题2：for (Human next : getTargets()) 语法名称
增强for循环（foreach循环），专门用于遍历数组、集合类容器，无需手动操作索引，简化遍历代码。