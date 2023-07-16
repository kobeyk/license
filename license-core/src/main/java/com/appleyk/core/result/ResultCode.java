package com.appleyk.core.result;

/**
 * <p>请求响应结果码</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:35 下午 2020/8/21
 */
public enum ResultCode {

    OK(200,"成功"),
    INTERNAL(500,"内部错误"),

    /**
     * 定义内部错误码值
     * 字段码范围：10000 -- 10100
     */
    FIELD_MISSING(10000,"字段缺失"),
    FILED_VALUE_INVALID(10001,"字段值无效/不合法"),

    /**
     * 对象
     * 对象码范围：10100 -- 10200
     */
    OBJECT_NOT_EXIST(10101,"对象不存在"), // 修改删除对象时
    OBJECT_IS_EXIST(10102,"对象已存在"), // 创建对象时
    OBJECT_NAME_NOT_NULL(10103,"对象名称不能为空"),//创建对象时
    OBJECT_ID_NOT_NULL(10104,"对象ID不能为空"),//创建对象时
    OBJECT_NAME_REPEATED(10104,"对象名称重复"),//创建对象时

    /**
     * 授权／令牌请求接口返回码
     * 授权码范围：10200 -- 10300
     */
    INVALID_REQUEST(10200,"非法的请求"),
    INVALID_CLIENT(10201,"用户认证失败"),
    INVALID_GRANT(10202,"非法的授权信息"),
    EXPIRED_TOKEN(10203,"令牌过期"),
    ACCESS_DENIED(10204,"用户或授权服务器拒绝授予数据访问权限"),
    INVALID_USER_ID(10205,"无效的用户ID"),

    /**
     * 数据存储
     * 授权码范围：10300 -- 10400
     */
    DATA_CREATE_ERROR(10300,"数据创建失败"),
    DATA_DELETE_ERROR(10301,"数据删除失败"),
    DATA_UPDATE_ERROR(10302,"数据更新失败");


    /** 结果状态码*/
    private final Integer code;

    /**结果状态码表示的内容*/
    private final String name;

    ResultCode(final Integer code , final String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
