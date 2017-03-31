## 定义

> 给定一门语言，定义它的文法的一种表示，并定义一个解释器，该解释器使用该表示来解释语言中的句子。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fdix3luc5fj20lh0ekaal)

* Context：环境角色，一般用来存放非终结符表达式或暂存运算结果。
* AbstractExpression：抽象解释器，定义具体解释器的共有特征和解释方法interpret()。
* TerminalExpression：终结符表达式，实现与文法中的元素相关联的解释操作。
* NonterminalExpression：非终结符表达式，每条文法规则都对应一个非终结符表达式。

## 代码示例

* 抽象表达式

```
public abstract class Expression {
    public abstract int interpreter(HashMap<String, Interger> var);
}
```

* 终结符表达式解析器

```
public class VarExpression extends Expression {
    private String key;
    public VarExpression(String key) {
        this.key = key;
    }
    pubublic int interpreter(HashMap<String, Interger> var) {
        return var.get(this.key);
    }
}
```

* 抽象非终结符解析器

```
public abstract class SymbolExpression extends Expression {
    protected Expression left; //左侧的值
    protected Expression right; //右侧的值
    public SymbolExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
```

* 加法解析器

```
public class AddExpression extend SymbolExpression {
    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }
    //加法解析器解析的规则
    public int interpreter(HashMap<String, Interger> var) {
        return super.left.interpreter(var) + super.right.interpreter(var);
    }
}
```

* 减法解析器

```
public class SubExpression extends SymbolExpression {
    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }
    public int interpreter(HashMap<String, Interger> var) {
        return super.left.interpreter(var) - super.right.interpreter(var);
    }
}
```

* 封装解析器

```
public class Calculator {
    private Expression expression;
    public Calculator(String expStr) {
        //用栈来实现运算的先后顺序
        Stack<Expression> stack = new Stack<Expression>():
        char[] charArray = expStr.toCharArray();
        Expression left = null;
        Expression right = null;
        for(int i = 0; i < charArray.length; i++） {
            switch(charArray[i]) {
                case '+':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(charArray[++i]));
                    stack.push(new AddExprssion(left, right));
                    break;
                case '-':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(charArray[++i}));
                    stack.push(new SubExpression(left, right);
                    break;
                default:
                    stack.push(new VarExpression(String.valueOf(charArray[i])));
            }
        }
            this.expression = stack.pop(); //保存运算结果
    }
    //执行运算
    public int run(HashMap<String, Integer> var) {
        return this.expression.interpreter(var);
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) throws IOException {
        String expStr = getExpStr();
        HashMap<String, Integer> var = getValue(expStr);
        Calculator cal = new Calculator(expStr);
        //打印结果
        System.out.println(expStr + " = " + cal.run(val));
    }

    //获取表达式
    public static String getExpStr() throws IOException {
        System.out.print("请输入表达式： ")
        return (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }
    //把数值存入map中
    public static HashMap<String, Integer> getValue(String expStr) throws IOException {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for(char c: expStr.toCharArray()) {
            if(ch != '+' && ch != '-') {
                if(!map.containsKey(String.valueOf(ch))) {
                    String in = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                    map.put(String.valueOf(ch), Integer.valueOf(in));
                }
            }
        }
        return map;
    }
}
```

## 优缺点

优点：

* 解释器模式是一个简单的语法分析工具，能够在运行时动态指定表达式，具有很好的扩展性，需要扩展新语法增加非终结符时无需修改已有解析器类。

缺点：

* 非终结符类型过多时会引起类膨胀。
* 解释器模式采用递归调用的方式，会导致调试困难和效率低下。