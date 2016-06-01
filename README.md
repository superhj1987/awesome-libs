#awesome-libs

some java useful utils and so on.

## Maven 

    <repository>
      <id>nexus-third</id>
      <name>Nexus osc third</name>
      <url>http://maven.oschina.net/content/repositories/thirdparty/</url>
      <releases>
        <enabled>true</enabled>
      </releases>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
    </repository>

    <dependency>
      <groupId>me.rowkey</groupId>
      <artifactId>awesome-libs</artifactId>
      <version>0.0.1</version>
    </dependency>
  
## Libs

### 1. ExcelUtil

use jxl

### 2. HttpFileRenderUtil

render http file from url、file、inputstream

### 3. ImageUtil

check the file is or not a image file

### 4. HttpClientUtil

send http request with connection pool

### 5. ServerChanUtil

@see <http://sc.ftqq.com/2.version>


### 6. me.rowkey.libs.spring.config.AwesomePropertiesPersister 

支持**.properites,.json,.conf(HOCON),.yaml**文件格式

        <bean id="propertyConfigurer"
            class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
            <property name="order" value="0"/>
            <property name="ignoreUnresolvablePlaceholders" value="true"/>
            ...
            <property name="propertiesPersister" ref="persister"/>
        </bean>
        <bean class="me.rowkey.libs.spring.config.AwesomePropertiesPersister"/>
    
### 7. me.rowkey.libs.spring.config.AwesomePropertyPlaceholderConfigurer

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
