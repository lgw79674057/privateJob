package com.xuaxi.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.util.FileCopyUtils;

import com.alibaba.druid.util.Base64;
import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.framework.core.spring.PropertyConfigurer;
import com.xuaxi.framework.core.spring.SpringContextUtil;
import com.xuaxi.service.utils.ContentTypeUtil;
import com.xuaxi.service.utils.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Path("/v1/file")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "附件上传 REST API", description = "附件上传 REST API")
public class FileApi {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "附件上传", httpMethod = "POST", notes = "附件上传")
	@ApiResponses(value = {@ApiResponse(code = 200, response = String.class, message = "OK") })
	public Response upload(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition disposition, @Context HttpServletRequest request)
			throws IOException {
		String []hz=disposition.getFileName().split("\\.");
		String rname=new SimpleDateFormat("yyyyMMdd").format(new Date());
		String cfg=SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("file.upload.savePath");
		if(checkFile(hz[hz.length-1])){
			File f=new File(cfg+File.separator+rname);
			if(!f.isDirectory()){
				f.mkdirs();
			}
			rname+=File.separator+UUID.randomUUID().toString()+"."+hz[hz.length-1];
			FileOutputStream fos = new FileOutputStream(cfg+File.separator+rname);
			FileCopyUtils.copy(fileInputStream, fos);
			fos.flush();
			fos.close();
			fileInputStream.close();
			return ResponseBuilder.buildSuccessResponse(rname);
		}else{
			return ResponseBuilder.buildFaildResponse(new ApiException(2002));
		}
	}

	private boolean checkFile(String f) {
		String str=f.toUpperCase();
		if (str.equals("JPG")||str.equals("PNG")||str.equals("JPEG")||str.equals("GIF")||str.equals("BMP")) {
			return true;
		}
		return false;
	}
	
	
	@GET
    @Path("/{file}")
    @Produces(MediaType.APPLICATION_JSON) 
 	@ApiOperation(value = "附件下载", httpMethod = "GET", notes="附件下载")
 	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public void download(
    		@Context HttpServletRequest request,@Context HttpServletResponse response,	@PathParam("file") String file) throws IOException {
		sendFile(request, response, new String(Base64.base64ToByteArray(file), "UTF-8"));
	} 
	
	
	
	
	private void sendFile(HttpServletRequest request,HttpServletResponse response,String filePath) throws IOException {
		OutputStream out = response.getOutputStream();
		RandomAccessFile raf=null;
		try {
			raf = new RandomAccessFile(new File(SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("file.upload.savePath")+"/"+ filePath), "r");
			response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
			response.setHeader("Last-Modified",DateUtil.formatDate(new Date(),"EEE, d MMM yyyy HH:mm:ss")+" GMT");
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.setHeader("Connection", "close");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Content-Type","application/octet-stream;charset=UTF-8"); 
			response.setContentType(ContentTypeUtil.getContentType(filePath));
			byte buffer[] = new byte[(int)raf.length()];
			raf.read(buffer, 0, buffer.length);
			out.write(buffer, 0, buffer.length);
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(raf!=null){
				raf.close();	
			}	
			raf=null;
			out.close();
			out=null;
		}
	}
}
