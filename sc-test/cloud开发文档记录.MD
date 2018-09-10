
当前项目仅用于demo测试使用

当前项目结构：
```
eureka-server表示：服务注册中心

sc-clint表示：cloud客户端，仅仅知识提供服务

service-ribbon：cloud客户端，不仅提供服务，并且消费服务

service-feign：Feign客户端，不仅提供服务，并且消费服务（个人推荐使用这种模式）

service-zuul:zuul就是一个路由器，外部来访问我们服务的时候，可以先经过路由器，然后通过路由器去找到具体的服务

```


#服务注册中心

我们要搭建一个普通的springboot，创建服务注册中心，来管理服务

第一步：
```
pom文件引用：
<dependencies>
        		<dependency>
        			<groupId>org.springframework.cloud</groupId>
        			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        		</dependency>
        	</dependencies>

```
第二步：
```
springboot的启动类加入：
@EnableEurekaServer注解表示，他是一个服务注册中心
```

第三步：
```
在application.yml中加入服务中心的配置信息

#配置端口
server:
  port: 8761

#配置服务信息
eureka:
  instance:
  #设置服务地址
    hostname: localhost
  client:
  #设置不需要去检索服务
    registerWithEureka: false
  #设置自己不注册自己
    fetchRegistry: false
  #访问服务的url地址
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
#设置应用名称
spring:
  application:
    name: eurka-server
```


好，启动项目。

这样我们就有一个服务注册中心了。

***


***



#服务提供者

有了服务注册中心以后，那么，我们就需要服务来进行注册，好，我们现在开始创建服务

创建服务呢，很简单，就是创建一个普通的springboot项目就可以了

 
+ [x] 首先创建一个普通的springboot的项目
+ [x] 然后在pom文件中加入引用
```
<dependencies>
    <!--表示客户端-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
	</dependencies>

```
+ [x] 在启动类上面加入注解，标注他是springcloud中的客户端（服务提供者）
```
@EnableEurekaClient
```

+ [x] 在本服务中，我们可以提供controller的访问

+ [x] 在application.yml配置向哪儿服务中心进行注册级其他信息
```aidl
#设置端口
server:
  port: 8762
#设置本服务的名称
spring:
  application:
    name: sc-clint
#设置注册中心的地址
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

+ [x] 启动项目

+ [x] 在服务注册中心里面，就可以看到该服务已经被注册进去了。

+ [x] 如果两个服务名称相同，那么在注册服务里面默认就会以集群的形式进行显示

        ```
        这里可以使用多个tomcat来配置，同一个微服务，然后同时注册到服务中心中
        服务中心会判断，只要是服务名称相同，那么他们将会以集群形式出现
        ```




#服务消费者（ribbon)

该demo中采用ribbon来进行实现的

+ [x] 首先创建一个普通的springboot项目

+ [x] 在pom文件中加入消费者和客户端标记
```
 <dependencies>
        <!--客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--客户端的消费端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
    </dependencies>
```
+ [x] 在启动类加入服务提供者和消费者的注解

```aidl
@EnableEurekaClient表示服务提供者（客户端）
@EnableDiscoveryClient表示服务服务消费者
```

+ [x] 在配置类中初始化RestTemplate类
```aidl
 /**
     * LoadBalanced注解表明这个restRemplate开启负载均衡的功能。
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
```


+ [x] 如何实现调用其他服务呢？可以在service中或者controller中去引用刚刚申明的
restTempLate

```aidl
其中：
sc-clint表示需要消费的服务名称（服务名称就是每个服务向服务中心注册的名字）
hi


restTemplate.getForObject("http://sc-clint/hi?name="+name,String.class);
```

+ [x] 然后启动服务

访问该项目中的service或者controller，来，就可以看到调用其他服务返回的消息。

这样就可以达到简单的服务之间的调用

PS：如果在注册中心服务名称是一样的，我们在其他服务调用的时候，加入@LoadBalanced那么会自动实现负债均衡模式




#服务消费者（Feign模式）个人比较推荐使用这种模式

创建一个普通的springboot项目
第一步：
```aidl
pom文件中加入

 <dependencies>
        <!--客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--客户端的消费端Feign-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
    </dependencies>

```

第二步：

在系统启动类上面加入：
```aidl
@EnableEurekaClient表示当前是客户端
@EnableDiscoveryClient表示当前是消费者
@EnableFeignClients表示当前是feign模式的客户端
```

第三步：

申明一个需要使用的接口信息
```aidl
表示客户端需要连接到哪个服务里面进行消费
@FeignClient(value = "sc-clint")
public interface FeignService {

    表示连接到该服务下面的具体哪个服务里面去，还需要给出这个服务所需要的参数信息
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name) ;
}


```

第四步：

申明一个controller进行服务调用即可。


PS：fegin默认只是支持负债均衡的。所以，没有必要写 @LoadBalanced注解





#断路由（服务器错了以后，友好提示）

通俗：

服务提供商，如果服务提供商服务器挂掉了。那么势必会引起一系列的问题。

那么我们可以规定，服务提供商服务挂掉以后，其他人来调用该服务的时候，返回特定的值

主要修改的地方是消费服务的地方

意思就是说：我去消费的时候，发现服务未启动，我就可以指定使用另外的服务或者其他操作

例如：我调用一个服务
该服务挂掉了。
我会采取备份措施
去访问另外一个方法，来处理该事情。 


##如何来做呢


###用ribbon模式如何来加

+ [x] 首先添加pom文件引用
```aidl
加入断路由
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
  </dependency>
```

+ [x] 在启动类加入注释@EnableHystrix来表示允许断路由存在

+ [x] 在调用其他服务的方法上面加入@HystrixCommand(fallbackMethod = "hiError")。

    指定如果是其他服务断开连接了，我们将会执行其他的方法。这里使用的是hiError方法
    
```aidl

   /**
     * 消费
     * @param name
     * @return
     */
    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        return restTemplate.getForObject("http://sc-clint/hi?name="+name,String.class);
    }


    public String hiError(String name) {
        return "hi,"+name+",服务已经停了，你等哈儿再来";
    }
