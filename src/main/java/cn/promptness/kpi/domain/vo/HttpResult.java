package cn.promptness.kpi.domain.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 返回前台数据类型
 *
 * @author linhuid
 */
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = -1949953568135344286L;

    private final static HttpResult DEFAULT = new HttpResult(200, "DEFAULT");

    private final static HttpResult ERROR = new HttpResult(500, "ERROR");

    private final static HttpResult SUCCESS = new HttpResult(0, "SUCCESS");

    private final static HttpResult ENTITY_EMPTY = new HttpResult(204, "ENTITY_EMPTY");

    private int code;
    private String message;
    private T content = null;

    public HttpResult() {
        super();
    }

    public HttpResult(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static HttpResult getSuccessHttpResult() {
        return new HttpResult(SUCCESS.code, SUCCESS.message);
    }

    public static <T> HttpResult<T> getSuccessHttpResult(T content) {
        HttpResult<T> httpResult = new HttpResult<>(SUCCESS.code, SUCCESS.message);
        httpResult.setContent(content);
        return httpResult;
    }

    public static HttpResult getErrorHttpResult() {
        return new HttpResult(ERROR.code, ERROR.message);
    }


    public static <T> HttpResult<T> getErrorHttpResult(T content) {
        HttpResult<T> httpResult = new HttpResult<>(ERROR.code, ERROR.message);
        httpResult.setContent(content);
        return httpResult;
    }

    public static HttpResult getDefaultHttpResult() {
        return new HttpResult(DEFAULT.code, DEFAULT.message);
    }

    public static <T> HttpResult<T> getDefaultHttpResult(T content) {
        HttpResult<T> httpResult = new HttpResult<>(DEFAULT.code, DEFAULT.message);
        httpResult.setContent(content);
        return httpResult;
    }

    public static HttpResult getEmptyHttpResult() {
        return new HttpResult(ENTITY_EMPTY.code, ENTITY_EMPTY.message);
    }


    public static <T> HttpResult<T> getEmptyHttpResult(T content) {
        HttpResult<T> httpResult = new HttpResult<>(ENTITY_EMPTY.code, ENTITY_EMPTY.message);
        httpResult.setContent(content);
        return httpResult;
    }

    public int getCode() {
        return code;
    }

    public HttpResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }


    public HttpResult setMessage(String message) {
        this.message = message;
        return this;
    }


    public T getContent() {
        return content;
    }

    public HttpResult setContent(T content) {
        this.content = content;
        return this;
    }


    @JsonIgnore
    public boolean isSuccess() {
        return this.code == SUCCESS.code;
    }

    @JsonIgnore
    public boolean isFailed() {
        return !isSuccess();
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }


}
