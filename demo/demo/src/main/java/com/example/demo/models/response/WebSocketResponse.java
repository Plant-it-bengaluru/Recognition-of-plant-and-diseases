package com.example.demo.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketResponse {
    private Object jsonObject;
    private String message;
    private String sessionId;

    public WebSocketResponse(Object obj, String message, String sessionId){
        this.jsonObject = obj;
        this.message = message;
        this.sessionId = sessionId;
    }
}
