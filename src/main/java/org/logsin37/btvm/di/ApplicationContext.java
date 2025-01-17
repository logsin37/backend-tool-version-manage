package org.logsin37.btvm.di;

import java.util.HashMap;
import java.util.Map;

import org.logsin37.btvm.constant.ErrorMessage;
import org.logsin37.btvm.util.Assert;

public class ApplicationContext {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static void registerBean(Class<?> clazz, Object bean) {
        Assert.notNull(clazz, ErrorMessage.NOT_NULL, "clazz");
        Assert.notNull(bean, ErrorMessage.NOT_NULL, "bean");
        if(BEAN_MAP.containsKey(clazz)) {
            throw new RuntimeException(String.format("bean %s already exists", clazz.getName()));
        }
        BEAN_MAP.put(clazz, bean);
    }

    public static void init() {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        ApplicationConfiguration.init();
        registerBean(ApplicationConfiguration.class, applicationConfiguration);
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T getBean(Class<T> clazz) {
        Assert.notNull(clazz, ErrorMessage.NOT_NULL, "clazz");
        if(!BEAN_MAP.containsKey(clazz)) {
            throw new RuntimeException(String.format("bean %s not found", clazz.getName()));
        }
        return (T) BEAN_MAP.get(clazz);
    }

    public static void stop() {
        BEAN_MAP.values().forEach(bean -> {
            if(bean instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) bean).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.exit(0);
    }

}
