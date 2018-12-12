package com.xiniunet.request;/**
 * Created by zzb on 2018/12/12.
 */

/**
 * @author zzb
 * @create 2018-12-12 11:46
 * @desc 通过手机号、验证码登录request
 **/
public class LoginByTelephoneRequest extends BaseRequest{
    /**
     * 手机号
     */
    private Long telephone;
    /**
     * 验证码
     */
    private String code;

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
