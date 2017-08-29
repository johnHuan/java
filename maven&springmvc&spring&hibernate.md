# Spring-SpringMVC-Hibernate在IntelliJ与Maven的环境下搭建

1. pom.xml

   ```
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
     <modelVersion>4.0.0</modelVersion>
     <groupId>com.john</groupId>
     <artifactId>heatpipe03</artifactId>
     <packaging>war</packaging>
     <version>1.0-SNAPSHOT</version>
     <name>heatpipe03 Maven Webapp</name>

     <properties>
       <!-- spring版本号 -->
       <spring.version>4.0.4.RELEASE</spring.version>
       <hibernate.version>4.3.5.Final</hibernate.version>
     </properties>



     <url>http://maven.apache.org</url>
     <dependencies>
       <dependency>
         <groupId>junit</groupId>
         <artifactId>junit</artifactId>
         <version>3.8.1</version>
         <scope>test</scope>
       </dependency>

       <!-- spring核心包 -->
       <!-- springframe start -->
       <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-webmvc</artifactId>
         <version>${spring.version}</version>
       </dependency>
       <!-- springframe end -->
       <dependency>
         <groupId>javax.servlet.jsp.jstl</groupId>
         <artifactId>javax.servlet.jsp.jstl-api</artifactId>
         <version>1.2.1</version>
       </dependency>
       <dependency>
         <groupId>taglibs</groupId>
         <artifactId>standard</artifactId>
         <version>1.1.2</version>
       </dependency>
       <dependency>
         <groupId>tomcat</groupId>
         <artifactId>servlet-api</artifactId>
         <version>5.5.23</version>
         <scope>provided</scope>
       </dependency>
       <dependency>
         <groupId>tomcat</groupId>
         <artifactId>jsp-api</artifactId>
         <version>5.5.23</version>
         <scope>provided</scope>
       </dependency>
       <dependency>
         <groupId>commons-fileupload</groupId>
         <artifactId>commons-fileupload</artifactId>
         <version>1.3.1</version>
       </dependency>
       <dependency>
         <groupId>org.hibernate</groupId>
         <artifactId>hibernate-validator</artifactId>
         <version>5.1.2.Final</version>
       </dependency>
       <!-- json数据 -->
       <dependency>
         <groupId>org.codehaus.jackson</groupId>
         <artifactId>jackson-mapper-asl</artifactId>
         <version>1.9.13</version>
       </dependency>

       <!-- hibernate -->
       <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-orm</artifactId>
         <version>${spring.version}</version>
       </dependency>
       <dependency>
         <groupId>org.hibernate</groupId>
         <artifactId>hibernate-core</artifactId>
         <version>${hibernate.version}</version>
       </dependency>

       <!-- MySQL -->
       <dependency>
         <groupId>c3p0</groupId>
         <artifactId>c3p0</artifactId>
         <version>0.9.1.2</version>
       </dependency>
       <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>5.1.31</version>
       </dependency>


     </dependencies>
     <build>
       <finalName>heatpipe03</finalName>
     </build>
   </project>
   ```

   ​

