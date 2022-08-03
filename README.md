##  东宝商城

单体项目---》微服务---》服务网格



## 1:**项目结构**

```java
代码模块介绍
msb-dongbao-mall-parent     父项目
--msb-dongbao-common   公共包
	--msb-dongbao-common-base 公共基础类
	--msb-dongbao-common-util 工具类
--msb-dongbao-api 业务模块接口层
	--msb-dongbao-oms-api 订单中心接口
	--msb-dongbao-pms-api 商品中心接口
	--msb-dongbao-ums-api 用户中心接口
 	--msb-dongbao-pay-api 支付中心接口
	--msb-dongbao-cart-api 购物车接口
	--msb-dongbao-dictionary-api 基础字典接口
	--msb-dongbao-sms-api 优惠中心接口
	--msb-dongbao-cms-api 内容中心接口
--msb-dongbao-service 业务模块实现层
	--msb-dongbao-oms 订单中心模块实现
	--msb-dongbao-pms 商品中心模块实现
	--msb-dongbao-ums 用户中心模块实现
	--msb-dongbao-pay 支付中心模块实现
	--msb-dongbao-cart 购物车模块实现
	--msb-dongbao-dictionary 基础字典模块实现
	--msb-dongbao-sms 优惠中心模块实现
	--msb-dongbao-cms 内容中心模块实现
--msb-dongbao-application-web应用模块
	--msb-dongbao-manager-web 后台管理应用
	--msb-dongbao-portal-web 商城门户网站
--msb-dongbao-job 定时任务模块
--msb-dongbao-generator 代码生成器
```

## 2:数据库设计

**【1】ums_member**

```sql
CREATE TABLE `ums_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `icon` varchar(500) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nick_name` varchar(200) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '1' COMMENT '0->1->',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_name` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8
```









【2】**时间由代码控制**

```java
@Component
public class MyHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("添加插入时间");
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("添加更新时间");
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
```

**在对应的实体类中设置 @TableField(fill = FieldFill.INSERT/UPDATE)**

```java
@TableField(fill = FieldFill.INSERT)
private Date gmtCreate;

@TableField(fill = FieldFill.UPDATE)
private Date gmtModified;
```

## 3：msb-dongbao-generator 

**代码生成器**

使用mybatis-plus 逆向工程

```java
public static void main(String[] args) {
    // 构建一个代码生成对象
    AutoGenerator mpg = new AutoGenerator();

    // 1. 全局配置
    GlobalConfig gc = new GlobalConfig();

    String separator = File.separator;

    gc.setOutputDir("E:\\ssm-project\\dongbao-project\\msb-dongbao-generator\\src\\main\\java");
    gc.setAuthor("悟空");
    gc.setOpen(false);//打开目录
    gc.setFileOverride(true);//是否覆盖
    gc.setServiceName("%sService");//去Service的I前缀。
    gc.setIdType(IdType.ID_WORKER);
    gc.setDateType(DateType.ONLY_DATE);
    gc.setSwagger2(false);

    mpg.setGlobalConfig(gc);

    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:mysql://192.168.11.129:3307/dongbao_project?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("root");
    dsc.setDbType(DbType.MYSQL);

    mpg.setDataSource(dsc);

    // 包设置
    PackageConfig pc = new PackageConfig();

    pc.setParent("com.msb.msbdongbaoums");
    pc.setEntity("entity");
    pc.setMapper("mapper");
    pc.setController("controller");

    mpg.setPackageInfo(pc);

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setInclude("ums_member");//表名
    strategy.setNaming(NamingStrategy.underline_to_camel);// 下划线转他驼峰
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);// 列 下划线转脱发
    strategy.setEntityLombokModel(true);//lombok 开启
    strategy.setLogicDeleteFieldName("deleted");

    // 自动填充
    TableFill gmtCreate = new TableFill("create_time",FieldFill.INSERT);
    TableFill gmtModify = new TableFill("update_time", FieldFill.INSERT_UPDATE);
    ArrayList<TableFill> tableFills = new ArrayList<TableFill>();
    tableFills.add(gmtCreate);
    tableFills.add(gmtModify);

    strategy.setTableFillList(tableFills);

    //乐观锁
    strategy.setVersionFieldName("version");

    // restcontroller
    strategy.setRestControllerStyle(true);
    strategy.setControllerMappingHyphenStyle(true);// localhost:xxx/hello_2

    mpg.setStrategy(strategy);

    mpg.execute();
}
```



## 4：注册的接口

**数据库标准**

- 【强制】表必备三字段：id, gmt_create, gmt_modified

- 更新时间的默认设置，不要让数据库来控制，用代码去控制

**时间由代码控制**

```java
@Component
public class MyHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("添加插入时间");
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("添加更新时间");
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
```

**在对应的实体类中设置 @TableField(fill = FieldFill.INSERT/UPDATE)**

```java
@TableField(fill = FieldFill.INSERT)
private Date gmtCreate;

