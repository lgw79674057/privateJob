package com.xuaxi.framework.core.exceptions;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.responses.ResponseBuilder;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<Exception> {
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		if(exception instanceof ApiException){
			return ResponseBuilder.buildFaildResponse( (ApiException)exception);
		}else if(exception instanceof NotFoundException){
			return ResponseBuilder.buildFaildResponse(new ApiException(404));
		}
		return ResponseBuilder.buildFaildResponse(new ApiException(500));
	}
}
