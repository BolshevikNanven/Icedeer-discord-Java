package org.discord.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;
    private String message;
    private Integer code;
    private T data;


    public JsonResponse() {
    }

    public JsonResponse(T data) {
        this.data = data;
    }
    public JsonResponse(Integer code,String msg) {
        this.code=code;
        this.message=msg;
    }

    public static JsonResponse success() {
        JsonResponse result = new JsonResponse<>();
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("成功");
        return result;
    }

    public static <T> JsonResponse<T> success(T data) {
        JsonResponse<T> result = new JsonResponse<>(data);
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("成功");
        return result;
    }

    public static JsonResponse error(Integer code, String msg) {
        JsonResponse result = new JsonResponse();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }
}
