## 定义

> 在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，以后可将该对象恢复到原先保存的状态。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fdd68e82n2j20oe0520sw)

* Originator：发起人角色，记录当前时刻的内部状态，负责定义哪些属于备份范围的状态，负责创建和恢复备忘录数据。
* Memento：备忘录角色，负责存储Originator发起人对象的内部状态，在需要的时候提供发起人需要的内部状态。
* Caretaker：备忘录管理员角色，对备忘录进行管理、保存和恢复。

## 代码示例

* 发起人角色

```
pulic class Originator {
    //内部状态
    private String state = "原始状态";
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    //创建备忘录
    public Memeto createMemento() {
        return new Memento(this.state);
    }
    //恢复备忘录
    public void restoreMemento(Memento m) {
        this.setState(m.getState());
    }
}
```

* 备忘录角色

```
public class Memento {
    //需要备忘录保存的状态
    private String state = "";
    public Memento(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
```

* 备忘录管理员角色

```
public class Caretaker {
    private Memento m;
    public Memento getMemento() {
        return m;
    }
    public void setMemento(Memento m) {
        this.m = m;
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        //创建备忘录
        caretaker.setMemento(originator.createMemento());
        //doSomething...
        //恢复备忘录
        originator.restoreMemento(caretaker.getMemento());
    }
}
```

## 使用场景举例

* 需要保存和恢复数据相关状态的场景
* 提供一个可回滚的操作，如撤销至上一步状态的操作。
* 数据库连接的事务管理。

## 注意事项

* 备忘录创建出来需要主动的管理其生命周期，建立就要使用，不再使用要立刻删除其引用，等待垃圾回收期进行回收处理。
* 不要在频繁建立备份的场景中使用备忘录模式，比如一个循环中，因为这样会导致控制不了备忘录建立的对象数量，并且大对象的建立是要消耗资源的，从而影响系统性能。

## 备忘录模式扩展

### clone方式的备忘录

clone方式实现备忘录可以精简代码，将备忘录角色和管理备忘录角色省去，由发起人自主进行备份和恢复。但是该种方式需要考虑深克隆和浅克隆的问题，在复杂的场景下会导致程序逻辑混乱，因此，Clone方式的备忘录模式只适用于简单单一的场景。

```
public class Originator implements Cloneable {
    //定义自身引用保存备份的状态
    private Originator backup;
    private String state = "";
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    //创建备忘录
    public void createMemento() {
        this.backup = this.clone();
    }
    //恢复备忘录
    public void restoreMemento() {
        if(this.backup != null) {
            this.setState(this.backup.getState());
        }
    }
    //覆写克隆方法
    @Override
    protected Originator clone() {
        try {
            return (Originator)super.cloen();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

### 多状态的备忘录模式

保存一个对象的多个状态，有多种处理方式，如使用Clone的方法、将DTO对象写到临时表中、使用AOP框架运行时决定保存哪些状态等。以下是一种借助HashMap实现的全状态备份方式：

![](https://ws1.sinaimg.cn/large/bc18b842gy1fdd7fvyv3ij20rx0bs754)

* 发起人角色

```
public class Originator {
    private String state1;
    private String state2;
    private String state3;
    public Memento createMemento() {
        return new Memento(BeanUtils.backupProp(this));
    }
    public void restoreMemento(Memento m) {
        BeanUtils.restoreProp(this, m.getStateMap());
    }
    //省略getter/settter方法
}
```

* BeanUtils工具类

```
public class BeanUtils {
    public static HashMap<String, Object> backupProp(Object bean) {
        HashMap<String,Object> result = new HashMap<>();
        try {
            //获取Bean描述
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            //获取属性描述
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            //遍历所有属性
            for(PropertyDescriptor des : descriptors) {
                String fieldName = des.getName();
                Method getter = des.getReadMethod();
                Object fieldVal = getter.invoker(bean, new Object[]{});
                if(!fieldName.equalsIgnoreCase('class')) {
                    result.put(fieldName, fieldVal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void restoreProp(Object bean, HashMap<String, Object> propMap) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptro[] descriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor des : descriptors) {
                String fieldName = des.getName();
                if(propMap.containsKey(fieldName)) {
                    Method setter = des.getWriteMethod();
                    setter.invoker(bean, new Object[]{propMap.get(fieldName)});
                }
            }
        } catch (Exception e） {
            e.printStackTrace();
        }
    }
}
```

* 备忘录角色

```
public class Memento {
    private HashMap<String,Object> stateMap;
    public Memento(HashMap<String,Object> map) {
        this.stateMap = map;
    }
    public HashMap<String,Object> getStateMap() {
        return stateMap;
    }
    public void setStateMap(HashMap<String,Object> stateMap) {
        this.stateMap = stateMap;
    }
}
```

### 多备份的备忘录模式

前面的备忘录模式不管是保存一个状态还是多个状态，对于一个确定的发起人，都永远只有一份备份，但是很多场景下我们需要同时保存多份备份，如要保存不同时间下的备份。这时只需要在前面的设计基础上为备忘录管理员加入检查点即可实现保存多份备份。

* 备忘录管理员

```
public class Caretaker {
    private HashMap<String,Memento> memMap = new HashMap<>();
    public Memento getMemento(String id) {
        return memMap.get(id);
    }
    public void setMemento(String id, Memento mem) {
        this.memMap.put(id,mem);
    }
}
```

### 安全备份

上述几种扩展以及原始的备忘录模式中，备份的数据都能通过高层模块直接获取和修改，而在系统管理上，一份备份的数据应该是完整且不允许外界直接对备份进行修改的。为了保证备份的阅读权限只有完成备份的发起人能执行，我们可以将备忘录角色封装到发起人内部，保证外界通过常规方法无法篡改备份数据。

* 发起人角色

```
public class Originator {
    private String state;
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public IMemento createMemento() {
        return new Memento(this.state);
    }
    public void restoreMemento(IMemento men) {
        this.setState(((Memento)men).getState());
    }
    //将备忘录角色封装到发起人内部
    private class Memento implements IMemento {
        private String state;
        private Memento(String state) {
            this.state = state;
        }
        private String getState() {
            return state;
        }
        private void setState(String state) {
            this.state = state;
        }
    }
}
```

* 备忘录空接口

```
public interface IMemento {

}
```

* 备忘录管理者

```
public class Caretaker {
    private IMemento memento;
    public IMemento getMemento() {
        return memento;
    }
    public void setMemento(IMemento memento) {
        this.memento = memento;
    }
}
```

这种情况下，内置类Memento全都是private的访问权限，只有发起人角色能访问到其内部属性，而外部角色是通过IMemento空接口得到公共的访问权限。这也是一种设计方法——**双接口设计**：在考虑对象的安全问题时，可以提供两个接口，一个是业务的正常接口，实现必要的业务逻辑，称之为宽接口，另一个则是一个空接口，不提供任何操纵数据的方法，目的是提供给子系统外的模块访问，称之为窄接口。