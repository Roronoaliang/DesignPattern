## 规格模式

> 规格不是不是一种新的设计模式，而是组合模式的扩展应用。规格模式中使用规格书接口来描述一个完整的、可组合的规格书，它代表的是一个整体，其子类如And规格书、Or规格书、其他具体业务条件的规格书都是一个真实的实现，也就是一个局部。同时从规格书之间的可自由替换的角度来看，它又是策略模式的应用。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fe67euuz7sj20wz0jwta5.jpg)

## 代码示例

* 用户类User

```
public class User {

    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```



* 规格书接口

```
public interface IUserSpecification {
    //候选者是否满足要求
    boolean isSatisfiedBy(User user);
    //and操作
    IUserSpecification and(IUserSpecification spec);
    //or操作
    IUserSpecification or(IUserSpecification spec);
    //not操作
    IUserSpecification not();
}
```

* 抽象组合规格书，将and、or、not操作固定下来，使之不可变

```
public abstract class CompositeSpecification implements  IUserSpecification{
    //是否满足条件由实现类实现
    public abstract  boolean isSatisfiedBy(User user);
    //and操作
    public IUserSpecification and(IUserSpecification spec) {
        return new AndSpecification(this, spec);
    }
    //or操作
    public IUserSpecification or(IUserSpecification spec) {
        return new OrSpecification(this, spec);
    }
    //not操作
    public IUserSpecification not() {
        return new NotSpecification(this);
    }
}
```

* 实现And操作的规格书

```
public class AndSpecification extends CompositeSpecification {

    //传递两个规格书进行and操作
    private IUserSpecification left;
    private IUserSpecification right;

    public AndSpecification(IUserSpecification left, IUserSpecification right) {
        this.left = left;
        this.right = right;
    }

    //进行and运算
    @Override
    public boolean isSatisfiedBy(User user) {
        return left.isSatisfiedBy(user) && right.isSatisfiedBy(user);
    }
}
```

* 实现Or操作的规格书

```
public class OrSpecification extends CompositeSpecification {

    //传递两个规格书
    private IUserSpecification left;
    private IUserSpecification right;

    public OrSpecification(IUserSpecification left, IUserSpecification right) {
        this.left = left;
        this.right = right;
    }

    //进行or操作
    @Override
    public boolean isSatisfiedBy(User user) {
        return left.isSatisfiedBy(user) || right.isSatisfiedBy(user);
    }
}
```

* 实现Not操作的规格书

```
public class NotSpecification extends CompositeSpecification{

    //传递一个规格书
    private IUserSpecification spec;

    public NotSpecification(IUserSpecification spec) {
        this.spec = spec;
    }

    //进行not操作
    @Override
    public boolean isSatisfiedBy(User user) {
        return !spec.isSatisfiedBy(user);
    }
}
```

* 实现按姓名查找的规格书

```
public class UserByNameEqual extends  CompositeSpecification{

    //传递查询条件姓名
    private String name;

    public UserByNameEqual(String name) {
        this.name = name;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getName().equals(name);
    }
}
```

* 实现按年龄条件查找的规格书

```
public class UserByAgeThan extends CompositeSpecification {

    //传递查询条件年龄
    private int age;

    public UserByAgeThan(int age) {
        this.age = age;
    }

    //判断年龄是否大于20
    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getAge() > age;
    }
}
```

* 用户查询类接口定义

```
public interface IUserProvider {
    //根据指定规格书查询用户
    ArrayList<User> findUser(IUserSpecification spec);
}
```

* 用户查询接口实现类

```
public class UserProvider implements IUserProvider {

    //传递用户列表
    private ArrayList<User> users;

    public UserProvider(ArrayList<User> users) {
        this.users = users;
    }

    //根据具体的规格书查找用户
    @Override
    public ArrayList<User> findUser(IUserSpecification spec) {
        ArrayList<User> result = new ArrayList<>();
        for(User u : users) {
            if(spec.isSatisfiedBy(u)){
                result.add(u);
            }
        }
        return result;
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        //mock用户集合
        ArrayList<User> users = mockUser();
        //定义用户查询类
        IUserProvider provider = new UserProvider(users);
        //查询姓名为张三的用户
        ArrayList<User> result = provider.findUser(new UserByNameEqual("张三"));
        //打印结果
        System.out.println("姓名为张三的用户");
        printUser(result);
        //查询年龄大于20的用户
        result = provider.findUser(new UserByAgeThan(20));
        //打印结果
        System.out.println("年龄大于20的用户:");
        printUser(result);
        //查询姓名为张三并且年龄大于30的用户
        result = provider.findUser(new AndSpecification(new UserByNameEqual("张三"), new UserByAgeThan(30)));
        //打印结果
        System.out.println("姓名为张三并且年龄大于30的用户:");
        printUser(result);
        //查询年龄不大于20岁的用户
        result = provider.findUser(new NotSpecification(new UserByAgeThan(20)));
        System.out.println("年龄不大于20岁的用户:");
        printUser(result);
    }

    public static ArrayList<User> mockUser() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("小明", 10));
        users.add(new User("小刚", 20));
        users.add(new User("小红", 30));
        users.add(new User("张三", 33));
        users.add(new User("张三", 18));
        users.add(new User("李四", 23));
        return users;
    }

    private static void printUser(ArrayList<User> result) {
        if (result != null) {
            for (User u : result) {
                System.out.println(u);
            }
        }
    }
}
```

## 使用场景举例

* 多个对象中筛选查找——规格模式的主要作用即对象筛选功能。
* 业务规则不适于放在任何已有实体或值对象中，而且规则的变化和组合会掩盖领域对象的基本含义时
* 实现类似LINQ的语言工具