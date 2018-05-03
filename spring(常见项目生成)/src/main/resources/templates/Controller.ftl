package ${package_name};

import com.xakj.service.${modelName}Service;
import com.xakj.model.${modelName}Entity;
import com.xakj.util.FormTransforUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 描述:${table_annotation}接口
 * 作者:${author}
 * 时间:${date}
 */
@Controller
public class ${modelName}Controller {
    
	@Autowired
	private ${modelName}Service ${modelName?uncap_first}Service;

	/**
	 * ${table_annotation}列表
	 */
	@RequestMapping(value = "/${modelName?uncap_first}/list", method = RequestMethod.GET)
	public String ${modelName?uncap_first}_list(
			ModelMap map,
			@RequestParam(value = "currentPage", defaultValue = "1", required = false) Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "12", required = false) Integer pageSize) {

		${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
		${modelName?uncap_first}Entity.setPage(currentPage);
		${modelName?uncap_first}Entity.setPageSize(pageSize);
		JSONObject object = JSONObject.fromObject(${modelName?uncap_first}Service.get${modelName}List(currentPage, pageSize, ${modelName?uncap_first}Entity));

		HashMap<String, Object> param = FormTransforUtil.JavaBeanToMap(${modelName?uncap_first}Entity);

		map.addAttribute("object", object);
		map.addAttribute("paramMap", param);

		return "${modelName?uncap_first}/${modelName}_list";
	}

	/**
	 * 跳转${table_annotation}新增
	 */
	@RequestMapping(value = "/${modelName?uncap_first}/add", method = RequestMethod.GET)
	public String ${modelName?uncap_first}_add() {

		return "${modelName?uncap_first}/${modelName}_add";
	}

	/**
	 * ${table_annotation}新增
	 */
	@RequestMapping(value = "/${modelName?uncap_first}/add", method = RequestMethod.POST)
	@ResponseBody
	public String ${modelName?uncap_first}_add(@RequestBody String requestbody) {
		JSONObject jSONObject = JSONObject.fromObject(requestbody);
		${modelName}Entity ${modelName?uncap_first}Entity = (${modelName}Entity)JSONObject.toBean(jSONObject, ${modelName}Entity.class);
		JSONObject object = JSONObject.fromObject(${modelName?uncap_first}Service.save${modelName}(${modelName?uncap_first}Entity));

		return object.toString();
	}

	/**
	 * 删除${table_annotation}
	 */
	@RequestMapping(value = "/${modelName?uncap_first}/del", method = RequestMethod.DELETE)
	@ResponseBody
	public String ${modelName?uncap_first}_del(@RequestParam(value = "${modelName?uncap_first}Id") String ${modelName?uncap_first}Id) {

		${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
		${modelName?uncap_first}Entity.set${modelName}Id(${modelName?uncap_first}Id);

		JSONObject object = JSONObject.fromObject(${modelName?uncap_first}Service.delete${modelName}(${modelName?uncap_first}Entity));

		return object.toString();
	}

	/**
	 * 跳转${table_annotation}更新
	 */
	@RequestMapping(value = "/${modelName?uncap_first}/edit", method = RequestMethod.GET)
	public String ${modelName?uncap_first}_edit(ModelMap map,
								  @RequestParam(value = "${modelName?uncap_first}Id") String ${modelName?uncap_first}Id) {
		${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
		${modelName?uncap_first}Entity.set${modelName}Id(${modelName?uncap_first}Id);
		JSONObject object = JSONObject.fromObject(${modelName?uncap_first}Service.get${modelName}Detail(${modelName?uncap_first}Entity));
		map.addAttribute("object", object);

		return "${modelName?uncap_first}/${modelName}_edit";
	}

	/**
	 * ${table_annotation}更新
	 */
	@RequestMapping(value = "/${modelName?uncap_first}/edit", method = RequestMethod.PUT)
	@ResponseBody
	public String ${modelName?uncap_first}_edit(@RequestBody String requestbody) {
		try {
			JSONObject jSONObject = JSONObject.fromObject(requestbody);
			${modelName}Entity ${modelName?uncap_first}Entity = (${modelName}Entity)JSONObject.toBean(jSONObject, ${modelName}Entity.class);
			JSONObject object = JSONObject.fromObject(${modelName?uncap_first}Service.update${modelName}(${modelName?uncap_first}Entity));

			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ${table_annotation}详情
	 */
	@RequestMapping(value = "/${modelName?uncap_first}/details", method = RequestMethod.GET)
	public String ${modelName?uncap_first}_details(ModelMap map,
								@RequestParam(value = "${modelName?uncap_first}Id") String ${modelName?uncap_first}Id) {
		${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
		${modelName?uncap_first}Entity.set${modelName}Id(${modelName?uncap_first}Id);
		JSONObject object = JSONObject.fromObject(${modelName?uncap_first}Service.get${modelName}Detail(${modelName?uncap_first}Entity));
		map.addAttribute("object", object);
		return "${modelName?uncap_first}/${modelName?uncap_first}_details";
	}
}
