package utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * @author jankinwu
 * @description 配置解析工具类
 * @date 2024/2/25 13:19
 */
public class ConfigUtils {

    public static <T> T getConfig(String fileName, Class<T> entityClass) {
        try {
            InputStream inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream(fileName);
            if (inputStream != null) {
                Yaml yaml = new Yaml();
                Map<String, Object> data = yaml.load(inputStream);
                return yamlToObject(data, entityClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> T yamlToObject(Map<String, Object> data, Class<T> entityClass) {
        try {
            T instance = entityClass.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                setFieldValue(instance, fieldName, fieldValue);
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> void setFieldValue(T instance, String fieldName, Object fieldValue) {
        try {
            java.lang.reflect.Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, fieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        // 示例：将配置映射到实体类
//        BasicConfig config = ConfigUtils.getConfig("config-dev.yml", BasicConfig.class);
//        if (config != null) {
//            log.info("Name: " + config.getAppId());
//            log.info("Version: " + config.getIdCode());
//            // 可以进一步处理其他属性
//        }
//    }
}
