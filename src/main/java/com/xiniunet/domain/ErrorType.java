package com.xiniunet.domain;/**
 * Created by zzb on 2018/12/11.
 */

import org.springframework.stereotype.Component;

/**
 * @author zzb
 * @create 2018-12-11 11:21
 * @desc 错误类型
 **/
public enum ErrorType {
    /**
     * 唯一性错误，出现了不允许重复的内容
     */
    UNIQUENESS_ERROR,
    /**
     * 期待值为空	找不到想要的对象
     */
    EXPECTATION_NULL,
    /**
     * 业务错误	不符合业务逻辑的情况发生
     */
    BUSINESS_ERROR,
    /**
     * 系统错误	JDBC的错误等
     */
    SYSTEM_ERROR,
    /**
     * 非法的参数	无效，格式不对、非法值、越界等
     */
    INVALID_PARAMETER,
    /**
     * 其它未归类错误
     */
    OTHER,
    /**
     * 异常信息Dump
     */
    STACK_DUMP
}
