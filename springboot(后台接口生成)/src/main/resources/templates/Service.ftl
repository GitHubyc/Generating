package ${package_name};

import com.xakj.service.dto.*;

/**
 * ${table_annotation}接口层
 * 作者:${author}
 * 时间:${date}
 */
public interface ${modelName}Service {

	/**
	 * 新增${table_annotation}
	 */
	public Object save${modelName}(Add${modelName}InputDto add${modelName}InputDto);

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
	public Object delete${modelName}(Del${modelName}InputDto del${modelName}InputDto);

	/**
	 * 更新${table_annotation}
	 */
	public Object update${modelName}(Put${modelName}InputDto put${modelName}InputDto);

	/**
	 * ${table_annotation}详细
	 */
	public Object get${modelName}Detail(${modelName}DetailInputDto ${modelName?uncap_first}DetailInputDto);

	/**
	 * ${table_annotation}列表
	 */
	public Object get${modelName}List(${modelName}ListInputDto ${modelName?uncap_first}ListInputDto);
}
