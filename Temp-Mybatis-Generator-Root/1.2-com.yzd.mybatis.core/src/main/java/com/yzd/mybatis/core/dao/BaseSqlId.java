package com.yzd.mybatis.core.dao;

/**
 * Mybatis Sql脚本的ID名称
 * @author cuiwei
 */
public class BaseSqlId {
	public static final String SQL_SELECT_COUNT = "selectCount";
	public static final String SQL_SELECT = "select";
	public static final String SQL_SELECT_BY_ID = "selectByPrimaryKey";
	public static final String SQL_SELECT_VO_BY_ID = "selectVoByPrimaryKey";
	public static final String SQL_UPDATE_BY_ID = "updateByPrimaryKey";
	public static final String SQL_UPDATE_BY_ID_SELECTIVE = "updateByPrimaryKeySelective";
	public static final String SQL_DELETE = "delete";
	public static final String SQL_DELETE_BY_ID = "deleteByPrimaryKey";
	public static final String SQL_INSERT = "insert";
}
