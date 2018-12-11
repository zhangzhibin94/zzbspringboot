package com.xiniunet.domain;/**
 * Created by zzb on 2018/12/11.
 */

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zzb
 * @create 2018-12-11 11:19
 * @desc 错误对象
 **/
@Component
public class Error implements Serializable {
    private static final long serialVersionUID = 3L;

    /**
     * 添加此错误的类
     */
    private String clazz;

    /**
     * 添加此错误的方法
     */
    private String method;

    /**
     * 添加此错误的行号
     */
    private int lineNumber;

    private String code;

    private ErrorType type;

    private String message;

    public Error() {
    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Error(ErrorType type, String message) {
        this.type = type;
        this.code = type.toString();
        this.message = message;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }
}
