package me.rowkey.libs.spring.core.databinder;

import com.google.common.base.CaseFormat;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Author: Bryant Hang
 * Date: 15/1/14
 * Time: 下午5:25
 */
public class SuishenWebDataBinder extends ExtendedServletRequestDataBinder {
    private final Map<String, String> renameMapping;
    private boolean transUnderScoreFlag = false; // whether 转换 underscore的 参数为 camel 变量

    public SuishenWebDataBinder(Object target, String objectName, Map<String, String> renameMapping,
                                boolean transUnderScoreFlag) {
        super(target, objectName);
        this.renameMapping = renameMapping;
        this.transUnderScoreFlag = transUnderScoreFlag;
    }

    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);

        if (transUnderScoreFlag) {
            List<PropertyValue> propertyValueList = mpvs.getPropertyValueList();
            int listSize = propertyValueList.size();
            for (int i = 0; i < listSize; i++) {
                PropertyValue v = propertyValueList.get(i);
                String newPropertyName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, v.getName());
                if (!mpvs.contains(newPropertyName)) mpvs.add(newPropertyName, v.getValue());
            }
        }

        for (Map.Entry<String, String> entry : renameMapping.entrySet()) {
            String from = entry.getKey();
            String to = entry.getValue();
            if (mpvs.contains(from)) {
                mpvs.add(to, mpvs.getPropertyValue(from).getValue());
            }
        }
    }
}
