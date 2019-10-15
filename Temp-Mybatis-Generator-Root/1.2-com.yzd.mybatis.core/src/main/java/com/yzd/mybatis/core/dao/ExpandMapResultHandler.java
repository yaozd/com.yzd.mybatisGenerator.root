package com.yzd.mybatis.core.dao;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.Map;

/**
 * @param <V>
 * @param <K>
 * @author ZYLORG
 * @version V1.0
 * @Title: ExpandMapResultHandler.java
 * @Package com.kjj.core.dao
 * @Description: 扩展MapResult
 * @date 2016年5月25日 上午9:58:08
 * @copyright Beijing KJJ Electronic commerce Co., LTD
 */
public class ExpandMapResultHandler<K, V> implements ResultHandler {

    private final Map<K, V> mappedResults;
    private final String mapKey;
    private final ObjectFactory objectFactory;
    private final ObjectWrapperFactory objectWrapperFactory;
    private final ReflectorFactory reflectorFactory;

    /**
     * ExpandResultHandler带参数构造方法
     *
     * @param mapKey               map键
     * @param objectFactory        对象创建工厂
     * @param objectWrapperFactory 对象包装工厂
     * @param clazz                Map子接口或子类
     */
    @SuppressWarnings("unchecked")
    public ExpandMapResultHandler(String mapKey, ObjectFactory objectFactory,
                                  ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory, Class<?> clazz) {
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;
        this.mappedResults = (Map<K, V>) objectFactory.create(clazz);
        this.mapKey = mapKey;
    }

    @SuppressWarnings("unchecked")
    public void handleResult(ResultContext context) {
        final V value = (V) context.getResultObject();
        final MetaObject mo = MetaObject.forObject(value, objectFactory,
                objectWrapperFactory, reflectorFactory);
        final K key = (K) mo.getValue(mapKey);
        mappedResults.put(key, value);
    }

    public Map<K, V> getMappedResults() {
        return mappedResults;
    }

}