2. web.xml

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns="http://java.sun.com/xml/ns/javaee"
            xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
            metadata-complete="true" version="3.0">

     <display-name>Archetype Created Web Application</display-name>

     <servlet>
       <servlet-name>spring-dispatcher</servlet-name>
       <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     </servlet>
     <servlet-mapping>
       <servlet-name>spring-dispatcher</servlet-name>
       <url-pattern>/</url-pattern>
     </servlet-mapping>

     <filter>
       <filter-name>hibernateFilter</filter-name>
       <filter-class>org.springframework.orm.hibernate4.support.OpenSessionInViewFilter</filter-class>
     </filter>
     <filter-mapping>
       <filter-name>hibernateFilter</filter-name>
       <url-pattern>/*</url-pattern>
     </filter-mapping>

     <!-- Spring字符集过滤器 -->
     <filter>
       <filter-name>SpringEncodingFilter</filter-name>
       <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
       <init-param>
         <param-name>encoding</param-name>
         <param-value>UTF-8</param-value>
       </init-param>
       <init-param>
         <param-name>forceEncoding</param-name>
         <param-value>true</param-value>
       </init-param>
     </filter>
     <filter-mapping>
       <filter-name>SpringEncodingFilter</filter-name>
       <url-pattern>/*</url-pattern>
     </filter-mapping>

     <!-- 读取spring配置文件 -->
     <!-- Hibernate通过SessionFactory来获取Session,我们要在spring的配置文件中配置一个我们所需的SessionFactory，为了便于修改，我们新建一个配置文件，路径与spring的配置文件相同，我们新建一个infrastructure.xml -->
     <context-param>
       <param-name>contextConfigLocation</param-name>
       <param-value>classpath:/META-INF/applicationContext.xml,classpath:/META-INF/infrastructure.xml
       </param-value>
     </context-param>

     <listener>
       <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
     </listener>


   </web-app>
   ```

3. spring-dispatcher-servlet.xml

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:mvc="http://www.springframework.org/schema/mvc"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

       <!-- 扫描controller（controller层注入） -->
       <context:component-scan base-package="com.john.heatpipe.controller"/>

       <mvc:annotation-driven />
       <!-- 对模型视图添加前后缀 -->
       <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
           <property name="prefix" value="/pages/"/>
           <property name="suffix" value=".jsp"/>
       </bean>

       <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
           <property name="defaultEncoding" value="UTF-8"/>
           <property name="maxUploadSize" value="2000000"/>
       </bean>

       <mvc:resources mapping="/static/**" location="/"/>

       <mvc:default-servlet-handler />


   </beans>
   ```

4. index.jsp

   ```
   <%--
     Created by Administrator 
     Created with IntelliJ IDEA.
     User: Administrator
     Author: 张桓
     Email: yz30.com@aliyun.com
     QQ: 248404941
     Date: 2017/8/29
     Time: 18:59
   --%>
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
       <meta charset="utf-8">
       <meta http-equiv="X-UA-Compatible" content="IE=edge">
       <meta name="viewport" content="width=device-width, initial-scale=1">
       <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
       <!-- 新 Bootstrap 核心 CSS 文件 -->
       <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
       <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
       <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
       <!--[if lt IE 9]>
       <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
       <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
       <![endif]-->
       <title>首页</title>
   </head>
   <body class="container">

   <h1>Hello , john this is index.jsp</h1>
   <h2> 哈哈 is a foolish!</h2>
   <p><a href="/json1">toJson1</a></p>
   <p><a href="/json">toJson</a></p>

   <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
   <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
   <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
   <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
   </body>
   </html>

   ```

5. infrastructure.xml

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:context="http://www.springframework.org/schema/context"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
       http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
       http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
       ">


       <context:property-placeholder location="classpath:/META-INF/properties/hibernate.properties" />

       <!-- 使用C3P0数据源，MySQL数据库 -->
       <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">
           <!-- MySQL -->
           <property name="driverClass" value="${driverClassName}"></property>
           <property name="jdbcUrl" value="${url}"></property>
           <property name="user" value="${username}"></property>
           <property name="password" value="${password}"></property>
           <property name="maxPoolSize" value="40"></property>
           <property name="minPoolSize" value="1"></property>
           <property name="initialPoolSize" value="1"></property>
           <property name="maxIdleTime" value="20"></property>
       </bean>

       <!-- session工厂 -->
       <!-- spring与hibernate整合配置，扫描所有dao -->
       <bean id="sessionFactory" class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
           <property name="dataSource" ref="dataSource" />
           <property name="packagesToScan" value="com.john.heatpipe.entity" />
           <property name="hibernateProperties">
               <props>
                   <prop key="hibernate.hbm2ddl.auto">${hibernate.hbm2ddl.auto}</prop>
                   <prop key="hibernate.dialect">${hibernate.dialect}</prop>
                   <prop key="hibernate.show_sql">${hibernate.show_sql}</prop>
                   <prop key="hibernate.format_sql">${hibernate.format_sql}</prop>
               </props>
           </property>
       </bean>

       <bean id="transactionManager"
             class="org.springframework.orm.hibernate4.HibernateTransactionManager">
           <property name="sessionFactory" ref="sessionFactory"></property>
       </bean>

       <!-- 对数据源进行事务管理 -->
       <tx:annotation-driven transaction-manager="transactionManager" />


   </beans>
   ```

6. applicationContext.xml

   ```
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:jee="http://www.springframework.org/schema/jee"
          xmlns:p="http://www.springframework.org/schema/p"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
           http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-3.0.xsd">

       <context:component-scan base-package="com.john.heatpipe.service"/>

       <context:component-scan base-package="com.john.heatpipe.entity"/>
       <!-- 扫描文件（自动将service层注入） -->
       <context:component-scan base-package="com.john.heatpipe.dao"/>


   </beans>

   ```

7. hibernate.properties

   ```

   hibernate.dialect=org.hibernate.dialect.MySQLDialect
   driverClassName=com.mysql.jdbc.Driver
   validationQuery=SELECT 1
   url=jdbc:mysql://localhost:3306/heatpipe?useUnicode=true&characterEncoding=UTF-8
   username=root
   password=johnjohn

   hibernate.hbm2ddl.auto=update
   hibernate.show_sql=true
   hibernate.format_sql=true

   ```

8. UserServiceImpl

   ```
   package com.john.heatpipe.service;

   import com.john.heatpipe.dao.UserDao;
   import com.john.heatpipe.entity.User;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   import org.springframework.transaction.annotation.Transactional;

   import java.util.ArrayList;
   import java.util.List;

   /**
    * Created by Administrator
    * Created with IntelliJ IDEA.
    * User: Administrator
    * Author: 张桓
    * Email: yz30.com@aliyun.com
    * QQ: 248404941
    * Date: 2017/8/29
    * Time: 19:25
    */

   @Service("userService")
   @Transactional
   public class UserServiceImpl implements UserService {

       @Autowired
       private UserDao userDao;

       public void saveUser(List<User> us) {
           for (User u : us) {
               userDao.save(u);
           }
       }

       public List<User> getAllUsernames() {
           return userDao.findAll();
       }
   }

   ```

9. UserService

   ```
   package com.john.heatpipe.service;

   import com.john.heatpipe.entity.User;

   import java.util.List;

   /**
    * Created by Administrator
    * Created with IntelliJ IDEA.
    * User: Administrator
    * Author: 张桓
    * Email: yz30.com@aliyun.com
    * QQ: 248404941
    * Date: 2017/8/29
    * Time: 19:24
    */

   public interface UserService {

       void saveUser(List<User> us);

       List<User> getAllUsernames();




   }

   ```

10. User

    ```
    package com.john.heatpipe.entity;


    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.Id;
    import javax.persistence.Table;

    /**
     * Created by Administrator
     * Created with IntelliJ IDEA.
     * User: Administrator
     * Author: 张桓
     * Email: yz30.com@aliyun.com
     * QQ: 248404941
     * Date: 2017/8/29
     * Time: 20:35
     */

    @Entity
    @Table
    public class User {

        @Id
        @GeneratedValue
        private Integer id;

        private String username;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

    ```

11. UserDaoImpl

    ```
    package com.john.heatpipe.dao;

    import com.john.heatpipe.entity.User;
    import org.hibernate.Criteria;
    import org.hibernate.SessionFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    /**
     * Created by Administrator
     * Created with IntelliJ IDEA.
     * User: Administrator
     * Author: 张桓
     * Email: yz30.com@aliyun.com
     * QQ: 248404941
     * Date: 2017/8/29
     * Time: 20:40
     */

    @Repository
    public class UserDaoImpl implements UserDao {

        @Autowired
        private SessionFactory sessionFactory;

        public int save(User u) {
            return (Integer) sessionFactory.getCurrentSession().save(u);
        }

        public List<User> findAll() {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
            return criteria.list();
        }

    }

    ```

12. UserDao

    ```
    package com.john.heatpipe.dao;

    import com.john.heatpipe.entity.User;

    import java.util.List;

    /**
     * Created by Administrator
     * Created with IntelliJ IDEA.
     * User: Administrator
     * Author: 张桓
     * Email: yz30.com@aliyun.com
     * QQ: 248404941
     * Date: 2017/8/29
     * Time: 20:38
     */

    public interface UserDao {

        /**
         * @param u
         * @return
         */
        int save(User u);

        /**
         * @return
         */
        List<User> findAll();

    }

    ```

13. MainController

    ```
    package com.john.heatpipe.controller;

    import com.john.heatpipe.entity.User;
    import com.john.heatpipe.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.ResponseBody;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    /**
     * Created by Administrator
     * Created with IntelliJ IDEA.
     * User: Administrator
     * Author: 张桓
     * Email: yz30.com@aliyun.com
     * QQ: 248404941
     * Date: 2017/8/29
     * Time: 19:08
     */

    @Controller
    @RequestMapping(value = "/")
    public class MainController {

        @Autowired
        private UserService userService;

        @RequestMapping("")
        public String home(){
            System.out.println("home");
            List<User> us = new ArrayList<User>();
            User u = new User();
            u.setUsername("johnHuan");
            us.add(u);
            u = new User();
            u.setUsername("张桓");
            us.add(u);
            userService.saveUser(us);
            return "index";
        }

    //    public String index(){
    //        return "index";
    //    }


        @RequestMapping(value = "/json1")
        @ResponseBody
        public Map<String, String> json1(){
            Map<String, String> result = new HashMap<String, String>();
            result.put("Mark", "hello");
            result.put("Ken", "Hehe");
            result.put("Fowa", "lala");
            result.put("john", "haha");
            return result;
        }


        @RequestMapping(value = "/json")
        @ResponseBody
        public List<User> json(){
            return userService.getAllUsernames();
        }

    }

    ```

    > ctrl+shift+alt+insert  调出草稿箱
    >
    > 粘贴json数据后
    >
    > ctrl+alt+l格式化json数据