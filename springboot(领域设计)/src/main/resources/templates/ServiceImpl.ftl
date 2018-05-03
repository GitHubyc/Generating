package ${package_name};

import java.util.ArrayList;
import java.util.List;

import com.xakj.bean.HttpReponse;
import com.xakj.util.UuidUtil;

import com.xakj.exception.custom.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ${daoPackage};
import ${entityPackage};
import com.xakj.service.dto.*;
import com.xakj.service.*;

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

		return HttpReponse.SimpleSuccess.builder().status(HttpStatus.OK.value()).message("新增成功！").build();
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

		return HttpReponse.SimpleSuccess.builder().status(HttpStatus.OK.value()).message("删除成功！").build();
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

		return HttpReponse.SimpleSuccess.builder().status(HttpStatus.OK.value()).message("更新成功！").build();
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

		return HttpReponse.SimpleSuccess.builder().status(HttpStatus.OK.value()).message("查询完成").data(${modelName?uncap_first}DetailOutputDto).build();
	}

	@Override
	public Object get${modelName}List(${modelName}ListInputDto ${modelName?uncap_first}ListInputDto) {
		boolean bool = false;//判断分页参数
        if (!StringUtils.isEmpty(${modelName?uncap_first}ListInputDto.getPage()) && !StringUtils.isEmpty(${modelName?uncap_first}ListInputDto.getPageSize())
                && ${modelName?uncap_first}ListInputDto.getPage() > 0 && ${modelName?uncap_first}ListInputDto.getPageSize() > 0) {
            bool = true;
        } else {
			${modelName?uncap_first}ListInputDto.setPage(1);
			${modelName?uncap_first}ListInputDto.setPageSize(9999);
        }

		${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
		BeanUtils.copyProperties(${modelName?uncap_first}ListInputDto,${modelName?uncap_first}Entity);

		int totalRows = ${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity);
		List<${modelName}Entity> ${modelName?uncap_first}Entities = ${modelName?uncap_first}Dao
			.findAll(${modelName?uncap_first}ListInputDto.getPage(), ${modelName?uncap_first}ListInputDto.getPageSize(), ${modelName?uncap_first}Entity);
		List<${modelName}Dto> ${modelName?uncap_first}Dtos = new ArrayList<${modelName}Dto>();
		for (int i = 0; i < ${modelName?uncap_first}Entities.size(); i++) {
			${modelName}Dto dto = new ${modelName}Dto();
			BeanUtils.copyProperties(${modelName?uncap_first}Entities.get(i),dto);
			${modelName?uncap_first}Dtos.add(dto);
		}

        if(bool){
        	return HttpReponse.ComplexSuccess.builder().status(HttpStatus.OK.value()).message("查询完成").currentPage(${modelName?uncap_first}ListInputDto.getPage()).pageSize(${modelName?uncap_first}ListInputDto.getPageSize()).totalRows(totalRows).data(${modelName?uncap_first}Dtos).build();
        }
        return HttpReponse.SimpleSuccess.builder().status(HttpStatus.OK.value()).message("查询完成").data(${modelName?uncap_first}Dtos).build();
	}
}
