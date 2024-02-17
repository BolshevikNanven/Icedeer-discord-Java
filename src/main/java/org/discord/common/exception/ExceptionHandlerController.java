package org.discord.common.exception;

import com.alibaba.fastjson.JSONObject;
import org.discord.common.entity.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public JsonResponse authenticationHandler(Exception e){
        return JsonResponse.error(403,e.getMessage());
    }
}
