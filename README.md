#awesome-libs

some java useful utils and so on.

1. [ExcelUtil](src/main/java/me/rowkey/libs/util/ExcelUtil.java)

  封装了excel读取、生成操作

1. [HttpFileRenderUtil](src/main/java/me/rowkey/libs/util/HttpFileRenderUtil.java)

  render http file from url、file、inputstream

1. [ImageUtil](src/main/java/me/rowkey/libs/util/ImageUtil.javal)

  check the file is or not a image file

1. [HttpClientUtil](src/main/java/me/rowkey/libs/util/HttpClientUtil.java)

  send http request with connection pool, newest with apache httpcomments httpclient 4.3

1. [ServerChanUtil](src/main/java/me/rowkey/libs/util/ServerChanUtil.java)

  @see <http://sc.ftqq.com/2.version>

1. [AwesomePropertiesPersister](src/main/java/me/rowkey/libs/spring/config/AwesomePropertiesPersister.java) 

  支持**.properites,.json,.conf(HOCON),.yaml**文件格式

        <bean id="propertyConfigurer"
            class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
            <property name="order" value="0"/>
            <property name="ignoreUnresolvablePlaceholders" value="true"/>
            ...
            <property name="propertiesPersister" ref="persister"/>
        </bean>
        <bean class="me.rowkey.libs.spring.config.AwesomePropertiesPersister"/>
    
1. [AwesomePropertyPlaceholderConfigurer](src/main/java/me/rowkey/libs/spring/config/AwesomePropertyPlaceholderConfigurer.java)

  use this PropertyPlaceholderConfigurer can adapt .properties,.json,.conf,.yaml config files.

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
