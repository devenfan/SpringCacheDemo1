package net.sh.spring.demo.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;

import java.lang.reflect.Method;

/**
 * Created by Deven on 2016-12-19.
 */
@Deprecated
public class CacheKeyGenerator implements KeyGenerator {

    private boolean debug;

    private String regionName;

    public Object generate(Object target, Method method, Object... params) {
        return generateKey(params);
    }

    /**
     * Generate a key based on the specified parameters.
     */
    public Object generateKey(Object... params) {

        if (this.regionName == null || this.regionName.length() == 0) {
            throw new RuntimeException("The CacheKeyGenerator must have a regionName!");
        }

        if (params.length == 0) {
            throw new IllegalArgumentException("The cache key cannot be null or empty!");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(this.regionName);
        sb.append(":");
        for(int i = 0; i < params.length; i++) {
            sb.append(params[i] == null ? "null" : params[i]);
            if(i < params.length - 1) {
                sb.append(":");
            }
        }

        if(debug) {
            System.out.println("CacheKey: " + sb.toString());
        }

        return sb.toString();
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
