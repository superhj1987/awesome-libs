# spring-remoting-thrift
thrift rpc support in spring: ThriftServiceExporter;ThriftFactoryBean

## Usage

### Server

    <bean name="/api/appService"
          class="me.rowkey.libs.spring.remoting.thrift.ThriftServiceExporter">
        <property name="serviceInterface" value="***">
        </property>
        <property name="service">
            <ref bean="***"/>
        </property>
    </bean>

### Client

    <bean id="thriftAppService"
          class="me.rowkey.libs.spring.remoting.thrift.ThriftProxyFactoryBean">
        <property name="serviceUrl">
            <value>${api.url}</value>
        </property>
        <property name="serviceInterface"
                  value="***"/>
    </bean>