```

+ [x] 重新启动消费服务。

+ [x] 如何测试呢
        （1.打开服务提供商，检查一下服务消费商是否能够正常使用）
        
        （2.在使用的时候，关闭服务提供商，检查一下服务消费商是否使用了指定的fallbackMethos中的方法）
        
        
###用Feign模式如何来加

+ [x] 本身feign是支持断路由的，但是目前我使用的这个版本有点儿低，所以需要在application.yml文件中配置
```aidl
feign:
  hystrix:
    enabled: true
```

    不用在pom文件中引用其他的包
    
+ [x] 需要实现服务调用的接口类。重写接口里面方法，并且需要在接口中定义fallback所对应的实现类
```aidl

接口类
fallback是指定接口实现类
@FeignClient(value = "sc-clint",fallback =SchedualServiceHiHystric.class )
public interface FeignService {

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name) ;
}


实现类

普通接口实现即可

@Component
public class SchedualServiceHiHystric implements FeignService {
    @Override
    public String sayHiFromClientOne(String name) {
        return name+"所调用的服务已经关闭了，你等哈儿来";
    }
}

```

+ [x] 重新启动消费服务。

+ [x] 如何测试呢
        （1.打开服务提供商，检查一下服务消费商是否能够正常使用）
        
        （2.在使用的时候，关闭服务提供商，检查一下服务消费商是否使用了指定的fallbackMethos中的方法）
        


#Zuul（路由器和权限认证控制的）
Zuul的主要功能是路由转发和过滤器。路由功能是微服务的一部分，

比如／api/user转发到到user服务，/api/shop转发到到shop服务。zuul默认和Ribbon结合实现了负载均衡的功能。

+ [x] 首先呢还是得创建一个普通的springboot的项目

+ [x] 然后呢，pom文件中加入路由器控制及客户端

```aidl
 <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
    </dependencies>
```

+ [x]  启动类中注明，该类是可以配置路由器配置的

```aidl
@EnableZuulProxy
```

+ [x]  向注册中心注册该服务信息、配置路由分发参数

```aidl
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
server:
  port: 8769
spring:
  application:
    name: service-zuul

zuul:
  routes:
    #配置名称
    api-a:
    #配置访问地址
      path: /ribbon/**
     #配置所使用的服务名称
      serviceId: service-ribbon
    api-b:
      path: /feign/**
      serviceId: service-feign

```
+ [x]  启动该路由服务。外部通过访问
    http://localhost:8679/feign/my
    http://localhost:8679/feign/hi?name=沈阳
     http://localhost:8679/ribbon/hi?name=沈阳
     
   如果正常访问，表示路由功能配置成功
   
   
#Eureka Server集群化


为了解决的问题：

    1.注册中心压力较大的情况下如何处理。
    
如何来做呢


    首先第一步(也可以不做）：
            windows：C:\Windows\System32\drivers\etc下面有个host文件。添加（有几个就写几个）,
            
                    127.0.0.1       peer1
                    127.0.0.1       peer2
    
            linxu：etc\host
            
                    127.0.0.1       peer1
                     127.0.0.1       peer2
    
    第二步：
    
            修改application.yml文件信息
            
                #设置不需要去检索服务（如果是集群，就需要修改为true）
                   registerWithEureka: true
                 #设置自己不注册自己（如果是集群，就需要修改为true）
                   fetchRegistry: true 
                
                
                 #访问服务的url地址(这里要写成其他注册中心地址）
                 #这里的127.0.0.1可以西城第一步配置的别名，例如peer1
                    serviceUrl:
                      defaultZone: http://127.0.0.1:8751/eureka/
                      
    第三步：
    
            其他注册中心也需要和第二步一样的更改方式。
            
            只是服务的url地址需要写成第二步配置的端口和地址
            
            
    第四步：
    
            在服务提供方注册的时候，填写地址更改
            
            原：
            
                eureka:
                  client:
                    serviceUrl:
                      defaultZone: http://127.0.0.1:8761/eureka/
                      
            更改为两个服务中心都要注册：
            
                    eureka:
                      client:
                        serviceUrl:
                          defaultZone: http://127.0.0.1:8761/eureka/,http://127.0.0.1:8751/eureka/
                          
                          
                          
    第五步：
    
            依次启动服务中心A，B
            
            在启动第一个服务中心的时候，抛出异常，未找到注册中心地址，不用管它，
            
            继续启动第二个服务中心的时候，第二个服务中心启动成功以后，第一个也会自己成功的。
            
            因为他们是相互注册，所以会有这个问题。
            
            
            最后启动服务支持项目。就可以看到服务注册中心是集群化了。
            
            
     
   
   