package accew.common;

import accew.modules.utils.ReflectionUtils;
import accew.common.exceptions.UpdateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class, Integer.class }) })
public class UpdateFilterPlugin implements Interceptor {
	private static org.slf4j.Logger logger = accew.modules.logger.Logger.getLog(UpdateFilterPlugin.class);

	private static final String  NoVersions = "_NoVersions";
	
	private static Properties properties;

	public UpdateFilterPlugin() {
		logger.debug("QueryPlugin start");
	}

	public Object intercept(Invocation ivk) throws Throwable {

		RoutingStatementHandler handler = (RoutingStatementHandler) ivk.getTarget();
		// 通过反射获取到当前RoutingStatementHandler对象的delegate属性
		StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
		MappedStatement statement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
		if (statement == null) {
			logger.error("在找到对应的SQL语句，请检查配置文件。");
			throw new RuntimeException("在找到对应的SQL语句，请检查配置文件。");
		}

		// 取得sql类型
		SqlCommandType sqlCommandType = statement.getSqlCommandType();
		// 获取到当前StatementHandler的
		// boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了
		// RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。

		BoundSql boundSql = delegate.getBoundSql();
		String sql = boundSql.getSql();

		if (SqlCommandType.UPDATE.equals(sqlCommandType) && !isSkip(sql)) {

			// 拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
			Object parameter = boundSql.getParameterObject();

			Connection connection = (Connection) ivk.getArgs()[0];
			if(!statement.getId().endsWith(NoVersions)){
				String isOk = this.getVersion(statement, parameter, connection);
				if (isOk == null) {
					throw new UpdateException("未进行排他性控制,sqlId=" + statement.getId());
				}else if (("").equals(isOk)) {
					
					throw new UpdateException("该版本数据已被他人修改,sqlId=" + statement.getId()+ "|"+ ReflectUtil.getString(parameter));
				}

				// 重写sql
				String repleceSql = buildSql(sql.toLowerCase());
				if (StringUtils.isNotBlank(repleceSql)){
					// 利用反射设置当前BoundSql对应的sql属性为我们建立好Sql语句
					ReflectUtil.setFieldValue(boundSql, "sql", repleceSql);
				}
			}
		}else if (SqlCommandType.INSERT.equals(sqlCommandType) && !isSkip(sql)){
			
			if(sql.toLowerCase().indexOf("CreateTime") < 0){
				//logger.info("未插入创建时间,sqlId=" + statement.getId());
				String repleceSql = sql.replaceFirst(")",",CreateTime)").trim();
				repleceSql = repleceSql.substring(0, repleceSql.length() - 1) + "now() )";
				ReflectUtil.setFieldValue(boundSql, "sql", repleceSql);
			}
		}
		return ivk.proceed();
	}

	/**
	 * getVersion(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param mappedStatement
	 * @param parameter
	 * @param connection
	 * @return null 排他错误 String
	 * @exception
	 * @since 1.0.0
	 */
	private String getVersion(MappedStatement mappedStatement, Object parameter, Connection connection) {
		String getVersion = null;
		// 获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
		// delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		// 获取到我们自己写在Mapper映射语句中对应的Sql语句
		String sql = boundSql.getSql();
		Long versions = (Long) getObjectValue(parameter,"versions");
		String where = getWhereByUpdateSql(sql);
		String sqlForUpdate = "select * From " + getTableByUpdateSql(sql) + " where " + where + " and versions = "+ versions+ " for update";

		int allSize = countStr(sql,"?");
		int whereSize = countStr(where,"?");
		// 通过BoundSql获取对应的参数映射
		// 取得参数，剔除from前的参数，
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		List<ParameterMapping> parameterMappingsBak = parameterMappings.subList(allSize - whereSize, allSize);
/*		for(ParameterMapping mapping : parameterMappings){
			mapping.
		}*/

		// 利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
		BoundSql selectBoundSql = new BoundSql(mappedStatement.getConfiguration(), sqlForUpdate, parameterMappingsBak, parameter);
		// 通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameter, selectBoundSql);

		// 通过connection建立一个countSql对应的PreparedStatement对象。
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(sqlForUpdate);
			// 通过parameterHandler给PreparedStatement对象设置参数
			parameterHandler.setParameters(pstmt);
			// 之后就是执行获取总记录数的Sql语句和获取结果了。
			rs = pstmt.executeQuery();
			if (rs.next()) {
				try {
					getVersion = rs.getString("versions");
				} catch (SQLException e) {
					getVersion = null;
				}
			}else{
				getVersion = "";
			}
		} catch (SQLException e) {
			logger.error("取得锁定记录出错，请检查表结构中是否有versions,," + e.getMessage(), e);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return getVersion;
	}

	private Object setObjectValue(Object parameter, String key, String value) {
		if (parameter instanceof Map) {
			Map map = (Map) parameter;
			map.put(key, value);
		} else if (parameter instanceof String || parameter instanceof Long || parameter instanceof Integer) {
			parameter = value;
		} else if (parameter instanceof List) {
			List<Object> list = (List) parameter;
			for (Object obj : list) {
				setObjectValue(obj, key, value);
			}
		} else {
			try {
				if (ReflectionUtils.getAccessibleField(parameter, key) != null && ReflectionUtils.getFieldValue(parameter, key) == null) {
					ReflectionUtils.setFieldValue(parameter, key, Long.valueOf(value));
				}
			} catch (Exception e) {
				logger.error("进行数据隔离," + e.getMessage(), e);
			}
		}
		return parameter;
	}

