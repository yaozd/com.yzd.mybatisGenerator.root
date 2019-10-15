package com.yzd.mybatis.core.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 基础Dao接口
 *
 * @author cuiwei
 */
public interface BaseDao<T, ID extends Serializable> {

    /**
     * 添加对象,如果要添加的对象没有设置Id或者Id为空字符串或者是空格，则添加数据之前会调用 generateId()方法设置Id
     *
     * @param entity 要实例化的实体，不能为null
     * @return 受影响的结果数
     */
    void insert(T entity);

    /**
     * 删除对象
     *
     * @param query 要删除的实体对象，不能为null
     * @return int 受影响结果数
     */
    int delete(T query);

    /**
     * 根据Id删除对象
     *
     * @param id 要删除的ID，不能为null
     * @return int 受影响结果数
     */
    int deleteById(ID id);

    /**
     * 更新对象，对象必须设置ID
     *
     * @param entity 实体的Id不能为null
     * @return int 受影响结果数
     */
    int updateById(T entity);

    /**
     * 更新对象中已设置的字段，未设置的字段不更新
     *
     * @param entity 要更新的实体对象，不能为null，切ID必须不为null
     * @return int 受影响结果数
     */
    int updateByIdSelective(T entity);

    /**
     * 根据id，批量删除记录，如果传入的列表为null或为空列表则直接返回
     *
     * @param idCollection 批量删除ID集合
     */
    void deleteByIdInBatch(Collection<ID> idCollection);

    /**
     * 批量插入，如果为空列表则直接返回
     *
     * @param entityCollection 需要批量插入的实体对象集合
     */
    void insertInBatch(Collection<T> entityCollection);

    /**
     * 批量更新，该方法根据实体ID更新已设置的字段，未设置的字段不更新
     *
     * @param entityCollection 批量更新的实体对象集合
     */
    void updateInBatch(Collection<T> entityCollection);

    /**
     * 通过Id查询一个对象，如果id为null这会抛出IllegalArgumentException异常
     *
     * @param id 主键，不能为null
     * @return 结果对象，如果未找到返回null
     */
    T selectById(ID id);

    /**
     * 通过Id查询一个对象，如果id为null这会抛出IllegalArgumentException异常
     *
     * @param id    主键，不能为null
     * @param sqlId 自定义sqlId
     * @return 结果对象，如果未找到返回null
     */
    T selectById(ID id, String sqlId);

    /**
     * 通过Id查询一个对象，如果id为null这会抛出IllegalArgumentException异常
     *
     * @param id 主键，不能为null
     * @return Mapper中映射的对象，继承自 T对象，一般是Vo对象
     */
    <V extends T> V selectVoById(ID id);

    /**
     * 通过Id查询一个对象，如果id为null这会抛出IllegalArgumentException异常
     *
     * @param id    主键，不能为null
     * @param sqlId 自定义sqlId
     * @return Mapper中映射的对象，继承自 T对象，一般是Vo对象
     */
    <V extends T> V selectVoById(ID id, String sqlId);

    /**
     * 查询一个对象，如果返回的结果多于一个对象将会抛出TooManyResultsException
     *
     * @param query 查询对象，不能为null
     * @return Mapper中映射的对象，继承自 T对象，一般是Vo对象
     */
    <V extends T> V selectOne(T query);

    /**
     * 查询一个对象，如果返回的结果多于一个对象将会抛出TooManyResultsException
     *
     * @param query 查询对象，不能为null
     * @param sqlId 自定义sqlId
     * @return Mapper中映射的对象，继承自 T对象，一般是Vo对象
     */
    <V extends T> V selectOne(T query, String sqlId);

    /**
     * 查询记录数
     *
     * @param query 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    long selectCount(T query);

    /**
     * 查询记录数
     *
     * @param query 查询对象，如果为null，则查询对象总数
     * @param sqlId 自定义sqlId
     * @return long 记录总数
     */
    long selectCount(T query, String sqlId);

    /**
     * 查询对象列表
     *
     * @param query 查询参数，如果未null则查询所有，相当于调用方法selectAll
     * @return 结果对象列表
     */
    <V extends T> List<V> selectList(T query);

    /**
     * 查询对象列表
     *
     * @param query 查询参数，如果未null则查询所有，相当于调用方法selectAll
     * @param sqlId 自定义sqlId
     * @return 结果对象列表
     */
    <V extends T> List<V> selectList(T query, String sqlId);

