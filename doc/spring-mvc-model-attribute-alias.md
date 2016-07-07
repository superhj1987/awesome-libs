# spring-mvc-model-attribute-alias

## Why

The default spring mvc don't support model attribute alias. For example:

**Url**:http://localhost:8080/api/test?user_name=testUser

Controller:

<pre>
@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {

    @RequestMapping(value = "/test", headers = "Accept=application/json")
    public void authUser(ModelMap modelMap, Account acc) {
        ResultPack.packOk(modelMap);
    }
}

public class Account{
    private static final long serialVersionUID = 750752375611621980L;

    private long id;
    private String userName;
    private String password;
    private AccountType type = AccountType.ADMIN;
    private long timeTag;
    private int status = 1;
    ...
    ...
}
</pre>

When use spring mvc,the param ***user_name*** cannot be mapped to the account ***userName*** field.

In this case, u can use the SuishenServletModelAttributeMethodProcessor.


## Usage

In the spring mvc config xml, do as follow:

    <mvc:annotation-driven>
        ...
        <mvc:argument-resolvers>
            <bean class="me.rowkey.libs.spring.core.argumentresolver.SuishenServletModelAttributeMethodProcessor">
                <constructor-arg name="annotationNotRequired" value="true"/>
                <constructor-arg name="extendDataBinder"><null/></constructor-arg>
            </bean>
        </mvc:argument-resolvers>
        ...
    </mvc:annotation-driven>


The extendDataBinder can specified to other SuishenExtendDataBinder.If null,it'll use DefaultExtendDataBinder which supports SuishenParamName annotation to support model attribute alias.

<pre>
public class Account{
    private static final long serialVersionUID = 750752375611621980L;

    private long id;
    @SuishenParamName("user_name")
    private String userName;
    private String password;
    private AccountType type = AccountType.ADMIN;
    private long timeTag;
    private int status = 1;
    ...
    ...
}
</pre>