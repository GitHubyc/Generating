package com.xakj.util;

/**
 * 项目常量 施先锋
 */
public final class Constant {
	// 配置数据源
	public final static String URL = "jdbc:oracle:thin:@192.168.1.198:1521/orcl";
	public final static String USER = "alone";
	public final static String PASSWORD = "alone";
	public final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	// 项目包文件路径
	public final static String packageName = "com.xakj";
	//public final static String CONTROLLER_PACKAGE = packageName + ".api";// controller所在包
	public final static String CONTROLLER_PACKAGE = packageName + ".controller";// controller所在包
	public final static String ENTITY_PACKAGE = packageName + ".model";// entity所在包
	public final static String DTO_PACKAGE = packageName + ".service.dto";// dto所在包
	public final static String DAO_PACKAGE = packageName + ".dao";// dao所在包
	public final static String DAO_IMPL_PACKAGE = DAO_PACKAGE + ".impl";// daoImpl所在包
	public final static String SERVICE_PACKAGE = packageName + ".service";// Service所在包
	public final static String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";// ServiceImpl所在包
	public final static String UTIL_PACKAGE = packageName + ".util";// entity所在包

//	public final static String CONTROLLER_PACKAGE = packageName + ".test";// controller所在包
//	public final static String ENTITY_PACKAGE = packageName + ".test";// entity所在包
//	public final static String DTO_PACKAGE = packageName + ".test";// dto所在包
//	public final static String DAO_PACKAGE = packageName + ".test";// dao所在包
//	public final static String DAO_IMPL_PACKAGE = DAO_PACKAGE + ".test";// daoImpl所在包
//	public final static String SERVICE_PACKAGE = packageName + ".test";// Service所在包
//	public final static String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".test";// ServiceImpl所在包
//	public final static String UTIL_PACKAGE = packageName + ".test";// entity所在包
	// 磁盘文件根路径D:\intellij-work\new\build-service
	public final static String projectPath = "D://idea//myService//basicdata-server";
	//public final static String projectPath = "C://Users//Administrator//Desktop//code//unit-service";
	//public final static String projectPath = "D://intellij-work//fire-self-inspection-service";
	public final static String javaPath = projectPath + "//src//main//java//";
	public final static String PACKAGE_PATH_ENTITY = javaPath
			+ packageConvertPath(ENTITY_PACKAGE);// 生成的Entity存放路径
	public final static String PACKAGE_PATH_DTO = javaPath
			+ packageConvertPath(DTO_PACKAGE);// 生成的Entity存放路径
	public final static String PACKAGE_PATH_DAO = javaPath
			+ packageConvertPath(DAO_PACKAGE);
	public final static String PACKAGE_PATH_DAO_IMPL = javaPath
			+ packageConvertPath(DAO_IMPL_PACKAGE);
	public final static String PACKAGE_PATH_SERVICE = javaPath
			+ packageConvertPath(SERVICE_PACKAGE);// 生成的Service存放路径
	public final static String PACKAGE_PATH_SERVICE_IMPL = javaPath
			+ packageConvertPath(SERVICE_IMPL_PACKAGE);// 生成的Service实现存放路径
	public final static String PACKAGE_PATH_CONTROLLER = javaPath
			+ packageConvertPath(CONTROLLER_PACKAGE);// 生成的Controller存放路径

	public static String packageConvertPath(String packageName) {
		return String.format("/%s/",
				packageName.contains(".") ? packageName.replaceAll("\\.", "/")
						: packageName);
	}

}
