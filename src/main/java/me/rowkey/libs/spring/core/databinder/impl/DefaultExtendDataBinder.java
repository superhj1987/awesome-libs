package me.rowkey.libs.spring.core.databinder.impl;

import me.rowkey.libs.spring.core.annotation.SuishenParamName;
import me.rowkey.libs.spring.core.databinder.SuishenExtendDataBinder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.ServletRequestDataBinder;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Bryant Hang
 * Date: 15/1/17
 * Time: 下午6:34
 */
public class DefaultExtendDataBinder implements SuishenExtendDataBinder {

    //Rename cache
    private final Map<Class<?>, Map<String, String>> replaceMap = new ConcurrentHashMap<Class<?>, Map<String, String>>();

    /**
     * 扩展bind，这里实现的是对parameter中的name别名转换以及low_underscore到camel的转化
     *
     * @param mpvs
     * @param dataBinder
     */
    @Override
    public void doExtendBind(MutablePropertyValues mpvs, ServletRequestDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        Class<?> targetClass = target.getClass();
        if (!replaceMap.containsKey(targetClass)) {
            Map<String, String> mapping = analyzeClass(targetClass);
            replaceMap.put(targetClass, mapping);
        }
        Map<String, String> mapping = replaceMap.get(targetClass);

        for (Map.Entry<String, String> entry : mapping.entrySet()) {
            String from = entry.getKey();
            String to = entry.getValue();
            if (mpvs.contains(from)) {
                mpvs.add(to, mpvs.getPropertyValue(from).getValue());
            }
        }
    }

    private static Map<String, String> analyzeClass(Class<?> targetClass) {
        Field[] fields = targetClass.getDeclaredFields();
        Map<String, String> renameMap = new HashMap<String, String>();
        for (Field field : fields) {
            SuishenParamName paramNameAnnotation = field.getAnnotation(SuishenParamName.class);
            if (paramNameAnnotation != null && StringUtils.isNotBlank(paramNameAnnotation.value())) {
                renameMap.put(paramNameAnnotation.value(), field.getName());
            }
        }
        if (renameMap.isEmpty()) return Collections.emptyMap();
        return renameMap;
    }
}
