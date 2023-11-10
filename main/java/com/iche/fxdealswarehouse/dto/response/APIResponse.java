package com.iche.fxdealswarehouse.dto.response;

import com.iche.fxdealswarehouse.utils.DateUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse <T>{
    private String message;
    private String time;
    private T payload;

    public APIResponse(T payload){
        this.message = "successful";
        this.time = DateUtils.saveDate(LocalDateTime.now());
        this.payload =  payload;
    }

}
