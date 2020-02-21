package cn.coldcoder.util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class propertiesUtil {

        private static Logger logger = LoggerFactory.getLogger(propertiesUtil.class);

        private static Properties props;

        static {    //静态代码块，先执行且仅加载一次
            String fileName = "hautbbs.properties";
            props = new Properties();
            try {
                props.load(new InputStreamReader(propertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
            } catch (IOException e) {
                logger.error("配置文件读取异常");
            }
        }
        public static String getProperty(String key){
            String value = props.getProperty(key.trim());
            if(StringUtils.isBlank(value)){
                return null;
            }
            return value.trim();
        }

        public static String getProperty(String key,String defaultValue){
            String value = props.getProperty(key.trim());
            if(StringUtils.isBlank(value)){
                value = defaultValue;
            }
            return value.trim();
        }

}
