## 1. Collection unexploredBuildings 为什么用Collection而不是List？
1. 面向顶层抽象编程，降低代码耦合。Collection是List、Set、Queue的父接口，仅定义集合通用操作；
2. 无下标操作依赖：业务仅需要存放、遍历一组元素，不需要List独有的按索引存取功能；
3. 底层实现灵活：后续可自由切换ArrayList、HashSet等实现类，不用修改变量类型；
4. 语义合理：未探索建筑仅代表一组元素，无“有序、按下标访问”的业务需求，使用通用Collection更贴合语义。

## 2. EnumSet.of(...)作用与普通Set对比优势
### 作用
EnumSet.of()是静态工厂方法，快速批量创建仅存储指定枚举常量的EnumSet实例，简化枚举集合初始化。

### 相比普通HashSet/TreeSet的好处
1. 性能极高：底层使用位掩码存储，增删查操作O(1)，内存占用极小；
2. 编译期类型安全：只能存放对应枚举类型，不会存入其他类型对象；
3. 天然有序：自动按照枚举类定义常量的顺序排列；
4. 无哈希冲突：不需要计算hashCode，无哈希碰撞开销；
5. 专属枚举工具API：支持allOf、noneOf、range等普通Set不具备的便捷方法。