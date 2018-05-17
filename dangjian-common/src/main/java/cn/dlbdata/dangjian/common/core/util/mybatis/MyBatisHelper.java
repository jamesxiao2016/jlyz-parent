package cn.dlbdata.dangjian.common.core.util.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;

public class MyBatisHelper {
	
	/**
	 * 获取countSql
	 * @param session
	 * @param namespace
	 * @param params
	 * @return
	 */
	public static String getSmartCountSql(SqlSession session, String namespace, Object params)
	{
		params = wrapCollection(params);
		Configuration configuration = session.getConfiguration();
		MappedStatement mappedStatement = configuration.getMappedStatement(namespace);
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		BoundSql boundSql = mappedStatement.getBoundSql(params);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql();
		sql = SqlParser.getSmartCountSql(sql);
				
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (params == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(params.getClass())) {
						value = params;
					} else {
						MetaObject metaObject = configuration.newMetaObject(params);
						value = metaObject.getValue(propertyName);
					}

					sql = replaceParameter(sql, propertyName, value);
				}
			}
		}
		
		return sql;
	}

	/**
	 * 通过命名空间方式获取sql
	 * 
	 * @param session
	 * @param namespace
	 * @param params
	 * @return
	 */
	public static String getNamespaceBoundSql(SqlSession session, String namespace, Object params) {
		params = wrapCollection(params);
		Configuration configuration = session.getConfiguration();
		MappedStatement mappedStatement = configuration.getMappedStatement(namespace);
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		BoundSql boundSql = mappedStatement.getBoundSql(params);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql();
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (params == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(params.getClass())) {
						value = params;
					} else {
						MetaObject metaObject = configuration.newMetaObject(params);
						value = metaObject.getValue(propertyName);
					}

					sql = replaceParameter(sql, propertyName, value);
				}
			}
		}
		return sql;
	}

	/**
	 * 通过命名空间方式获取sql
	 * 
	 * @param session
	 * @param namespace
	 * @param params
	 * @return
	 */
	public static String getBoundSql(SqlSession session, String namespace, Object params) {
		params = wrapCollection(params);
		Configuration configuration = session.getConfiguration();
		MappedStatement mappedStatement = configuration.getMappedStatement(namespace);
		BoundSql boundSql = mappedStatement.getBoundSql(params);
		String sql = boundSql.getSql();
		return sql;
	}

	/**
	 * 通过命名空间方式获取sql
	 * 
	 * @param session
	 * @param namespace
	 * @param params
	 * @return
	 */
	public static String getNamespaceSql(SqlSession session, String namespace, Object params) {
		params = wrapCollection(params);
		Configuration configuration = session.getConfiguration();
		MappedStatement mappedStatement = configuration.getMappedStatement(namespace);
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		BoundSql boundSql = mappedStatement.getBoundSql(params);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql();
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (params == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(params.getClass())) {
						value = params;
					} else {
						MetaObject metaObject = configuration.newMetaObject(params);
						value = metaObject.getValue(propertyName);
					}
					JdbcType jdbcType = parameterMapping.getJdbcType();
					if (value == null && jdbcType == null)
						jdbcType = configuration.getJdbcTypeForNull();
					sql = replaceParameter(sql, value, jdbcType, parameterMapping.getJavaType());
				}
			}
		}
		return sql;
	}

	/**
	 * 根据类型替换参数 仅作为数字和字符串两种类型进行处理，需要特殊处理的可以继续完善这里
	 * 
	 * @param sql
	 * @param value
	 * @param jdbcType
	 * @param javaType
	 * @return
	 */
	private static String replaceParameter(String sql, Object value, JdbcType jdbcType, Class<?> javaType) {
		String strValue = String.valueOf(value);
		if (jdbcType != null) {
			switch (jdbcType) {
			// 数字
			case BIT:
			case TINYINT:
			case SMALLINT:
			case INTEGER:
			case BIGINT:
			case FLOAT:
			case REAL:
			case DOUBLE:
			case NUMERIC:
			case DECIMAL:
				break;
			// 日期
			case DATE:
			case TIME:
			case TIMESTAMP:
				// 其他，包含字符串和其他特殊类型
			default:
				strValue = "'" + strValue + "'";

			}
		} else if (Number.class.isAssignableFrom(javaType)) {
			// 不加单引号
		} else {
			strValue = "'" + strValue + "'";
		}
		return sql.replaceFirst("\\?", strValue);
	}

	private static String replaceParameter(String sql, String propertyName, Object value) {
		return sql.replaceFirst("\\?", "#{" + propertyName + "}");
	}

	/**
	 * 简单包装参数
	 * 
	 * @param object
	 * @return
	 */
	private static Object wrapCollection(final Object object) {
		if (object instanceof List) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", object);
			return map;
		} else if (object != null && object.getClass().isArray()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("array", object);
			return map;
		}
		return object;
	}

}
