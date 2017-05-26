awesome-libs [![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
----
[![GitHub stars](https://img.shields.io/github/stars/superhj1987/awesome-libs.svg?style=social&label=Star&)](https://github.com/superhj1987/awesome-libs/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/superhj1987/awesome-libs.svg?style=social&label=Fork&)](https://github.com/superhj1987/awesome-libs/fork)

some java useful utils and so on.

1. [ExcelUtil](src/main/java/me/rowkey/libs/util/ExcelUtil.java)

    > 封装了excel读取、生成操作

1. [HttpFileRenderUtil](src/main/java/me/rowkey/libs/util/HttpFileRenderUtil.java)

    > render http file from url、file、inputstream

1. [ImageUtil](src/main/java/me/rowkey/libs/util/ImageUtil.javal)

    > check the file is or not a image file

1. [HttpClientUtil](src/main/java/me/rowkey/libs/util/HttpClientUtil.java)

    > send http request with connection pool, newest with apache httpcomments httpclient 4.3

1. [ServerChanUtil](src/main/java/me/rowkey/libs/util/ServerChanUtil.java)

    > @see <http://sc.ftqq.com/2.version>
  
1. [SortUtil](src/main/java/me/rowkey/libs/util/SortUtil.java)

    > include seven common use sort algorithms

1. [LoogerUtil](src/main/java/me/rowkey/libs/util/LoggerUtil.java)

    > logger operation, can output the line u call the method

1. [JavaUtil](src/main/java/me/rowkey/libs/util/JavaUtil.java)

    > Java operation util, such as get the line no of the code
    
1. [Networks](src/main/java/me/rowkey/libs/support/Networks.java)

    > network utils, include ip、hostaddress...
    
1. [Systems](src/main/java/me/rowkey/libs/suppport/Systems.java)
 
    > include some system function such as killByPid, hostName, hsotPid and so on
    
 1. [Urls](src/main/java/me/rowkey/libs/suppport/Urls.java)
  
     > url process tools.
     
 1. [Maps](src/main/java/me/rowkey/libs/suppport/Maps.java)
  
     > Map util, such as convert java bean to/from map.

1. [LRUCache](src/main/java/me/rowkey/libs/cache/LRUCache.java)
    
    > lru缓存的linkedhashmap实现

1. [AwesomePropertiesPersister](src/main/java/me/rowkey/libs/spring/config/AwesomePropertiesPersister.java) 

    > 支持**.properites,.json,.conf(HOCON),.yaml**文件格式;支持java properties包含中文

        <bean id="propertyConfigurer"
            class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
            <property name="order" value="0"/>
            <property name="ignoreUnresolvablePlaceholders" value="true"/>
            ...
            <property name="propertiesPersister" ref="persister"/>
        </bean>
        <bean id="persister" class="me.rowkey.libs.spring.config.AwesomePropertiesPersister"/>
    
1. [AwesomePropertyPlaceholderConfigurer](src/main/java/me/rowkey/libs/spring/config/AwesomePropertyPlaceholderConfigurer.java)

    > use this PropertyPlaceholderConfigurer can adapt .properties,.json,.conf,.yaml config files and support chinese in java properties.

        <bean id="propertyConfigurer"
                  class="me.rowkey.libs.spring.config.AwesomePropertyPlaceholderConfigurer">
            <property name="order" value="0"/>
            <property name="ignoreUnresolvablePlaceholders" value="true"/>
            <property name="locations">
                <list>
                    ...
                </list>
            </property>
        </bean>
        
1. [spring-mvc-model-attribute-alias](doc/spring-mvc-model-attribute-alias.md)
 
1. [spring-remoting-thrift](doc/spring-remoting-thrift.md)

1. [picocli](http://picocli.info) 

    > Annotation based command line parser with strong typing for both options and positional args and support for git-like subcommands. Usage help with ANSI colors. Easily included as source to avoid external dependencies.
