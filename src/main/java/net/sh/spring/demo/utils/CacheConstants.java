package net.sh.spring.demo.utils;

/**
 * Created by Deven on 2016-12-19.
 */
public class CacheConstants {

    public static final String REGION_NAME = SpringContextHolder.getBean("cacheRegion").toString();

}
