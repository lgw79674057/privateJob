package com.xuaxi.framework.core.responses;

import javax.ws.rs.core.Response;

import com.xuaxi.framework.core.exceptions.ApiException;

public class ResponseBuilder {

    /**
     * 构建默认的成功响应信息
     *
     * @return
     */
    public static Response buildSuccessResponse() {
        BaseResponse<?> response = new BaseResponse<Object>();
        response.setStatus(Boolean.TRUE);
        return Response.status(Response.Status.OK).entity(response).build();

    }


    /**
     * 构建默认的成功响应信息
     *
     * @param obj
     * @return
     */
    public static <T> Response buildSuccessResponse(T obj) {
        BaseResponse<T> response = new BaseResponse<T>();
        response.setStatus(Boolean.TRUE);
        response.setResult(obj);
        return Response.status(Response.Status.OK).entity(response).build();

    }


    /**
     * 构建失败响应信息
     *
     * @param e
     * @return
     */
    public static Response buildFaildResponse(ApiException e) {
    	BaseResponse<?> response = new BaseResponse<Object>();
    	response.setStatus(Boolean.FALSE);
    	response.setError(new ErrorResponse(e.getCode(), e.getResponseMessage(), e.getResponseCode(),e.getErrorTargetData()));
        return Response.status( e.getResponseCode()).entity(response).build();
    }
    
    /**
     * 构建失败响应信息
     *
     * @param e
     * @return
     */
    public static Response buildFaildResponse(ApiException e,String referer) {
    	BaseResponse<?> response = new BaseResponse<Object>();
    	response.setStatus(Boolean.FALSE);
    	ErrorResponse err=new ErrorResponse(e.getCode(), e.getResponseMessage(), e.getResponseCode(),e.getErrorTargetData());
    	err.setReferer(referer);
    	response.setError(err);
        return Response.status( e.getResponseCode()).entity(response).build();
    }
}
