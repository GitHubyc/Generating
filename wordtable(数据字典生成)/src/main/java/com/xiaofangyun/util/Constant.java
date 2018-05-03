package com.xiaofangyun.util;

/**
 * 项目常量 施先锋
 */
public final class Constant {
	// 配置数据源
	public final static String URL = "jdbc:oracle:thin:@192.168.1.18:1521:ORCL";
	public final static String USER = "build_sz";
	public final static String PASSWORD = "build_sz_3458";
	public final static String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// 磁盘文件根路径
	public final static String projectPath = "G:\\";

	public static String packageConvertPath(String packageName) {
		return String.format("/%s/",
				packageName.contains(".") ? packageName.replaceAll("\\.", "/")
						: packageName);
	}

}
