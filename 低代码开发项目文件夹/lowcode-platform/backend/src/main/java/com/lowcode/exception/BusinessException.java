package com.lowcode.exception;

/**
 * 业务异常类
 * 用于处理业务逻辑中的异常情况
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
public class BusinessException extends RuntimeException {
    
    /**
     * 错误码
     */
    private final Integer code;
    
    /**
     * 错误信息
     */
    private final String message;
    
    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }
    
    /**
     * 构造函数
     *
     * @param code 错误码
     * @param message 错误信息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    /**
     * 构造函数
     *
     * @param message 错误信息
     * @param cause 原始异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
        this.message = message;
    }
    
    /**
     * 构造函数
     *
     * @param code 错误码
     * @param message 错误信息
     * @param cause 原始异常
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
    
    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public Integer getCode() {
        return code;
    }
    
    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    @Override
    public String getMessage() {
        return message;
    }
    
    /**
     * 创建业务异常
     *
     * @param message 错误信息
     * @return BusinessException
     */
    public static BusinessException of(String message) {
        return new BusinessException(message);
    }
    
    /**
     * 创建业务异常
     *
     * @param code 错误码
     * @param message 错误信息
     * @return BusinessException
     */
    public static BusinessException of(Integer code, String message) {
        return new BusinessException(code, message);
    }
}