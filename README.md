# POS in Shell

The demo shows a simple POS system with command line interface. Currently it implements three commands which you can see using the `help` command.

```shell
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.5.7)
 
shell:>help
AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        history: Display or save the history of previously run commands
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

Pos Command
        a: Add a Product to Cart
        n: New Cart
        p: List Products
```

Everytime a customer come to make a purchase, use `n` to create a new cart and then use `a ${productid} ${amount}` to add a product to the cart.

Please make the POS system robust and fully functional by implementing more commands, for instance, print/empty/modify cart.

Implementing a PosDB with real database is very much welcome. 

Please elaborate your understanding in layered systems via this homework in your README.md.

## 编译说明  

为了支持```asciinema```工具，demo是在`Docker`中运行的。以下简要介绍如何生成这个demo。

```shell
#第一步编译:
./mvnw package
#第二步生成Docker镜像
docker build -t aw02:v1 .
#第三步运行对应容器
docker run -ti aw02:v1 
```

## 理解  

简单来讲，这是一个使用`spring-boot`框架的命令行应用程序。整体上而言，这个程序体现了三层设计。

具体而言，三层就是指表示层、业务处理层以及数据访问层。

`PosCommand`类就是表示层，这里利用了`spring-boot`框架的相关组件，高效率的定义了一个命令行界面以及对应命令所调用的方法。

业务处理层的具体实现在这里是`PosServiceImp`类，这一层需要完成的任务是，利用从表示层中收集的信息，处理用户产生的请求，同时添加，修改数据访问层中的数据。

数据访问层的具体实现则是`PosInMemoryDB`。这里用于集中管理应用程序所处理的信息。

所以业务处理层实际上负责了表示层与数据访问层的通信。

这样设计的最主要目的并不是为了开发这样简单的应用，而是将大型应用程序中的逻辑功能分离。例如这里就可以将功能对应分布到3个服务器上：
1. Web服务器，将相关内容生成网页并提供给用户
2. 应用程序服务器，处理业务逻辑，查询库存数据库，修改用户对应购物车数据库
3. 数据库服务器，只负责管理数据

实际上这样的简单程序使用三层架构一定程度上增加了工作量——在表示层添加的功能有时会导致业务处理层和数据访问层均产生修改（为了保持三层架构）。
