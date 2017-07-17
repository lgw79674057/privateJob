package com.xuaxi.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuaxi.domain.AddressDomain;
import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.service.IAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Path("/v1/address")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "行政区划表 REST API", description = "行政区划表 REST API")
public class AddressApi {

	@Autowired
	private IAddressService addressService;
	
	@GET
	@Path("/{id}")
	@ApiOperation(value = "根据ID查询",  httpMethod = "GET", notes="根据ID查询")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response findById(@PathParam("id") Long id) {
		boolean flg=false;
		String father=null;
		String this_code=null;
		if(id!=-1){
			AddressDomain ad=new AddressDomain();
			ad.setId(id);
			List<AddressDomain> selects= addressService.findByBeanProp(ad);
			if(selects!=null&&selects.size()>0){
				father=selects.get(0).getFather();
				this_code=selects.get(0).getArea_code();
				flg=true;
			}
		}
		
		if(flg){
			return ResponseBuilder.buildSuccessResponse(asc(father,this_code));
		}else{
			return ResponseBuilder.buildSuccessResponse(desc());
		}
		
	}
	
	@GET
	@Path("/{id}/byParent")
	@ApiOperation(value = "根据ParentID查询",  httpMethod = "GET", notes="根据ParentID查询")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response findByParentId(@PathParam("id") Long id) {
		return ResponseBuilder.buildSuccessResponse(addressService.findByBeanPropName("father", addressService.findByPk(id).getArea_code()));
	}
	
	private Map<String,List<AddressDomain>> desc(){
		Map<String,List<AddressDomain>> map=new HashMap<String, List<AddressDomain>>();
		List<AddressDomain> level1=addressService.findByBeanPropName("father", "0");
		level1.get(0).setSelected(true);
		map.put("level1", level1);
		List<AddressDomain> level2=addressService.findByBeanPropName("father", level1.get(0).getArea_code());
		level2.get(0).setSelected(true);
		map.put("level2", level2);
		List<AddressDomain> level3=addressService.findByBeanPropName("father", level2.get(0).getArea_code());
		level3.get(0).setSelected(true);
		map.put("level3", level3);
		List<AddressDomain> level4=addressService.findByBeanPropName("father", level3.get(0).getArea_code());
		level4.get(0).setSelected(true);
		map.put("level4", level4);
		List<AddressDomain> level5=addressService.findByBeanPropName("father", level4.get(0).getArea_code());
		level5.get(0).setSelected(true);
		map.put("level5", level5);
		return map;
	}
	
	private Map<String,List<AddressDomain>> asc(String areacode,String thisCode){
		Map<String,List<AddressDomain>> map=new HashMap<String, List<AddressDomain>>();
		List<AddressDomain> level5=addressService.findByBeanPropName("father", areacode);
		for(int i=0;i<level5.size();i++){
			if(level5.get(i).getArea_code().equals(thisCode)){
				level5.get(i).setSelected(true);
				break;
			}
		}
		map.put("level5", level5);
		
		
		List<AddressDomain> level4Select=addressService.findByBeanPropName("area_code", areacode);
		List<AddressDomain> level4=addressService.findByBeanPropName("father", level4Select.get(0).getFather());
		for(int i=0;i<level4.size();i++){
			if(level4.get(i).getArea_code().equals(areacode)){
				level4.get(i).setSelected(true);
				break;
			}
		}
		map.put("level4", level4);
		
		
		List<AddressDomain> level3Select=addressService.findByBeanPropName("area_code", level4Select.get(0).getFather());
		List<AddressDomain> level3=addressService.findByBeanPropName("father", level3Select.get(0).getFather());
		for(int i=0;i<level3.size();i++){
			if(level3.get(i).getArea_code().equals(level4Select.get(0).getFather())){
				level3.get(i).setSelected(true);
				break;
			}
		}
		map.put("level3", level3);
		
		List<AddressDomain> level2Select=addressService.findByBeanPropName("area_code", level3Select.get(0).getFather());
		List<AddressDomain> level2=addressService.findByBeanPropName("father", level2Select.get(0).getFather());
		for(int i=0;i<level2.size();i++){
			if(level2.get(i).getArea_code().equals(level3Select.get(0).getFather())){
				level2.get(i).setSelected(true);
				break;
			}
		}
		map.put("level2", level2);
		
		List<AddressDomain> level1=addressService.findByBeanPropName("father", "0");
		
		for(int i=0;i<level1.size();i++){
			if(level1.get(i).getArea_code().equals(level2Select.get(0).getFather())){
				level1.get(i).setSelected(true);
				break;
			}
		}
		
		map.put("level1", level1);
		
		return map;
	}
}
