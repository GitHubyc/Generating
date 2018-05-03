package com.xakj.util;

import freemarker.template.Template;
import net.sf.json.JSONObject;

import oracle.jdbc.driver.OracleConnection;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

	public void generate(String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		Connection connection = null;
		ResultSet resultSet = null;
		ResultSet primaryKeyResultSet = null;
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
			// 生成Entity文件
			resultSet = databaseMetaData
					.getColumns(null, null, tableName, null);// 获取表结构信息
			generateEntityFile(resultSet, tableName, modelName,
					tableAnnotation, author);
			// 生成Dto文件
			resultSet = databaseMetaData
					.getColumns(null, null, tableName, null);// 获取表结构信息
			generateDtoFile(resultSet, tableName, modelName, tableAnnotation,
					author);
			// 生成AddDto文件
			resultSet = databaseMetaData
					.getColumns(null, null, tableName, null);// 获取表结构信息
			generateAddDtoFile(resultSet, tableName, modelName, tableAnnotation,
					author);

			// 生成PutDto文件
			resultSet = databaseMetaData
					.getColumns(null, null, tableName, null);// 获取表结构信息
			generatePutDtoFile(resultSet, tableName, modelName, tableAnnotation,
					author);

			// 生成DetailOutputDto文件
			resultSet = databaseMetaData
					.getColumns(null, null, tableName, null);// 获取表结构信息
			generateDetailOutputDtoFile(resultSet, tableName, modelName, tableAnnotation,
					author);
			// 生成ListInputDto文件
			resultSet = databaseMetaData
					.getColumns(null, null, tableName, null);// 获取表结构信息
			generateListInputDtoFile(resultSet, tableName, modelName, tableAnnotation,
					author);
			// 生成ListOutputDto文件
			resultSet = databaseMetaData
					.getColumns(null, null, tableName, null);// 获取表结构信息
			generateListOutputDtoFile(resultSet, tableName, modelName, tableAnnotation,
					author);
			String table_key = "";
			String table_key_change = "";
			System.out.print("数据库表" + tableName);
			while (primaryKeyResultSet.next()) {
				table_key = primaryKeyResultSet.getString("COLUMN_NAME")
						.toLowerCase();
				table_key_change = replaceUnderLineAndUpperCase(primaryKeyResultSet
						.getString("COLUMN_NAME"));
				System.out.println("主键:" + table_key);
			}
			int rowCount = primaryKeyResultSet.getRow();
			if (rowCount >= 1) {// 存在主键
				
				
				// 生成Dao文件
				resultSet = databaseMetaData.getColumns(null, null, tableName,
						null);// 获取表结构信息
				generateDaoFile(resultSet, table_key, table_key_change,
						tableName, modelName, tableAnnotation, author);
				
				
				// 生成DaoImpl文件
				resultSet = databaseMetaData.getColumns(null, null, tableName,
						null);// 获取表结构信息
				generateDaoImplFile(resultSet, table_key, table_key_change,
						tableName, modelName, tableAnnotation, author);
				
				
				// 生成Service文件
				resultSet = databaseMetaData.getColumns(null, null, tableName,
						null);// 获取表结构信息
				generateServiceFile(resultSet, table_key, table_key_change,
						tableName, modelName, tableAnnotation, author);


				// 生成ServiceImpl文件
				resultSet = databaseMetaData.getColumns(null, null, tableName,
						null);// 获取表结构信息
				generateServiceImplFile(resultSet, table_key, table_key_change,
						tableName, modelName, tableAnnotation, author);


				// 生成Controller文件
				resultSet = databaseMetaData.getColumns(null, null, tableName,
						null);// 获取表结构信息
				generateControllerFile(resultSet, table_key, table_key_change,
						tableName, modelName, tableAnnotation, author);

				// 生成DelDto文件
				resultSet = databaseMetaData
						.getColumns(null, null, tableName, null);// 获取表结构信息
				generateDelDtoFile(resultSet, table_key, table_key_change,
						tableName, modelName, tableAnnotation, author);
				// 生成DetailInputDto文件
				resultSet = databaseMetaData
						.getColumns(null, null, tableName, null);// 获取表结构信息
				generateDetailInputDtoFile(resultSet, table_key, table_key_change,
						tableName, modelName, tableAnnotation,author);

			} else {
				System.out.println("提示:请为表" + tableName + "设置主键才能生成DaoImpl文件！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// close(resultSet, connection);
			// close(primaryKeyResultSet, connection);
		}
	}

	private void generateEntityFile(ResultSet resultSet, String tableName,
			String modelName, String tableAnnotation, String author)
			throws Exception {
		File file = new File(Constant.PACKAGE_PATH_ENTITY);
		if (!file.exists()) {
			file.mkdirs();
		}
		String hasDateType = "false";
		final String suffix = ".java";
		final String path = Constant.PACKAGE_PATH_ENTITY + modelName + "Entity"
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Entity.ftl";
			File mapperFile = new File(path);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
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
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				System.out.println(resultSet.getString("COLUMN_NAME")
						.toLowerCase()
						+ "\t"
						+ resultSet.getString("TYPE_NAME"));
				columnClassList.put(object.getString("ColumnName"), object);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("model_column", columnClassList);
			dataMap.put("package_name", Constant.ENTITY_PACKAGE);
			dataMap.put("hasDateType", hasDateType);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateDtoFile(ResultSet resultSet, String tableName,
			String modelName, String tableAnnotation, String author)
			throws Exception {
		File file = new File(Constant.PACKAGE_PATH_ENTITY);
		if (!file.exists()) {
			file.mkdirs();
		}
		String hasDateType = "false";
		final String suffix = ".java";
		final String path = Constant.PACKAGE_PATH_DTO + modelName + "Dto"
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Dto.ftl";
			File mapperFile = new File(path);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
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
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				System.out.println(resultSet.getString("COLUMN_NAME")
						.toLowerCase()
						+ "\t"
						+ resultSet.getString("TYPE_NAME"));
				columnClassList.put(object.getString("ColumnName"), object);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("model_column", columnClassList);
			dataMap.put("package_name", Constant.DTO_PACKAGE);
			dataMap.put("hasDateType", hasDateType);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateAddDtoFile(ResultSet resultSet, String tableName,
								 String modelName, String tableAnnotation, String author)
			throws Exception {
		File file = new File(Constant.PACKAGE_PATH_ENTITY);
		if (!file.exists()) {
			file.mkdirs();
		}
		String hasDateType = "false";
		final String suffix = ".java";
		final String path =  Constant.PACKAGE_PATH_DTO + "Add" + modelName + "InputDto"
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "AddDto.ftl";
			File mapperFile = new File(path);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
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
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				System.out.println(resultSet.getString("COLUMN_NAME")
						.toLowerCase()
						+ "\t"
						+ resultSet.getString("TYPE_NAME"));
				columnClassList.put(object.getString("ColumnName"), object);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("model_column", columnClassList);
			dataMap.put("package_name", Constant.DTO_PACKAGE);
			dataMap.put("hasDateType", hasDateType);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateDelDtoFile(ResultSet resultSet, String table_key,String table_key_change,
									String tableName, String modelName, String tableAnnotation, String author)
			throws Exception {
		File file = new File(Constant.PACKAGE_PATH_ENTITY);
		if (!file.exists()) {
			file.mkdirs();
		}
		String hasDateType = "false";
		final String suffix = ".java";
		final String path = Constant.PACKAGE_PATH_DTO + "Del" + modelName + "InputDto"
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "DelDto.ftl";
			File mapperFile = new File(path);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
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
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				System.out.println(resultSet.getString("COLUMN_NAME")
						.toLowerCase()
						+ "\t"
						+ resultSet.getString("TYPE_NAME"));
				columnClassList.put(object.getString("ColumnName"), object);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();

			// 获取主键及类型
			for (Entry<String, Object> entry : columnClassList.entrySet()) {
				if (table_key.equals(entry.getKey().toLowerCase())) {
					JSONObject jsonObject = (JSONObject) entry.getValue();
					String tablekeytype = jsonObject.getString("ColumnType");
					dataMap.put("table_key_type", tablekeytype);
				}
			}
			dataMap.put("table_key", table_key);
			dataMap.put("table_key_change", camelName(table_key));
			dataMap.put("model_column", columnClassList);

			dataMap.put("model_column", columnClassList);
			dataMap.put("package_name", Constant.DTO_PACKAGE);
			dataMap.put("hasDateType", hasDateType);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generatePutDtoFile(ResultSet resultSet, String tableName,
									String modelName, String tableAnnotation, String author)
			throws Exception {
		File file = new File(Constant.PACKAGE_PATH_ENTITY);
		if (!file.exists()) {
			file.mkdirs();
		}
		String hasDateType = "false";
		final String suffix = ".java";
		final String path = Constant.PACKAGE_PATH_DTO + "Put" + modelName + "InputDto"
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "PutDto.ftl";
			File mapperFile = new File(path);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
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
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				System.out.println(resultSet.getString("COLUMN_NAME")
						.toLowerCase()
						+ "\t"
						+ resultSet.getString("TYPE_NAME"));
				columnClassList.put(object.getString("ColumnName"), object);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("model_column", columnClassList);
			dataMap.put("package_name", Constant.DTO_PACKAGE);
			dataMap.put("hasDateType", hasDateType);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateDetailInputDtoFile(ResultSet resultSet, String table_key,
			String table_key_change,String tableName, String modelName, String tableAnnotation, String author)
			throws Exception {
		File file = new File(Constant.PACKAGE_PATH_ENTITY);
		if (!file.exists()) {
			file.mkdirs();
		}
		String hasDateType = "false";
		final String suffix = ".java";
		final String path = Constant.PACKAGE_PATH_DTO + modelName + "DetailInputDto"
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "DetailInputDto.ftl";
			File mapperFile = new File(path);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
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
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				System.out.println(resultSet.getString("COLUMN_NAME")
						.toLowerCase()
						+ "\t"
						+ resultSet.getString("TYPE_NAME"));
				columnClassList.put(object.getString("ColumnName"), object);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();

			// 获取主键及类型
			for (Entry<String, Object> entry : columnClassList.entrySet()) {
				if (table_key.equals(entry.getKey().toLowerCase())) {
					JSONObject jsonObject = (JSONObject) entry.getValue();
					String tablekeytype = jsonObject.getString("ColumnType");
					dataMap.put("table_key_type", tablekeytype);
				}
			}
			dataMap.put("table_key", table_key);
			dataMap.put("table_key_change", camelName(table_key));
			dataMap.put("model_column", columnClassList);

			dataMap.put("model_column", columnClassList);
			dataMap.put("package_name", Constant.DTO_PACKAGE);
			dataMap.put("hasDateType", hasDateType);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateDetailOutputDtoFile(ResultSet resultSet, String tableName,
									   String modelName, String tableAnnotation, String author)
			throws Exception {
		File file = new File(Constant.PACKAGE_PATH_ENTITY);
		if (!file.exists()) {
			file.mkdirs();
		}
		String hasDateType = "false";
		final String suffix = ".java";
		final String path = Constant.PACKAGE_PATH_DTO + modelName + "DetailOutputDto"
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "DetailOutputDto.ftl";
			File mapperFile = new File(path);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
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
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				System.out.println(resultSet.getString("COLUMN_NAME")
						.toLowerCase()
						+ "\t"
						+ resultSet.getString("TYPE_NAME"));
				columnClassList.put(object.getString("ColumnName"), object);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("model_column", columnClassList);
			dataMap.put("package_name", Constant.DTO_PACKAGE);
			dataMap.put("hasDateType", hasDateType);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateListInputDtoFile(ResultSet resultSet, String tableName,
											String modelName, String tableAnnotation, String author)
			throws Exception {
		File file = new File(Constant.PACKAGE_PATH_ENTITY);
		if (!file.exists()) {
			file.mkdirs();
		}
		String hasDateType = "false";
		final String suffix = ".java";
		final String path = Constant.PACKAGE_PATH_DTO + modelName + "ListInputDto"
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "ListInputDto.ftl";
			File mapperFile = new File(path);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
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
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				System.out.println(resultSet.getString("COLUMN_NAME")
						.toLowerCase()
						+ "\t"
						+ resultSet.getString("TYPE_NAME"));
				columnClassList.put(object.getString("ColumnName"), object);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("model_column", columnClassList);
			dataMap.put("package_name", Constant.DTO_PACKAGE);
			dataMap.put("hasDateType", hasDateType);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateListOutputDtoFile(ResultSet resultSet, String tableName,
											 String modelName, String tableAnnotation, String author)
			throws Exception {
		File file = new File(Constant.PACKAGE_PATH_ENTITY);
		if (!file.exists()) {
			file.mkdirs();
		}
		String hasDateType = "false";
		final String suffix = ".java";
		final String path = Constant.PACKAGE_PATH_DTO + modelName + "ListOutputDto"
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "ListOutputDto.ftl";
			File mapperFile = new File(path);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
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
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				System.out.println(resultSet.getString("COLUMN_NAME")
						.toLowerCase()
						+ "\t"
						+ resultSet.getString("TYPE_NAME"));
				columnClassList.put(object.getString("ColumnName"), object);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("model_column", columnClassList);
			dataMap.put("package_name", Constant.DTO_PACKAGE);
			dataMap.put("hasDateType", hasDateType);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateDaoFile(ResultSet resultSet, String table_key,
			String table_key_change, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		File file = new File(Constant.PACKAGE_PATH_DAO);
		if (!file.exists()) {
			file.mkdirs();
		}
		final String suffix = "Dao.java";
		final String path = Constant.PACKAGE_PATH_DAO + modelName + suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Dao.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("package_name", Constant.DAO_PACKAGE);
			dataMap.put("entityPackage", Constant.ENTITY_PACKAGE + "."
					+ modelName + "Entity");
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
				// 获取字段名称
				object.put("ColumnName", resultSet.getString("COLUMN_NAME")
						.toLowerCase());
				// 获取字段类型
				object.put("ColumnType", resultSet.getString("TYPE_NAME"));
				// 转换字段名称，如 sys_name 变成 SysName
				object.put("ChangeColumnName",
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				columnClassList.put(object.getString("ColumnName"), object);
			}
			// 获取主键及类型
			for (Entry<String, Object> entry : columnClassList.entrySet()) {
				if (table_key.equals(entry.getKey().toLowerCase())) {
					JSONObject jsonObject = (JSONObject) entry.getValue();
					String tablekeytype = jsonObject.getString("ColumnType");
					dataMap.put("table_key_type", tablekeytype);
				}
			}
			dataMap.put("table_key", table_key);
			dataMap.put("table_key_change", table_key_change);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateDaoImplFile(ResultSet resultSet, String table_key,
			String table_key_change, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		File file = new File(Constant.PACKAGE_PATH_DAO_IMPL);
		if (!file.exists()) {
			file.mkdirs();
		}
		final String suffix = "DaoImpl.java";
		final String path = Constant.PACKAGE_PATH_DAO_IMPL + modelName + suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "DaoImpl.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("package_name", Constant.DAO_IMPL_PACKAGE);
			dataMap.put("daoPackage", Constant.DAO_PACKAGE + "." + modelName
					+ "Dao");
			dataMap.put("entityPackage", Constant.ENTITY_PACKAGE + "."
					+ modelName + "Entity");
			dataMap.put("utilPackage", Constant.UTIL_PACKAGE);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
				// 获取字段名称
				object.put("ColumnName", resultSet.getString("COLUMN_NAME")
						.toLowerCase());
				// 获取字段类型
				object.put("ColumnType", resultSet.getString("TYPE_NAME"));
				// 转换字段名称，如 sys_name 变成 SysName
				object.put("ChangeColumnName",
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				columnClassList.put(object.getString("ColumnName"), object);
			}
			// 获取主键及类型
			for (Entry<String, Object> entry : columnClassList.entrySet()) {
				if (table_key.equals(entry.getKey().toLowerCase())) {
					JSONObject jsonObject = (JSONObject) entry.getValue();
					String tablekeytype = jsonObject.getString("ColumnType");
					dataMap.put("table_key_type", tablekeytype);
				}
			}
			dataMap.put("table_key", table_key);
			dataMap.put("table_key_change", camelName(table_key));
			dataMap.put("model_column", columnClassList);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}
	}

	private void generateServiceFile(ResultSet resultSet, String table_key,
			String table_key_change, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		File file = new File(Constant.PACKAGE_PATH_SERVICE);
		if (!file.exists()) {
			file.mkdirs();
		}
		final String suffix = "Service.java";
		final String path = Constant.PACKAGE_PATH_SERVICE + modelName + suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Service.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("package_name", Constant.SERVICE_PACKAGE);
			dataMap.put("dtoPackage", Constant.DTO_PACKAGE + "." + modelName
					+ "Dto");
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
				// 获取字段名称
				object.put("ColumnName", resultSet.getString("COLUMN_NAME")
						.toLowerCase());
				// 获取字段类型
				object.put("ColumnType", resultSet.getString("TYPE_NAME"));
				// 转换字段名称，如 sys_name 变成 SysName
				object.put("ChangeColumnName",
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				columnClassList.put(object.getString("ColumnName"), object);
			}
			// 获取主键及类型
			for (Entry<String, Object> entry : columnClassList.entrySet()) {
				if (table_key.equals(entry.getKey().toLowerCase())) {
					JSONObject jsonObject = (JSONObject) entry.getValue();
					String tablekeytype = jsonObject.getString("ColumnType");
					dataMap.put("table_key_type", tablekeytype);
				}
			}
			dataMap.put("table_key", table_key);
			dataMap.put("table_key_change", table_key_change);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateServiceImplFile(ResultSet resultSet, String table_key,
			String table_key_change, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		File file = new File(Constant.PACKAGE_PATH_SERVICE_IMPL);
		if (!file.exists()) {
			file.mkdirs();
		}
		final String suffix = "ServiceImpl.java";
		final String path = Constant.PACKAGE_PATH_SERVICE_IMPL + modelName
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "ServiceImpl.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("package_name", Constant.SERVICE_IMPL_PACKAGE);
			dataMap.put("daoPackage", Constant.DAO_PACKAGE + "." + modelName
					+ "Dao");
			dataMap.put("entityPackage", Constant.ENTITY_PACKAGE + "."
					+ modelName + "Entity");
			dataMap.put("dtoPackage", Constant.DTO_PACKAGE + "." + modelName
					+ "Dto");
			dataMap.put("servicePackage", Constant.SERVICE_PACKAGE + "."
					+ modelName + "Service");
			dataMap.put("utilPackage", Constant.UTIL_PACKAGE);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
				// 获取字段名称
				object.put("ColumnName", resultSet.getString("COLUMN_NAME")
						.toLowerCase());
				// 获取字段类型
				object.put("ColumnType", resultSet.getString("TYPE_NAME"));
				// 转换字段名称，如 sys_name 变成 SysName
				object.put("ChangeColumnName",
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				columnClassList.put(object.getString("ColumnName"), object);
			}
			// 获取主键及类型
			for (Entry<String, Object> entry : columnClassList.entrySet()) {
				if (table_key.equals(entry.getKey().toLowerCase())) {
					JSONObject jsonObject = (JSONObject) entry.getValue();
					String tablekeytype = jsonObject.getString("ColumnType");
					dataMap.put("table_key_type", tablekeytype);
				}
			}
			dataMap.put("table_key", table_key);
			dataMap.put("table_key_change", camelName(table_key));
			dataMap.put("model_column", columnClassList);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}
	}

	private void generateControllerFile(ResultSet resultSet, String table_key,
			String table_key_change, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		File file = new File(Constant.PACKAGE_PATH_CONTROLLER);
		if (!file.exists()) {
			file.mkdirs();
		}
		final String suffix = "Controller.java";
		final String path = Constant.PACKAGE_PATH_CONTROLLER + modelName
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Controller.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("package_name", Constant.CONTROLLER_PACKAGE);
			dataMap.put("daoPackage", Constant.DAO_PACKAGE + "." + modelName
					+ "Dao");
			dataMap.put("entityPackage", Constant.ENTITY_PACKAGE + "."
					+ modelName + "Entity");
			dataMap.put("dtoPackage", Constant.DTO_PACKAGE + "." + modelName
					+ "Dto");
			dataMap.put("servicePackage", Constant.SERVICE_PACKAGE + "."
					+ modelName + "Service");
			dataMap.put("utilPackage", Constant.UTIL_PACKAGE);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
				// 获取字段名称
				object.put("ColumnName", resultSet.getString("COLUMN_NAME")
						.toLowerCase());
				// 获取字段类型
				object.put("ColumnType", resultSet.getString("TYPE_NAME"));
				// 转换字段名称，如 sys_name 变成 SysName
				object.put("ChangeColumnName",
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				columnClassList.put(object.getString("ColumnName"), object);
			}
			// 获取主键及类型
			for (Entry<String, Object> entry : columnClassList.entrySet()) {
				if (table_key.equals(entry.getKey().toLowerCase())) {
					JSONObject jsonObject = (JSONObject) entry.getValue();
					String tablekeytype = jsonObject.getString("ColumnType");
					dataMap.put("table_key_type", tablekeytype);
				}
			}
			dataMap.put("table_key", table_key);
			dataMap.put("table_key_change", camelName(table_key));
			dataMap.put("model_column", columnClassList);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}
	}


	private void generateListHtmlFile(ResultSet resultSet, String table_key,
									 String table_key_change, String tableName, String modelName,
									 String tableAnnotation, String author) throws Exception {
		File file = new File(Constant.PACKAGE_PATH_CONTROLLER);
		if (!file.exists()) {
			file.mkdirs();
		}
		final String suffix = "list.html";
		final String path = Constant.PACKAGE_PATH_CONTROLLER + modelName + "_"
				+ suffix;
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "ListHtml.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("package_name", Constant.CONTROLLER_PACKAGE);
			dataMap.put("daoPackage", Constant.DAO_PACKAGE + "." + modelName
					+ "Dao");
			dataMap.put("entityPackage", Constant.ENTITY_PACKAGE + "."
					+ modelName + "Entity");
			dataMap.put("dtoPackage", Constant.DTO_PACKAGE + "." + modelName
					+ "Dto");
			dataMap.put("servicePackage", Constant.SERVICE_PACKAGE + "."
					+ modelName + "Service");
			dataMap.put("utilPackage", Constant.UTIL_PACKAGE);
			Map<String, Object> columnClassList = new HashMap<String, Object>();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				if (resultSet.getString("COLUMN_NAME").equals("id"))
					continue;
				// 获取字段名称
				object.put("ColumnName", resultSet.getString("COLUMN_NAME")
						.toLowerCase());
				// 获取字段类型
				object.put("ColumnType", resultSet.getString("TYPE_NAME"));
				// 转换字段名称，如 sys_name 变成 SysName
				object.put("ChangeColumnName",
						camelName(resultSet.getString("COLUMN_NAME")));
				// 字段在数据库的注释
				object.put(
						"Remarks",
						resultSet.getString("REMARKS") != null ? resultSet
								.getString("REMARKS") : "未定义");
				columnClassList.put(object.getString("ColumnName"), object);
			}
			// 获取主键及类型
			for (Entry<String, Object> entry : columnClassList.entrySet()) {
				if (table_key.equals(entry.getKey().toLowerCase())) {
					JSONObject jsonObject = (JSONObject) entry.getValue();
					String tablekeytype = jsonObject.getString("ColumnType");
					dataMap.put("table_key_type", tablekeytype);
				}
			}
			dataMap.put("table_key", table_key);
			dataMap.put("table_key_change", camelName(table_key));
			dataMap.put("model_column", columnClassList);
			generateFileByTemplate(templateName, mapperFile, dataMap,
					tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}
	}

	private void generateFileByTemplate(final String templateName, File file,
			Map<String, Object> dataMap, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
		FileOutputStream fos = new FileOutputStream(file);
		dataMap.put("table_name_small", tableName);
		dataMap.put("table_name", tableName);
		dataMap.put("modelName", modelName);
		dataMap.put("author", author);
		dataMap.put("date", CURRENT_DATE);
		dataMap.put("table_annotation", tableAnnotation);
		Writer out = new BufferedWriter(new OutputStreamWriter(fos), 10240);
		template.process(dataMap, out);
	}

	public String replaceUnderLineAndUpperCase(String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		int count = sb.indexOf("_");
		while (count != 0) {
			int num = sb.indexOf("_", count);
			count = num + 1;
			if (num != -1) {
				char ss = sb.charAt(count);
				char ia = (char) (ss - 32);
				sb.replace(count, count + 1, ia + "");
			}
		}
		String result = sb.toString().replaceAll("_", "");
		return StringUtils.capitalize(result);
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将字母小写
			return name.toLowerCase();
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->HELLO_WORLD
	 * 
	 * @param name
	 *            转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			// 将第一个字符处理成大写
			result.append(name.substring(0, 1).toUpperCase());
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase())
						&& !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s.toUpperCase());
			}
		}
		return result.toString();
	}
}