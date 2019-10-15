package com.yzd.mybatis.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yzd.mybatis.core.exception.DaoException;
import com.yzd.mybatis.core.util.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.executor.result.DefaultResultContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;


/**
 * 基础Dao接口实现类，实现改类的子类必须设置泛型类型
 * @author cuiwei
 */
public abstract class BaseDaoImpl<T,ID extends Serializable> implements BaseDao<T,ID> {
	@Resource
	protected SqlSessionTemplate sqlSession;
	
	public static final String SQLNAME_SEPARATOR = ".";
	
	private Configuration configuration=new Configuration();

	/**
	 * @fields sqlNamespace SqlMapping命名空间 
	 */
	private String sqlNamespace = getDefaultSqlNamespace();

	/**
	 * 获取泛型类型的实体对象类全名
	 * @author cuiwei
	 * @return
	 */
	protected String getDefaultSqlNamespace() {
		Class<?> genericClass = BeanUtils.getGenericClass(this.getClass());
		return genericClass == null ? null : genericClass.getName().replace("entity", "sqlmaps")+"Mapper";
	}

	/**
	 * 获取SqlMapping命名空间 
	 * @author cuiwei
	 * @return SqlMapping命名空间 
	 */
	public String getSqlNamespace() {
		return sqlNamespace;
	}

	/**
	 * 设置SqlMapping命名空间。 以改变默认的SqlMapping命名空间，
	 * 不能滥用此方法随意改变SqlMapping命名空间。 
	 * @author cuiwei
	 * @param sqlNamespace SqlMapping命名空间 
	 */
	public void setSqlNamespace(String sqlNamespace) {
		this.sqlNamespace = sqlNamespace;
	}

	/**
	 * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
	 * @param sqlName SqlMapping名 
	 * @return 组合了SqlMapping命名空间后的完整SqlMapping名 
	 */
	protected String getSqlName(String sqlName) {
		return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
	}
	
	@Override
	public void insert(T entity) {
		Assert.notNull(entity);
		try {
			this.sqlSession.insert(getSqlName(BaseSqlId.SQL_INSERT), entity);
		} catch (Exception e) {
			throw new DaoException(String.format("添加对象出错！语句：%s", getSqlName(BaseSqlId.SQL_INSERT)), e);
		}
	}
	
	@Override
	public void insertInBatch(Collection<T> entityCollection) {
		// 没有数据时直接退出
		if (CollectionUtils.isEmpty(entityCollection)) {
			return;
		}

		// 新获取一个模式为BATCH，自动提交为false的session
		// 如果希望避免内存溢出，可以在循环中控制多少条提交一次并清理缓存
		SqlSession session = this.sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		// 通过新的session提交
		try {
			for (T entity : entityCollection) {
				session.insert(getSqlName(BaseSqlId.SQL_INSERT),entity);
			}
			// 提交
			session.commit();
			// 清理缓存
			session.clearCache();
		} catch (Exception e) {
			// 回滚
			session.rollback();
			throw new DaoException(String.format("批量添加对象出错！语句：%s", getSqlName(BaseSqlId.SQL_INSERT)),
					e);
		} finally {
			// 关闭
			session.close();
		}
	}

