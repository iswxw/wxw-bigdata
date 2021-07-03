package com.wxw.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/3
 */
@Getter
@Setter
public class RRException extends RuntimeException{

    private String message;

    private int code = 500;

    /**
     * 直接使用message中的消息
     * @param message
     */
    public RRException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 将其他异常包装成本异常,使用自定义消息
     * @param message
     * @param e
     */
    public RRException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    /**
     * 直接使用自定义异常的消息
     * @param message
     * @param code
     */
    public RRException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    /**
     * 将其他异常包装成本异常,使用自定义消息
     * @param message
     * @param code
     * @param e
     */
    public RRException(String message, int code, Throwable e) {
        super(message, e);
        this.message = message;
        this.code = code;
    }
}