	/**
	 * countStr(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param str1 取得原
	 * @param str2 判断字符
	 * @return str2 在 str1 的个数
	 *int
	 * @exception
	 * @since  1.0.0
	*/
	public static int countStr(String str1, String str2) { 
    	int counter=0;
        if (str1.indexOf(str2) == -1) {  
            return 0;
        } 
            while(str1.indexOf(str2)!=-1){
            	counter++;
            	str1=str1.substring(str1.indexOf(str2)+str2.length());
            }
            return counter;  
    }  
	
	private Object getObjectValue(Object parameter, String key) {
		Object ret = null;
		if (parameter instanceof Map) {
			Map map = (Map) parameter;
			ret = map.get(key);
		} else if (parameter instanceof String || parameter instanceof Long || parameter instanceof Integer) {
			ret = parameter;
/*		} else if (parameter instanceof List) {
			List<Object> list = (List) parameter;
			for (Object obj : list) {
				setObjectValue(obj, key, value);
			}*/
		} else {
			try {
				Field field = ReflectUtil.getField(parameter, key);
				field.setAccessible(true);
				if (field != null) {
					ret = ReflectionUtils.getFieldValue(parameter, key);
				}
			} catch (Exception e) {
				logger.error("进行排他," + e.getMessage(), e);
			}
		}
		return ret;
	}
	
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties aproperties) {
		properties = aproperties;
	}

	public static boolean isSkip(String sql) {
		Pattern p = Pattern.compile("(\\s*update\\s+)(\\w*)(\\s+)");
		Matcher m = p.matcher(sql.toLowerCase());
		String tableStr = "";
		if (m.find()) {
			tableStr = m.group(2);
		} else {
			tableStr = "";
		}
		if (StringUtils.isNotBlank(tableStr) && !properties.containsValue(tableStr.toUpperCase())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * buildPageSql 如果没有设定version 则添加version
	 * 
	 * @param sql
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	private String buildSql(String sql) {
		// StringBuffer sqlBack = new
		// StringBuffer(sql.toLowerCase().trim().substring(6));
		// 需要增加的设值
		String addSql = " versions = (IFNULL(versions,0)+1)";

		// 转换成小写，以便重写
		String sqlSetting = getSettingByUpdateSql(sql);

		String setting = sql;
		// 判断是否有version
		Pattern p = Pattern.compile("(\\s+\\w*\\.{0,1}\\s+versions\\s*=\\s*\\w*\\s*)");
		Matcher m = p.matcher(sqlSetting);
		if (!m.find()) {
			//sqlSetting = sqlSetting + " , " + addSql;
			setting = sql.replace("where ", "," + addSql + " where ");
		}

		//String setting = "UPDATE " + getTableByUpdateSql(sql) + " " + sqlSetting + " where " + getWhereByUpdateSql(sql);
		return setting;
	}

	private String getTableByUpdateSql(String sql) {
		Pattern p = Pattern.compile("(.*update\\s*)(\\w*)(.*)");
		Matcher m = p.matcher(sql.toLowerCase());
		String tableStr = "";
		if (m.find()) {
			tableStr = m.group(2);
		} else {
			tableStr = "";
		}
		return tableStr;
	}

	private String getWhereByUpdateSql(String sql) {
		Pattern p = Pattern.compile("(.*\\swhere \\s*)(.*)");
		Matcher m = p.matcher(sql.toLowerCase());
		String whereStr = "";
		if (m.find()) {
			whereStr = m.group(2);
		} else {
			whereStr = "";
		}
		return whereStr;
	}

	private String getSettingByUpdateSql(String sql) {
		String sqlBack = sql.trim().substring(6).replaceFirst(getTableByUpdateSql(sql), "").replace(getWhereByUpdateSql(sql), "").trim();
		sqlBack = sqlBack.substring(0, sqlBack.length() - 5);
		return sqlBack;
	}

	/**
	 * 利用反射进行操作的一个工具类
	 * 
	 */
	private static class ReflectUtil {
		/**
		 * 利用反射获取指定对象的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标属性的值
		 */
		public static Object getFieldValue(Object obj, String fieldName) {
			Object result = null;
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				field.setAccessible(true);
				try {
					result = field.get(obj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		/**
		 * 利用反射获取指定对象里面的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标字段
		 */
		private static Field getField(Object obj, String fieldName) {
			Field field = null;
			for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException e) {
					// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
				}
			}
			return field;
		}

		/**
		 * 利用反射设置指定对象的指定属性为指定的值
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @param fieldValue
		 *            目标值
		 */
		public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				try {
					field.setAccessible(true);
					field.set(obj, fieldValue);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public static String getString(Object obj)throws Exception{
			StringBuffer sb = new StringBuffer("");
			String lineSeparator = System.getProperty("line.separator", "\n");  
	        Field [] fields = obj.getClass().getDeclaredFields();  
	        for(Field f:fields){  
	            f.setAccessible(true);  
	        }  

	        for(Field f:fields){  
	            String field = f.toString().substring(f.toString().lastIndexOf(".")+1);         //取出属性名称  
	            sb.append(lineSeparator + field+" --> "+f.get(obj) + "");

	        }
	        return sb.toString();
	    } 
	}

}
