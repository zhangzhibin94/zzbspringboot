package com.xiniunet.domain;/**
 * Created by zzb on 2018/12/11.
 */

/**
 * @author zzb
 * @create 2018-12-11 13:52
 * @desc 返回成功信息domain
 **/
public class SuccessStatus extends BaseDomain{
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
