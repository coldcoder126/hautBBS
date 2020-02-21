package cn.coldcoder.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class cacheUtil {

    private static Logger logger = LoggerFactory.getLogger(cacheUtil.class);

    //LRU算法
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().
            initialCapacity(1000).
            maximumSize(30000).
            expireAfterAccess(3, TimeUnit.DAYS).concurrencyLevel(4)
            .build(new CacheLoader<String, String>() {
                //初始大小是1000个键值对，最大为30000,生命周期为30天
                //默认的数据加载实现,当调用get取值的时候,如果key没有对应的值,就调用这个方法进行加载.
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key,String value){
        localCache.put(key,value);
    }

    public static String getKey(String key){
//        initCache();
        String value = null;
        try {
            value = localCache.get(key);
            if("null".equals(value)){
                return null;
            }
            return value;
        }catch (Exception e){
            logger.error("localCache get error",e);
        }
        return null;
    }
//initCache方法为测试用
//    private static void initCache(){
//        localCache.put("token_duxiang","10001");
//        System.out.println("杜翔已加入缓存");
//    }
}
