package ${package_name};

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.xakj.util.UuidUtil;

import com.xakj.util.ResultBase;
import com.xakj.util.ResultPage;
import com.xakj.exception.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ${daoPackage};
import ${entityPackage};
import com.xakj.service.dto.*;
import com.xakj.service.*;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.util.StringUtils;

/**
 * ${table_annotation}接口实现层
 * 作者:${author}
 *
时间:${date}
 */
@Service
public class ${modelName}ServiceImpl implements ${modelName}Service {
	@Autowired
	private ${modelName}Dao ${modelName?uncap_first}Dao;

	@Transactional
	@Override
	public Object save${modelName}(Add${modelName}InputDto add${modelName}InputDto) {
		${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
		BeanUtils.copyProperties(add${modelName}InputDto,${modelName?uncap_first}Entity);

		${modelName?uncap_first}Entity.set${table_key_change?cap_first}(UuidUtil.get32UUID());
		${modelName?uncap_first}Dao.save(${modelName?uncap_first}Entity);

		return ResultBase.builder().status(HttpStatus.OK.value()).msg("新增成功！").build();
	}

	@Transactional
	@Override
	<#assign tablekeytype="" />
	<#--主键类型-->
	<#if table_key_type?default('NVARCHAR2') == "NUMBER">
		<#assign tablekeytype="Integer" />
	<#elseif table_key_type?default('NVARCHAR2') == "FLOAT">
		<#assign tablekeytype="Float" />
	<#else>
		<#assign tablekeytype="String" />
	</#if>
	public Object delete${modelName}(Del${modelName}InputDto del${modelName}InputDto) {
		String ${table_key_change?uncap_first} = del${modelName}InputDto.get${table_key_change?cap_first}();
		${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
		${modelName?uncap_first}Entity.set${table_key_change?cap_first}(${table_key_change?uncap_first});
		if (${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity) != 0) {
			${modelName?uncap_first}Dao.delete(${table_key_change?uncap_first});
		} else {
			throw new ServiceException("${table_annotation}不存在");
		}

		return ResultBase.builder().status(HttpStatus.OK.value()).msg("删除成功！").build();
	}

	@Transactional
	@Override
	public Object update${modelName}(Put${modelName}InputDto put${modelName}InputDto) {
		String ${table_key_change?uncap_first} = put${modelName}InputDto.get${table_key_change?cap_first}();
		${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
		${modelName?uncap_first}Entity.set${table_key_change?cap_first}(${table_key_change?uncap_first});
		if (${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity) != 0) {
			BeanUtils.copyProperties(put${modelName}InputDto,${modelName?uncap_first}Entity);
			${modelName?uncap_first}Dao.update(${modelName?uncap_first}Entity);
		} else {
			throw new ServiceException("${table_annotation}不存在");
		}

		return ResultBase.builder().status(HttpStatus.OK.value()).msg("更新成功！").build();
	}

	@Override
	<#assign tablekeytype="" />
	<#--主键类型-->
	<#if table_key_type?default('NVARCHAR2') == "NUMBER">
		<#assign tablekeytype="Integer" />
	<#elseif table_key_type?default('NVARCHAR2') == "FLOAT">
		<#assign tablekeytype="Float" />
	<#else>
		<#assign tablekeytype="String" />
	</#if>
	public Object get${modelName}Detail(${modelName}DetailInputDto ${modelName?uncap_first}DetailInputDto) {
		String ${table_key_change?uncap_first} = ${modelName?uncap_first}DetailInputDto.get${table_key_change?cap_first}();
		${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
		${modelName?uncap_first}Entity.set${table_key_change?cap_first}(${table_key_change?uncap_first});
		${modelName}DetailOutputDto ${modelName?uncap_first}DetailOutputDto = new ${modelName}DetailOutputDto();
		if (${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity) != 0) {
			${modelName?uncap_first}Entity = ${modelName?uncap_first}Dao.findOne(${modelName?uncap_first}Entity).get(0);
			BeanUtils.copyProperties(${modelName?uncap_first}Entity,${modelName?uncap_first}DetailOutputDto);
		} else {
			throw new ServiceException("${table_annotation}不存在");
		}

		return ResultBase.builder().status(HttpStatus.OK.value()).msg("查询完成").data(${modelName?uncap_first}DetailOutputDto).build();
	}

	@Override
	public Object get${modelName}List(${modelName}ListInputDto ${modelName?uncap_first}ListInputDto) {
		boolean bool = false;//判断分页参数
        if (!StringUtils.isEmpty(${modelName?uncap_first}ListInputDto.getCurrentPage()) && !StringUtils.isEmpty(${modelName?uncap_first}ListInputDto.getPageSize())
                && ${modelName?uncap_first}ListInputDto.getCurrentPage() > 0 && ${modelName?uncap_first}ListInputDto.getPageSize() > 0) {
            bool = true;
        } else {
			${modelName?uncap_first}ListInputDto.setCurrentPage(1);
			${modelName?uncap_first}ListInputDto.setPageSize(9999);
        }

		${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
		BeanUtils.copyProperties(${modelName?uncap_first}ListInputDto,${modelName?uncap_first}Entity);

		int totalRows = ${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity);
		List<${modelName}Entity> ${modelName?uncap_first}Entities = ${modelName?uncap_first}Dao
			.findAll(${modelName?uncap_first}ListInputDto.getCurrentPage(), ${modelName?uncap_first}ListInputDto.getPageSize(), ${modelName?uncap_first}Entity);
		List<${modelName}Dto> ${modelName?uncap_first}Dtos = new ArrayList<${modelName}Dto>();
		for (int i = 0; i < ${modelName?uncap_first}Entities.size(); i++) {
			${modelName}Dto dto = new ${modelName}Dto();
			BeanUtils.copyProperties(${modelName?uncap_first}Entities.get(i),dto);
			${modelName?uncap_first}Dtos.add(dto);
		}

        if(bool){
        	return ResultPage.builder().status(HttpStatus.OK.value()).msg("查询完成").currentPage(${modelName?uncap_first}ListInputDto.getCurrentPage()).pageSize(${modelName?uncap_first}ListInputDto.getPageSize()).totalRows(totalRows).data(${modelName?uncap_first}Dtos).build();
        }
        return ResultBase.builder().status(HttpStatus.OK.value()).msg("查询完成").data(${modelName?uncap_first}Dtos).build();
	}
}