    /**
     * 查询对象列表
     *
     * @param query 查询参数，如果未null则查询所有，相当于调用方法selectAll
     * @param sort  排序参数
     * @return 结果对象列表
     */
    <V extends T> List<V> selectList(T query, Sort sort);

    /**
     * 查询对象列表
     *
     * @param query 查询参数，如果未null则查询所有，相当于调用方法selectAll
     * @param sort  排序参数
     * @param sqlId 自定义sqlId
     * @return 结果对象列表
     */
    <V extends T> List<V> selectList(T query, Sort sort, String sqlId);

    /**
     * 查询对象列表，注意：在给定非null的分页对象时该方法自动设置分页总记录数,如果query和pageable同时为null则查询所有
     *
     * @param query    查询参数
     * @param pageInfo 分页对象
     * @return Page 信息方便前台显示
     */
    <V extends T> Page<V> selectPageList(T query, Pageable pageable);

    /**
     * 查询对象列表，注意：在给定非null的分页对象时该方法自动设置分页总记录数,如果query和pageable同时为null则查询所有
     *
     * @param query      查询参数
     * @param pageInfo   分页对象
     * @param sqlId      自定义sqlId
     * @param sqlIdCount 自定义sqlIdCount
     * @return Page 信息方便前台显示
     */
    <V extends T> Page<V> selectPageList(T query, Pageable pageable, String sqlId, String sqlIdCount);

    /**
     * 根据结果集中的一列作为key，将结果集转换成Map
     *
     * @param <K>    返回Map的key类型
     * @param <V>    返回Map的Value类型
     * @param query  查询参数,如果未null则查询所有对象
     * @param mapKey 返回结果List中‘mapKey’属性值作为Key (The property to use as key for each value in the list.)
     * @return Map 包含key属性值的Map对象
     */
    <K, V extends T> Map<K, V> selectMap(T query, String mapKey);

    /**
     * 根据结果集中的一列作为key，将结果集转换成Map
     *
     * @param <K>    返回Map的key类型
     * @param <V>    返回Map的Value类型
     * @param query  查询参数,如果未null则查询所有对象
     * @param mapKey 返回结果List中‘mapKey’属性值作为Key (The property to use as key for each value in the list.)
     * @param sqlId  自定义sqlId
     * @return Map 包含key属性值的Map对象
     */
    <K, V extends T> Map<K, V> selectMap(T query, String mapKey, String sqlId);

    /**
     * 根据结果集中的一列作为key，将结果集转换成LinkerHashMap
     *
     * @param <K>    返回Map的key类型
     * @param <V>    返回Map的Value类型
     * @param query  查询参数,如果未null则查询所有对象
     * @param mapKey 返回结果List中‘mapKey’属性值作为Key (The property to use as key for each value in the list.)
     * @param sort   排序参数
     * @return Map 包含key属性值的Map对象
     */
    <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Sort sort);

    /**
     * 根据结果集中的一列作为key，将结果集转换成LinkerHashMap
     *
     * @param <K>    返回Map的key类型
     * @param <V>    返回Map的Value类型
     * @param query  查询参数,如果未null则查询所有对象
     * @param mapKey 返回结果List中‘mapKey’属性值作为Key (The property to use as key for each value in the list.)
     * @param sort   排序参数
     * @param sqlId  自定义sqlId
     * @return Map 包含key属性值的Map对象
     */
    <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Sort sort, String sqlId);


    /**
     * 根据结果集中的一列作为key，将结果集转换成Map
     *
     * @param <K>    返回Map的key类型
     * @param <V>    返回Map的Value类型
     * @param query  查询参数
     * @param mapKey 返回结果List中‘mapKey’属性值作为Key (The property to use as key for each value in the list.)
     * @param page   分页对象
     * @return Map containing key pair data.
     */
    <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable);

    /**
     * 根据结果集中的一列作为key，将结果集转换成Map
     *
     * @param <K>    返回Map的key类型
     * @param <V>    返回Map的Value类型
     * @param query  查询参数
     * @param mapKey 返回结果List中‘mapKey’属性值作为Key (The property to use as key for each value in the list.)
     * @param page   分页对象
     * @param sqlId  自定义sqlId
     * @return Map containing key pair data.
     */
    <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable, String sqlId);

    /**
     * 根据自定义sqlId查询自定义对象
     *
     * @param sqlId  查询语句
     * @param object 查询对象（实体类，map）
     * @return
     */
    Object selectList(String sqlId, Object object);
}
