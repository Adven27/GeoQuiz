package adven.geoquiz.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Injection {

    private static final Map<Class, Object> services;
    static {
        Map<Class, Object> aMap = new HashMap<>();
        aMap.put(QuestionsService.class, new QuestionsServiceImpl());
        services = aMap;
    }

    public static <T> T provide(Class<T> aClass) {
        return (T) services.get(aClass);
    }

    public static <T> void bind(Class<T> aClass, Object impl) {
        services.put(aClass, impl);
    }
}
