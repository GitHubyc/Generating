package ${package_name};

import java.util.ArrayList;
import java.util.List;

import com.xakj.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ${daoPackage};
import ${entityPackage};
import com.xakj.service.${modelName}Service;
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
	public Object save${modelName}(${modelName}Entity ${modelName?uncap_first}Entity) {

		${modelName?uncap_first}Entity.set${table_key_change?cap_first}(UuidUtil.get32UUID());
		${modelName?uncap_first}Dao.save(${modelName?uncap_first}Entity);

		return HttpResponse.SimpleSuccess.builder().status(HttpStatus.OK.value()).message("新增成功！").build();
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
	public Object delete${modelName}(${modelName}Entity ${modelName?uncap_first}Entity) {
		if (${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity) != 0) {
			${modelName?uncap_first}Dao.delete(${modelName?uncap_first}Entity.get${table_key_change?cap_first}());
		} else {
			throw new ServiceException("${table_annotation}不存在");
		}

		return HttpResponse.ComplexSuccess.builder().status(HttpStatus.OK.value()).message("删除成功！").build();
	}

	@Transactional
	@Override
	public Object update${modelName}(${modelName}Entity ${modelName?uncap_first}Entity) {
		${modelName}Entity entity = new ${modelName}Entity();
		entity.set${table_key_change?cap_first}(${modelName?uncap_first}Entity.get${table_key_change?cap_first}());
		if (${modelName?uncap_first}Dao.count(entity) != 0) {
			${modelName?uncap_first}Dao.update(${modelName?uncap_first}Entity);
		} else {
			throw new ServiceException("${table_annotation}不存在");
		}

		return HttpResponse.SimpleSuccess.builder().status(HttpStatus.OK.value()).message("更新成功！").build();
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
	public Object get${modelName}Detail(${modelName}Entity ${modelName?uncap_first}Entity) {
		if (${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity) != 0) {
			${modelName?uncap_first}Entity = ${modelName?uncap_first}Dao.findOne(${modelName?uncap_first}Entity).get(0);
		} else {
			throw new ServiceException("${table_annotation}不存在");
		}

		return HttpResponse.SimpleSuccess.builder().status(HttpStatus.OK.value()).message("查询完成").data(${modelName?uncap_first}Entity).build();
	}

	@Override
	public Object get${modelName}List(Integer currentPage, Integer pageSize, ${modelName}Entity ${modelName?uncap_first}Entity) {
		boolean bool = false;//判断分页参数
        if (!StringUtils.isEmpty(currentPage) && !StringUtils.isEmpty(pageSize)
                && currentPage > 0 && pageSize > 0) {
            bool = true;
        } else {
			currentPage = 1;
			pageSize = 9999;
        }

		int totalRows = ${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity);
		List<${modelName}Entity> ${modelName?uncap_first}Entities = ${modelName?uncap_first}Dao
			.findAll(currentPage, pageSize, ${modelName?uncap_first}Entity);

        if(bool){
        	return HttpResponse.ComplexSuccess.builder().status(HttpStatus.OK.value()).message("查询完成").currentPage(currentPage).pageSize(pageSize).totalRows(totalRows).data(${modelName?uncap_first}Entities).build();
        }
        return HttpResponse.SimpleSuccess.builder().status(HttpStatus.OK.value()).message("查询完成").data(${modelName?uncap_first}Entities).build();
	}
}
