## 定义

> 使多个对象都有机会处理请求，从而避免了请求的发送者和接收者之间的耦合关系。将这些对象连成一条链，并沿着这条链传递该请求，直到有对象处理它为止。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fd1hcnrbixj20hb0aeglu)

## 代码示例

* 抽象代理者

```
public abstract class Handler {
    //每个Handler都持有下一个处理者的引用
    private Handler nextHandler;
    public final Response handleMessage(Request request) {
        Response response = null;
        //判断请求是否由当前处理器处理
        if(this.getHandlerLevel().equals(request.getRequestLevel())) {
            response = this.echo(request);
        } else {
            //判断有无后续处理器
            if(this.nextHandler != null) {
                response = this.nextHandler.handleMessage(request);
            } else {
                //没有相应的处理者
            }
        }
        return response;
    }

    //设置后续处理者
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }
    //获取每个处理者的处理级别
    protected abstract Level getHandlerLevel();
    //由每个具体的处理者实现处理任务
    protected abstract Response echo(Request request);
}
```

抽象处理器主要有三个职责：

* 定义一个对外开放处理请求的方法handleMessage
* 设置下一个处理器的方法setNext
* 定义每个处理器能够处理的请求类型或级别getHandlerLevel和具体的处理任务方法echo。

* 具体处理者1

```
public class ConcreteHandler1 extends Handler {
    protected Response echo(Request requst) {
    //完成处理逻辑，返回处理结果
    return null;
    }
    //返回处理级别
    protected Level getHandlerLevel() {
        return Level.FIRST;
    }
}
```

* 具体处理者2

```
public class ConcreteHandler2 extends Handler {
    protected Response echo(Request requst) {
    return null;
    }
    //返回处理级别
    protected Level getHandlerLevel() {
        return Level.SECOND;
    }
}
```

* 具体处理者3

```
public class ConcreteHandler3 extends Handler {
    protected Response echo(Request requst) {
    return null;
    }
    //返回处理级别
    protected Level getHandlerLevel() {
        return Level.THIRD;
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        //声明所有处理器并设置处理顺序
        Handler h1 = new ConcreteHandler1();
        Handler h2 = new ConcreteHandler2();
        Handler h3 = new ConcreteHandler3();
        h1.setNext(h2);
        h2.setNext(h3);
        //处理请求
        Requst request = new Request();
        request.setRequestLevel(2);
        Response response = h1.handleMessage(request);
    }
}
```

## 优缺点

优点

* 使用责任链模式可以将请求和处理分开，请求者可以不需要知道谁来处理请求，实现请求方和处理方的解耦，提高灵活性。

缺点：

* 责任链中请求是从链头遍历到链尾，特别是链比较长的时候，性能会大打折扣。一般会在Handler中设置最大的节点数避免处理链过长，破坏系统的性能。
* 使用责任链模式会导致调试不方便，调试逻辑复杂。