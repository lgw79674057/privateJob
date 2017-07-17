/**
 * 给swagger 添加Authorization
 */
package io.swagger.jaxrs.listing.xuaxi;

import io.swagger.annotations.ApiOperation;
import io.swagger.jaxrs.listing.BaseApiListingResource;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.HeaderParameter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.xuaxi.framework.core.spring.PropertyConfigurer;
import com.xuaxi.framework.core.spring.SpringContextUtil;

@Path("/swagger.json")
public class ZxApiListingResource extends BaseApiListingResource {

	@Context
	ServletContext context;
	
	private String tokenKey; 

	@GET
	@Produces({ "application/json" })
	@ApiOperation(value = "The swagger definition in either JSON or YAML", hidden = true)
	public Response getListing(@Context Application app, @Context ServletConfig sc, @Context HttpHeaders headers, @Context UriInfo uriInfo, @PathParam("type") String type) {
		Response response = getListingJsonResponse(app, this.context, sc, headers, uriInfo);
		Swagger swagger = (Swagger) response.getEntity();
		tokenKey=SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("rbac.token.key");
		for (io.swagger.models.Path path : swagger.getPaths().values()) {
			if (path.getPost() != null) {
				path.getPost().addParameter(getHeaderParameter());
			}
			if (path.getGet() != null) {
				path.getGet().addParameter(getHeaderParameter());
			}
			if (path.getPatch() != null) {
				path.getPatch().addParameter(getHeaderParameter());
			}
			if (path.getDelete() != null) {
				path.getDelete().addParameter(getHeaderParameter());
			}
			if (path.getPut() != null) {
				path.getPut().addParameter(getHeaderParameter());
			}

			if (path.getHead() != null) {
				path.getHead().addParameter(getHeaderParameter());
			}

			if (path.getOptions() != null) {
				path.getOptions().addParameter(getHeaderParameter());
			}
		}
		return response;
	}

	private HeaderParameter getHeaderParameter() {
		HeaderParameter h = new HeaderParameter();
		h.setName(tokenKey);
		h.setType("string");
		h.setRequired(false);
		h.setDescription("系统认证Token");
		return h;
	}
}