package ${package_name};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xakj.service.dto.*;
import com.xakj.service.*;

/**
 * 描述:${table_annotation}接口
 * 作者:${author}
 * 时间:${date}
 */
@Api(tags = "${table_annotation}")
@RestController
public class ${modelName}Controller {
    
	@Autowired
	private ${modelName}Service ${modelName?uncap_first}Service;

	@ApiOperation(value = "新增${table_annotation}", notes = "新增${table_annotation}")
	@RequestMapping(value = "/${modelName?uncap_first}", method = RequestMethod.POST)
	public Object add${modelName}(
			@Validated @RequestBody Add${modelName}InputDto add${modelName}InputDto) {
		return ${modelName?uncap_first}Service.save${modelName}(add${modelName}InputDto);
	}
	<#assign tablekeytype="" />
	<#--主键类型-->
	<#if table_key_type?default('NVARCHAR2') == "NUMBER">
		<#assign tablekeytype="Integer" />
	<#elseif table_key_type?default('NVARCHAR2') == "FLOAT">
		<#assign tablekeytype="Float" />
	<#else>
		<#assign tablekeytype="String" />
	</#if>

	@ApiOperation(value = "删除${table_annotation}", notes = "删除${table_annotation}")
	@RequestMapping(value = "/${modelName?uncap_first}/{${table_key_change?uncap_first}}", method = RequestMethod.DELETE)
	public Object del${modelName}(
			@PathVariable(name = "${table_key_change?uncap_first}") String ${table_key_change?uncap_first},
			@Validated @ModelAttribute Del${modelName}InputDto del${modelName}InputDto) {
		del${modelName}InputDto.set${table_key_change?cap_first}(${table_key_change?uncap_first});
		return ${modelName?uncap_first}Service.delete${modelName}(del${modelName}InputDto);
	}

	@ApiOperation(value = "更新${table_annotation}", notes = "更新${table_annotation}")
	@RequestMapping(value = "/${modelName?uncap_first}/{${table_key_change?uncap_first}}", method = RequestMethod.PUT)
	public Object put${modelName}(
			@PathVariable(name = "${table_key_change?uncap_first}") String ${table_key_change?uncap_first},
			@Validated @RequestBody Put${modelName}InputDto put${modelName}InputDto) {
		put${modelName}InputDto.set${table_key_change?cap_first}(${table_key_change?uncap_first});
		return ${modelName?uncap_first}Service.update${modelName}(put${modelName}InputDto);
	}

	@ApiOperation(value = "${table_annotation}详细", notes = "${table_annotation}详细")
	@RequestMapping(value = "/${modelName?uncap_first}/{${table_key_change?uncap_first}}", method = RequestMethod.GET)
	public Object ${modelName?uncap_first}Detail(
			@PathVariable(name = "${table_key_change?uncap_first}") String ${table_key_change?uncap_first},
			@Validated @ModelAttribute ${modelName}DetailInputDto ${modelName?uncap_first}DetailInputDto) {
		${modelName?uncap_first}DetailInputDto.set${table_key_change?cap_first}(${table_key_change?uncap_first});
		return ${modelName?uncap_first}Service.get${modelName}Detail(${modelName?uncap_first}DetailInputDto);
	}

	@ApiOperation(value = "${table_annotation}列表", notes = "${table_annotation}列表")
	@RequestMapping(value = "/${modelName?uncap_first}", method = RequestMethod.GET)
	public Object ${modelName?uncap_first}List(
			@Validated @ModelAttribute ${modelName}ListInputDto ${modelName?uncap_first}ListInputDto ) {
		return ${modelName?uncap_first}Service.get${modelName}List(${modelName?uncap_first}ListInputDto);
	}
}
