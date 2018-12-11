package com.xiniunet.response;/**
 * Created by zzb on 2018/12/11.
 */

import com.xiniunet.domain.Error;
import com.xiniunet.domain.ErrorType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzb
 * @create 2018-12-11 11:17
 * @desc 返回结果响应
 **/
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 错误列表
     */
    private List<Error> errors;

    /**
     * @param code 错误码
     * @param message 错误描述
     */
    public void addError(String code, String message){
        Error error = new Error(code,message);
        try {
            StackTraceElement ste = new Throwable().getStackTrace()[1];
            error.setClazz(ste.getClassName());
            error.setMethod(ste.getMethodName());
            error.setLineNumber(ste.getLineNumber());
        } catch (Exception ignored) {
        }
        _getErrors().add(error);
    }

    /**
     * @param type 错误类型
     * @param message 错误描述
     */
    public void addError(ErrorType type, String message){
        Error error = new Error(type,message);
        try {
            StackTraceElement ste = new Throwable().getStackTrace()[1];
            error.setClazz(ste.getClassName());
            error.setMethod(ste.getMethodName());
            error.setLineNumber(ste.getLineNumber());
        } catch (Exception ignored) {
        }
        _getErrors().add(error);
    }

    public void addError(Error error){
        _getErrors().add( error);
    }

    /**
     * 批量添加错误
     * @param errors 错误列表
     */
    public void addErrors(List<Error> errors){
        for(Error error : errors){
            addError(error);
        }
    }

    /**
     * 获取全部的错误信息，返回的是副本
     */
    public List<Error> getErrors(){
        return new ArrayList<Error>( _getErrors());
    }

    public String getFirstErrorMessage(){
        if(hasError()){
            return getErrors().get(0).getMessage();
        }
        return "";
    }

    /**
     * 是否有错误
     */
    public boolean hasError() {
        return ( errors != null ) && !errors.isEmpty();
    }

    private List<Error> _getErrors(){
        if(errors == null){
            errors = new ArrayList<Error>();
        }
        return errors;
    }
}
