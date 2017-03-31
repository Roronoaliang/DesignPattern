## 定义

> 将对象组合成树形结构以表示“部分-整体”的层次结构，使得用户对单个对象和组合对象的使用具有一致性。

组合模式又叫合成模式、部分-整体模式，主要是用来描述部分与整体的关系。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fd9e4iiirlj20nr0bl74r)

* Component：抽象构件角色，用来定义参与组合对象的共有方法和属性，定义一些默认的行为和属性等。
* Leaf：叶子构件，是遍历的最小单位，其下再没有其他分支。
* Composite：复合构件，也称树枝构件，其作用是组合树枝节点和叶子节点形成一个树形结构。

## 代码示例

* 抽象构件

```
public abstract class Component {
    //共享属性
    protected String shareAttribute;
    //个体和整体共享行为
    public void shareMethod() {
        //doSomething
    }
}
```

* 树枝构件

```
public class Composite extends Component
    //存放树枝或叶子的容器
    private ArrayList<Component> components = new ArrayList<Component>();
    //增加叶子或树枝
    public void add(Component c) {
        this.components.add(c);
    }
    //删除叶子或树枝
    public void remove(Component c) {
        this.components.remove(c);
    }
    //获取分支下所有的叶子节点和分支节点
    public ArrayList<Component> getChildren() {
        return this.components;
    }
}
```

* 叶子构件

```
public class Leaf extends Component {

}
```

* 场景类

```
//场景类负责利用上述结构来建立树状结构，并通过递归的方式来遍历整棵树
public class Client {
    public static void main(String[] args) {
        Composite root = new Composite();
        Composite branch = new Composite();
        Leaf leaf = new Leaf();
        root.add(branch);
        root.add(leaf);
        Client.display(root);
    }
    public static void display(Composite root) {
        for(Component c:root.getChildren()) {
            if(c instanceof Leaf) {
                c.shareMethod();
            } else {
                c.shareMethod();
                Client.display((Composite)c); //递归调用
            }
        }
    }
}
```

## 优缺点

优点：

* 组合模式中所有的节点类型都是Component，整体和部分对于调用者来说是一致的，不必关心调用的是整体还是部分。
* 可以自由增加节点。

缺点：

* 在遍历时需要进行强制类型转换，违背了依赖倒置原则。

## 使用场景举例

* 维护和展示部分-整体关系的场景，如属性菜单、文件与文件夹管理。
* 从一个整体中能够独立出部分的模块或功能的场景。