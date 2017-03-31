## 定义

> 迭代器模式提供一种方法访问一个容器对象中各个元素，而不需要暴露该对象的内部细节。

迭代器模式是魏容器服务的，如Collection集合类型，Map类型。但迭代器模式基本上是一个是没落的模式，除非是产品性质的开发，否则不会在项目中使用。


## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fd78yr35lvj20i308cdg3)

* Iterator：抽象迭代器，负责定义访问和遍历元素的接口，一般有固定的3个方法：first()方法用来获取第一个元素；next()方法用来访问下一个元素；hasNext()方法用来判断是否已经访问到末尾。
* ConcreteIterator：具体的迭代器，完成容器元素的遍历。
* Collection：抽象容器，负责提供创建具体迭代器角色的接口。
* ConcreteIterator：具体容器类。

## 代码示例

* 抽象迭代器

```
public interface Iterator {
    Object first();
    Object next();
    boolean hasNext();
    boolean remove();
}
```

* 具体迭代器

```
public class ConcreteIterator impleements Iterator {
    private Vector vector = new Vector();
    //定义当前游标
    public int cursor = 0;
    public ConcreteIterator(Vector vector) {
        this.vector = vector;
    }
    public Object first() {
        return this.vectr.size() > 0 ? this.vector.get(0) : null;
    }
    public boolean hasNext() {
        if(this.cursor == this.vector.size()) {
            return false;
        } else {
            return true;
        }
    }
    public Object next() {
        Object result = null;
        if(this.hasNext()) {
            result = this.vector.get(this.cursor++);
        } else {
            result = null;
        }
        return result;
    }
    public boolean remove() {
        this.vector.remove(this.cursor);
        return true;
    }
}
```

* 抽象容器

```
public interface Collection {
    void add(Object object);
    void remove(Object object);
    Iterator iterator();
}
```

* 具体容器

```
public class ConcreteCollection implements Collection {
    private Vector = new Vector();
    public void add(Object object) {
        this.vector.add(object);
    }
    public Iterator iterator() {
        return new ConcreteIterator(this.vector);
    }
    public void remove(Object object) {
        this.remove(object);
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        Collection c = new ConcreteCollection();
        c.add("abc");
        c.add("def");
        c.add("ghi");
        Iterator iterator = c.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
```