package com.xiaofangyun.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import freemarker.template.Template;
import net.sf.json.JSONObject;
import oracle.jdbc.driver.OracleConnection;

/**
 * 描述：代码生成器
 */
public class CodeGenerateUtils {
	private final String CURRENT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss").format(new Date());

	public Connection getConnection() throws Exception {
		Class.forName(Constant.DRIVER);
		Connection connection = DriverManager.getConnection(Constant.URL,
				Constant.USER, Constant.PASSWORD);
		return connection;
	}

	public void generate(String tableName, String modelName) throws Exception {
		Connection connection = null;
		ResultSet resultSet = null;
		ResultSet primaryKeyResultSet = null;
		ResultSet exportedKeys = null;
		try {
			// 小写转大写
			tableName = tableName.toUpperCase();
			connection = getConnection();
			((OracleConnection) connection).setRemarksReporting(true);
			DatabaseMetaData databaseMetaData = null;
			try {
				databaseMetaData = connection.getMetaData();
				System.out.println("数据库已知的用户: "
						+ databaseMetaData.getUserName());
				System.out.println("数据库的系统函数的逗号分隔列表: "
						+ databaseMetaData.getSystemFunctions());
				System.out.println("数据库的时间和日期函数的逗号分隔列表: "
						+ databaseMetaData.getTimeDateFunctions());
				System.out.println("数据库的字符串函数的逗号分隔列表: "
						+ databaseMetaData.getStringFunctions());
				System.out.println("数据库供应商用于 'schema' 的首选术语: "
						+ databaseMetaData.getSchemaTerm());
				System.out.println("数据库URL: " + databaseMetaData.getURL());
				System.out.println("是否允许只读:" + databaseMetaData.isReadOnly());
				System.out.println("数据库的产品名称:"
						+ databaseMetaData.getDatabaseProductName());
				System.out.println("数据库的版本:"
						+ databaseMetaData.getDatabaseProductVersion());
				System.out.println("驱动程序的名称:"
						+ databaseMetaData.getDriverName());
				System.out.println("驱动程序的版本:"
						+ databaseMetaData.getDriverVersion());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			primaryKeyResultSet = databaseMetaData.getPrimaryKeys(null, null,
					tableName);// 获取表主键信息
			exportedKeys = databaseMetaData.getExportedKeys(null, null, tableName);  //获取外键
			// 生成Dto文件
			resultSet = databaseMetaData
					.getColumns(null, null, tableName, null);// 获取表结构信息
		
			String table_key = "";

			while (primaryKeyResultSet.next()) {
				table_key = primaryKeyResultSet.getString("COLUMN_NAME");
				System.out.println("主键:" + table_key);
			}		
			String exported_key = "";
			while (exportedKeys.next()) {
				exported_key = exportedKeys.getString("FKCOLUMN_NAME");
				System.out.println("外键:" + exported_key);
			}
			generatewordFile(resultSet, tableName, modelName,table_key,exported_key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// close(resultSet, connection);
			// close(primaryKeyResultSet, connection);
		}
	}

	

	private void generatewordFile(ResultSet resultSet, String tableName,
			String modelName,String table_key,String exported_key)
			throws Exception {
		File file = new File(Constant.projectPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String hasDateType = "false";
		final String suffix = ".doc";
		final String path = Constant.projectPath + modelName
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Dto.ftl";
			File mapperFile = new File(path);
			Map<String, Object> columnClassList = new HashMap<String, Object>();

			while (resultSet.next()) {
				//if(!StringUtils.isEmpty(resultSet.getString("REMARKS"))){
				JSONObject object = new JSONObject();
				// 获取字段名称
				object.put("ColumnName", resultSet.getString("COLUMN_NAME")
						.toLowerCase());
				// 获取字段类型
				object.put("ColumnType", resultSet.getString("TYPE_NAME"));
				if ("DATE".equals(resultSet.getString("TYPE_NAME").toString())) {
					hasDateType = "true";
				}
				// 转换字段名称，如 sys_name 变成 SysName
				object.put("ChangeColumnName",
						resultSet.getString("COLUMN_NAME"));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				//是否为空
				object.put(
						"isNullAble",
						resultSet.getString("IS_NULLABLE"));
				//字符长度
				object.put(
						"ColumnSize",
						resultSet.getString("COLUMN_SIZE"));
				//获取主键
				object.put(
						"primaryKey",
						resultSet.getString("COLUMN_NAME").equals(table_key)? "✔" : "");
				//获取外键
				object.put(
						"exportedKey",
						resultSet.getString("COLUMN_NAME").equals(exported_key)? "✔" : "");
				
				System.out.println(resultSet.getString("IS_NULLABLE")+"\t"+resultSet.getString("COLUMN_SIZE"));
				columnClassList.put(object.getString("ColumnName"), object);
			//	}
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("model_column", columnClassList);
			dataMap.put("hasDateType", hasDateType);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}



	private void generateFileByTemplate(final String templateName, File file,
			Map<String, Object> dataMap, String tableName, String modelName) throws Exception {
		Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
		FileOutputStream fos = new FileOutputStream(file);
		dataMap.put("table_name_small", tableName);
		dataMap.put("table_name", tableName);
		dataMap.put("modelName", modelName);
		dataMap.put("date", CURRENT_DATE);	
		Writer out = new BufferedWriter(new OutputStreamWriter(fos), 10240);
		template.process(dataMap, out);
	}

	
}