package com.xiaofangyun;

import com.xiaofangyun.util.CodeGenerateUtils;

public class Application {

	public static void main(String[] args) {
		try {
			String tableName = "T_GRID";// 表名
			String modelName = "T_GRID";// 生成Model名称
			CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
			codeGenerateUtils.generate(tableName, modelName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