	@Override
	public int delete(T query) {
		Assert.notNull(query);
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return this.sqlSession.delete(getSqlName(BaseSqlId.SQL_DELETE), params);
		} catch (Exception e) {
			throw new DaoException(String.format("删除对象出错！语句：%s", getSqlName(BaseSqlId.SQL_DELETE)), e);
		}
	}

	@Override
	public int deleteById(ID id) {
		Assert.notNull(id);
		try {
			return this.sqlSession.delete(getSqlName(BaseSqlId.SQL_DELETE_BY_ID), id);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID删除对象出错！语句：%s", getSqlName(BaseSqlId.SQL_DELETE_BY_ID)), e);
		}
	}
	
	@Override
	public void deleteByIdInBatch(Collection<ID> idCollection) {
		if (CollectionUtils.isEmpty(idCollection)) {
			return;
		}

		// 新获取一个模式为BATCH，自动提交为false的session
		// 如果希望避免内存溢出，可以在循环中控制多少条提交一次并清理缓存
		SqlSession session = this.sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		// 通过新的session提交
		try {
			for (ID id : idCollection) {
				session.delete(getSqlName(BaseSqlId.SQL_DELETE_BY_ID),id);
			}
			// 提交
			session.commit();
			// 清理缓存
			session.clearCache();
		} catch (Exception e) {
			// 回滚
			session.rollback();
			throw new DaoException(String.format("批量删除出错！语句：%s", getSqlName(BaseSqlId.SQL_DELETE_BY_ID)),
					e);
		} finally {
			// 关闭
			session.close();
		}
	}

	@Override
	public int updateById(T entity) {
		Assert.notNull(entity);
		try {
			return this.sqlSession.update(getSqlName(BaseSqlId.SQL_UPDATE_BY_ID), entity);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID更新对象出错！语句：%s", getSqlName(BaseSqlId.SQL_UPDATE_BY_ID)), e);
		}
	}

	@Override
	public int updateByIdSelective(T entity) {
		Assert.notNull(entity);
		try {
			return this.sqlSession.update(getSqlName(BaseSqlId.SQL_UPDATE_BY_ID_SELECTIVE), entity);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID更新对象某些属性出错！语句：%s", getSqlName(BaseSqlId.SQL_UPDATE_BY_ID_SELECTIVE)),
					e);
		}
	}

	@Override
	public void updateInBatch(Collection<T> entityCollection) {
		// 没有数据时直接退出
		if (CollectionUtils.isEmpty(entityCollection)) {
			return;
		}

		// 新获取一个模式为BATCH，自动提交为false的session
		// 如果希望避免内存溢出，可以在循环中控制多少条提交一次并清理缓存
		SqlSession session = this.sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		// 通过新的session提交
		try {
			for (T entity : entityCollection) {
				session.update(getSqlName(BaseSqlId.SQL_UPDATE_BY_ID_SELECTIVE),entity);
			}
			// 提交
			session.commit();
			// 清理缓存
			session.clearCache();
		} catch (Exception e) {
			// 回滚
			session.rollback();
			throw new DaoException(String.format("批量更新对象出错！语句：%s", getSqlName(BaseSqlId.SQL_UPDATE_BY_ID_SELECTIVE)),
					e);
		} finally {
			// 关闭
			session.close();
		}
	}
	
	@Override
	public T selectById(ID id) {
		Assert.notNull(id);
		try {
			return this.sqlSession.selectOne(getSqlName(BaseSqlId.SQL_SELECT_BY_ID), id);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID查询对象出错！语句：%s", getSqlName(BaseSqlId.SQL_SELECT_BY_ID)), e);
		}
	}
	
	@Override
	public T selectById(ID id,String sqlId) {
		Assert.notNull(id);
		try {
			return this.sqlSession.selectOne(getSqlName(sqlId), id);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID查询对象出错！语句：%s", getSqlName(sqlId)), e);
		}
	}
	
	
	@Override
	public <V extends T> V  selectVoById(ID id) {
		Assert.notNull(id);
		try {
			return this.sqlSession.selectOne(getSqlName(BaseSqlId.SQL_SELECT_VO_BY_ID), id);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID查询Vo对象出错！语句：%s", getSqlName(BaseSqlId.SQL_SELECT_VO_BY_ID)), e);
		}
	}
	
	@Override
	public <V extends T> V  selectVoById(ID id,String sqlId) {
		Assert.notNull(id);
		try {
			return this.sqlSession.selectOne(getSqlName(sqlId), id);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID查询Vo对象出错！语句：%s", getSqlName(sqlId)), e);
		}
	}
	

	@Override
	public <V extends T> V selectOne(T query) {
		Assert.notNull(query);
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return this.sqlSession.selectOne(getSqlName(BaseSqlId.SQL_SELECT), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询一条记录出错！语句：%s", getSqlName(BaseSqlId.SQL_SELECT)), e);
		}
	}
	
	@Override
	public <V extends T> V selectOne(T query,String sqlId) {
		Assert.notNull(query);
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return this.sqlSession.selectOne(getSqlName(sqlId), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询一条记录出错！语句：%s", getSqlName(sqlId)), e);
		}
	}
	
	@Override
	public long selectCount(T query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return this.sqlSession.selectOne(getSqlName(BaseSqlId.SQL_SELECT_COUNT), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName(BaseSqlId.SQL_SELECT_COUNT)), e);
		}
	}
	
	@Override
	public long selectCount(T query,String sqlId) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return this.sqlSession.selectOne(getSqlName(sqlId), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName(sqlId)), e);
		}
	}

	@Override
	public <V extends T> List<V> selectList(T query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return this.sqlSession.selectList(getSqlName(BaseSqlId.SQL_SELECT), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName(BaseSqlId.SQL_SELECT)), e);
		}
	}
	
	@Override
	public <V extends T> List<V> selectList(T query,String sqlId) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return this.sqlSession.selectList(getSqlName(sqlId), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName(sqlId)), e);
		}
	}
	
	@Override
	public <V extends T> List<V> selectList(T query, Sort sort) {
		try {
			return this.sqlSession.selectList(getSqlName(BaseSqlId.SQL_SELECT), getParams(query, sort));
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句:%s", getSqlName(BaseSqlId.SQL_SELECT)), e);
		}
	}
	
	public <V extends T> List<V> selectList(T query, Sort sort, String sqlId){
		try {
			Map<String, Object> params = getParams(query, sort);
			return this.sqlSession.selectList(getSqlName(sqlId), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName(sqlId)), e);
		}
	}
	
	@Override
	public <V extends T> Page<V> selectPageList(T query, Pageable pageable) {
		try {
			List<V> contentList = this.sqlSession.selectList(getSqlName(BaseSqlId.SQL_SELECT),
					getParams(query, pageable));
			return new PageImpl<V>(contentList, pageable, this.selectCount(query));
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(BaseSqlId.SQL_SELECT)), e);
		}
	}
	
	@Override
	public <V extends T> Page<V> selectPageList(T query, Pageable pageable,String sqlId,String sqlIdCount) {
		try {
			List<V> contentList = this.sqlSession.selectList(getSqlName(sqlId),getParams(query, pageable));
			return new PageImpl<V>(contentList, pageable, this.selectCount(query,sqlIdCount));
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(sqlId)), e);
		}
	}
	
	@Override
	public <K, V extends T> Map<K, V> selectMap(T query, String mapKey) {
		Assert.notNull(mapKey, "[mapKey] - must not be null!");
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return this.sqlSession.selectMap(getSqlName(BaseSqlId.SQL_SELECT), params, mapKey);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象Map时出错！语句：%s", getSqlName(BaseSqlId.SQL_SELECT)), e);
		}
	}
		
	@Override
	public <K, V extends T> Map<K, V> selectMap(T query, String mapKey,String sqlId) {
		Assert.notNull(mapKey, "[mapKey] - must not be null!");
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return this.sqlSession.selectMap(getSqlName(sqlId), params, mapKey);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象Map时出错！语句：%s", getSqlName(sqlId)), e);
		}
	}
	
	@Override
	public <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Sort sort) {
		Assert.notNull(mapKey, "[mapKey] - must not be null!");
		try {
			Map<String, Object> params = getParams(query, sort);
			return getLinkedMap(params, mapKey, getSqlName(BaseSqlId.SQL_SELECT));
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象Map时出错！语句：%s", getSqlName(BaseSqlId.SQL_SELECT)), e);
		}
	}
	
	@Override
	public <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Sort sort,String sqlId) {
		Assert.notNull(mapKey, "[mapKey] - must not be null!");
		try {
			Map<String, Object> params = getParams(query, sort);
			return getLinkedMap(params, mapKey, getSqlName(sqlId));
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象Map时出错！语句：%s", getSqlName(BaseSqlId.SQL_SELECT)), e);
		}
	}
	
	@Override
	public <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable) {
		try {
			return this.sqlSession.selectMap(getSqlName(BaseSqlId.SQL_SELECT), getParams(query, pageable), mapKey);
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询Map出错！语句:%s", getSqlName(BaseSqlId.SQL_SELECT)), e);
		}
	}
	
	@Override
	public <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable,String sqlId) {
		try {
			return this.sqlSession.selectMap(getSqlName(sqlId), getParams(query, pageable), mapKey);
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询Map出错！语句:%s", getSqlName(sqlId)), e);
		}
	}

	/**
	 * 设置分页
	 * @param pageInfo 分页信息
	 * @return SQL分页参数对象
	 */
	protected RowBounds getRowBounds(Pageable pageable) {
		RowBounds bounds = RowBounds.DEFAULT;
		if (null != pageable) {
			bounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
		}
		return bounds;
	}

	/**
	 * 获取分页查询参数
	 * @param query 查询对象
	 * @param pageable 分页对象
	 * @return Map 查询参数
	 */
	protected Map<String, Object> getParams(T query, Pageable pageable) {
		Map<String, Object> params = BeanUtils.toMap(query, getRowBounds(pageable));
		if (pageable != null && pageable.getSort() != null) {
			String sorting = pageable.getSort().toString();
			params.put("sorting", sorting.replace(":", ""));
		}
		return params;
	}
	
	/**
	 * 获取排序查询参数
	 * @param query 查询对象
	 * @param sort 排序对象
	 * @return Map 查询参数
	 */
	protected Map<String, Object> getParams(T query, Sort sort) {
		Map<String, Object> params = BeanUtils.toMap(query);
		if (sort != null) {
			String sorting = sort.toString();
			params.put("sorting", sorting.replace(":", ""));
		}
		return params;
	}
	/**
	 * 获取LinkedMap
	 * @param query 查询对象
	 * @param mapKey map键
	 * @param sqlId mapper
	 * @return
	 */
	private <K, V extends T> Map<K, V> getLinkedMap(Map<String, Object> params, String mapKey, String sqlId) {

		final List<?> list = this.sqlSession.selectList(sqlId, params,RowBounds.DEFAULT);
		final ExpandMapResultHandler<K, V> mapResultHandler = new ExpandMapResultHandler<K, V>(
				mapKey, configuration.getObjectFactory(),configuration.getObjectWrapperFactory(),configuration.getReflectorFactory(),LinkedHashMap.class);
		final DefaultResultContext context = new DefaultResultContext();
		for (Object o : list) {
			context.nextResultObject(o);
			mapResultHandler.handleResult(context);
		}
		Map<K, V> selectedMap = (Map<K, V>) mapResultHandler.getMappedResults();
		return selectedMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object selectList(String sqlId,Object object) {
		if(object!=null){
			if(org.springframework.beans.BeanUtils.isSimpleProperty(object.getClass())||object instanceof Collection){
				throw new DaoException(String.format("查询自定义对象，查询参数出错！语句：%s", getSqlName(sqlId)));
			}
			Map<String, Object> params=null;
			try {
				if(object instanceof Map){
					params=(Map<String, Object>) object;
				}else{
					params = BeanUtils.toMap(object);
				}
			} catch (Exception e1) {
				throw new DaoException(String.format("查询自定义对象，参数转化出错！语句：%s", getSqlName(sqlId)),e1);
			}
			try {
				return this.sqlSession.selectList(getSqlName(sqlId), params);
			} catch (Exception e) {
				throw new DaoException(String.format("查询自定义对象出错！语句：%s", getSqlName(sqlId)), e);
			}
		}else{
			try {
				return this.sqlSession.selectList(getSqlName(sqlId));
			} catch (Exception e) {
				throw new DaoException(String.format("查询自定义对象出错！语句：%s", getSqlName(sqlId)), e);
			}
		}
	}
	
}
