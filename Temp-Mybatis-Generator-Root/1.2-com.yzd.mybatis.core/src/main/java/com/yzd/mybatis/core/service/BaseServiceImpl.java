package com.yzd.mybatis.core.service;

import com.yzd.mybatis.core.dao.BaseDao;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 基础Service服务接口实现类
 *
 * @author cuiwei
 */
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    /**
     * 获取基础数据库操作类
     *
     * @return
     */
    protected abstract BaseDao<T, ID> getBaseDao();

    @Override
    public void add(T entity) {
        getBaseDao().insert(entity);
    }

    @Override
    public void addInBatch(Collection<T> entityCollection) {
        getBaseDao().insertInBatch(entityCollection);
    }

    @Override
    public int delete(T query) {
        return getBaseDao().delete(query);
    }

    @Override
    public int deleteById(ID id) {
        return getBaseDao().deleteById(id);
    }

    @Override
    public void deleteByIdInBatch(Collection<ID> idCollection) {
        getBaseDao().deleteByIdInBatch(idCollection);
    }

    @Override
    public int updateById(T entity) {
        return getBaseDao().updateById(entity);
    }

    @Override
    public int updateByIdSelective(T entity) {
        return getBaseDao().updateByIdSelective(entity);
    }

    @Override
    public void updateInBatch(Collection<T> entityCollection) {
        getBaseDao().updateInBatch(entityCollection);
    }

    @Override
    public T queryById(ID id) {
        return getBaseDao().selectById(id);
    }

    @Override
    public <V extends T> V queryVoById(ID id) {
        return getBaseDao().selectVoById(id);
    }

    @Override
    public <V extends T> V queryOne(T query) {
        return getBaseDao().selectOne(query);
    }

    @Override
    public <V extends T> V queryOne(T query, Sort sort) {
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<V> page = queryPageList(query, pageable);
        if (CollectionUtils.isNotEmpty(page.getContent())) {
            return page.getContent().get(0);
        } else {
            return null;
        }
    }

    @Override
    public long queryCount(T query) {
        return getBaseDao().selectCount(query);
    }

    @Override
    public <V extends T> List<V> queryList(T query) {
        return getBaseDao().selectList(query);
    }

    @Override
    public <V extends T> List<V> queryList(T query, Sort sort) {
        return getBaseDao().selectList(query, sort);
    }

    @Override
    public <V extends T> Page<V> queryPageList(T query, Pageable pageable) {
        return getBaseDao().selectPageList(query, pageable);
    }

    @Override
    public <V extends T> Page<V> queryPageList(T query, Pageable pageable, String sqlId, String sqlIdCount) {
        return getBaseDao().selectPageList(query, pageable, sqlId, sqlIdCount);
    }

    @Override
    public <K, V extends T> Map<K, V> queryMap(T query, String mapKey) {
        return getBaseDao().selectMap(query, mapKey);
    }

    @Override
    public <K, V extends T> Map<K, V> queryMap(T query, String mapKey, String sqlId) {
        return getBaseDao().selectMap(query, mapKey, sqlId);
    }

    ;

    @Override
    public <K, V extends T> Map<K, V> queryMap(T query, String mapKey, Sort sort) {
        return getBaseDao().selectMap(query, mapKey, sort);
    }

    ;

    @Override
    public <K, V extends T> Map<K, V> queryMap(T query, String mapKey, Sort sort, String sqlId) {
        return getBaseDao().selectMap(query, mapKey, sort, sqlId);
    }

    ;


    @Override
    public <K, V extends T> Map<K, V> queryMap(T query, String mapKey, Pageable pageable) {
        return getBaseDao().selectMap(query, mapKey, pageable);
    }

    @Override
    public Object queryList(String sqlId, Object object) {
        return getBaseDao().selectList(sqlId, object);
    }
}