@TableField(fill = FieldFill.UPDATE)
private Date gmtModified;
```

## 5：统一返回值

前后端分离，后端接口，一般为：

```java
{
    "code":状态码,
    "msg":"信息提示",
    "data":
}
```





## 6：参数校验

```java
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
            <version>2.4.1</version>
        </dependency>
```



## 7：统一异常



## 8：JWT

> 引入依赖

```java
<!--jwt-->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.7.0</version>
</dependency>
```



在以后的请求中，不需要重复输入用户名和密码。

#### 1、**token**

```java
token：eyJhbGciOiJIUzI1NiJ9					   (base64(header))
    .eyJzdWIiOiLmgp_nqboifQ						(base64(payload载荷，信息用户名，用户id))
    .vLKuUH92GQ7Q_XroS9A2o9MpLrG9CJWqIBFbmD69vVY（签名）（  （payload,盐（secret））
解析Token：悟空
```

#### 2、**加密解密的知识点**

base64是一种编译方式，不是一种加密方式。只能算是一种编码解码的工具。

md5是一种散列算法（md5），可以通过彩虹表破解，不能反解。 

散列算法：md5，sha系列

对称加密：加解密用的是同一秘钥，DES，3DES，ADES2

非对称加密：公钥加密 私钥解，私钥加密，公钥解。



#### 3、**token延期**

【方案一】：每次请求都会返回一个新的token，老的token让他自动过期，牺牲了及时性，换了空间。

![image-20220801201540257](image-20220801201540257.png)





 【方案二】用户第一次请求的时候设置一个无限期的token，存到redis缓存中，每次请求，先去reids缓存中查找，然后每查找一次，就增加一次缓存时间。

![image-20220801201823662](image-20220801201823662.png)

【方案三】access_token refresh_token





## 9：Token鉴权、、SSO单点登录

> 使用annotation（注解）方式实现token 鉴权，使用自定义注解的方式，将身份拦截器作用在注解注解上，简化开发。
>
> SSO单点登录：
>
> 1、我在一个设备已经登录了 ，另一个设备 强制下线。
>
> 2、我在一个系统上登录过了，在另一个系统就不用在登录了，举例：淘宝、天猫

**a）: ** 创建一个自定义注解 TokenCheck

```java
@Target(ElementType.METHOD)  //作用在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenCheck {

    //是否校验tokenn
    boolean required() default true;

}
```

**b）: ** 创建一个拦截器，用来拦截所有的用户请求 AuthInterceptor

```java
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("拦截器进入");
        //从请求头里获取token
        String token = request.getHeader("token");

        if (token.equals("")) {
            throw new TokenException("token 为空");
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(TokenCheck.class)) {
            TokenCheck annotation = method.getAnnotation(TokenCheck.class);
            //如果为 true
            if (annotation.required()) {
                //校验token
                try {
                    JwtUtil.parseToken(token);
                    return true;
                }catch (Exception e) {
                   throw new TokenException("token 异常");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
```

**c）: ** 将拦截器注册到容器中，交由Spring管理 InterceptorConfig

```java
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/ums-member/login");
    }

    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
}
```

**d）: ** 将自定义注解到方法上，此时token拦截器生效，当用户请求这个方法路径时候，会自动判断是否带有token

```java
/**
 * 更新信息
 * @param umsMember
 * @return
 */
@PostMapping("/update")
@TokenCheck
public ResultWrapper update(@RequestBody UmsMember umsMember) {
    System.out.println("/ums-member/update");
    return umsMemberService.update(umsMember);
}
```

## 10：验证码

> 实习图形数组验证码 ，并破解

