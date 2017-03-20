package accew.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by CrazyFive on 2017/3/19.
 */
public class PropertyUtil {

    private static Map<String, Properties> propertiesMap = Collections.synchronizedMap(new HashMap<String, Properties>());

    private PropertyUtil(){

    }

    private synchronized static void loadProperties(String fileName) throws Exception {
        InputStream is = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        is = classLoader.getResourceAsStream(fileName);

        Properties props = new Properties();
        if (is != null){
            props.load(is);
        }else {
            File propFile = new File(System.getProperty("user.dir", fileName));
            props.load(new FileInputStream(propFile));
        }

        propertiesMap.put(fileName, props);
    }

    public static String getProperty(String fileName, String key){
        String value = null;
        try {
            Properties properties = propertiesMap.get(fileName);
            if (properties == null){
                loadProperties(fileName);
                properties = propertiesMap.get(fileName);
            }
            value = properties.getProperty(key);
        } catch (Exception e){
            value = null;
        }
        return value == null ? null : value.trim();
    }
}
