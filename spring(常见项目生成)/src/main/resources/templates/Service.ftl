package ${package_name};

import com.xakj.model.*;

/**
 * ${table_annotation}接口层
 * 作者:${author}
 * 时间:${date}
 */
public interface ${modelName}Service {

	/**
	 * 新增${table_annotation}
	 */
	public Object save${modelName}(${modelName}Entity add${modelName}InputDto);

	/**
	 * 删除${table_annotation}
	 */
	<#assign tablekeytype="" />
	<#--主键类型-->
	<#if table_key_type?default('NVARCHAR2') == "NUMBER">
		<#assign tablekeytype="Integer" />
	<#elseif table_key_type?default('NVARCHAR2') == "FLOAT">
		<#assign tablekeytype="Float" />
	<#else>
		<#assign tablekeytype="String" />
	</#if>
	public Object delete${modelName}(${modelName}Entity del${modelName}InputDto);

	/**
	 * 更新${table_annotation}
	 */
	public Object update${modelName}(${modelName}Entity put${modelName}InputDto);

	/**
	 * ${table_annotation}详细
	 */
	public Object get${modelName}Detail(${modelName}Entity ${modelName?uncap_first}DetailInputDto);

	/**
	 * ${table_annotation}列表
	 */
	public Object get${modelName}List(Integer currentPage, Integer pageSize, ${modelName}Entity ${modelName?uncap_first}ListInputDto);
}
