package com.example.demo;

import com.example.demo.models.response.WebSocketResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint(value = "/channel/log")
@Slf4j
public class LogChannel {

    public static final ConcurrentMap<String, LogChannel> CHANNELS = new ConcurrentHashMap<>();

    private Session session;

    @OnMessage() // MaxMessage 1 byte
    public void onMessage(String message) {
        if(!message.equals("ping"))
            log.info("Recv Message: {}", message);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;
        this.session.setMaxIdleTimeout(0);
        CHANNELS.put(this.session.getId(), this);

        log.info("New client connection: id={}", this.session.getId());
    }

    @OnClose
    public void onClose(CloseReason closeReason) {

        log.info("Connection disconnected: id={}, err={}", this.session.getId(), closeReason);

        CHANNELS.remove(this.session.getId());
    }

    @OnError
    public void onError(Throwable throwable) throws IOException {
        log.info("Connection Error: id={}, err={}", this.session.getId(), throwable);
        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }

    public static void push(Object obj, String objectType, String sessionId) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(obj);
            WebSocketResponse response = new WebSocketResponse(obj, objectType, sessionId);
            CHANNELS.values().stream().forEach(endpoint -> {
                if (endpoint.session.isOpen()) {
                    try {
                        endpoint.session.getAsyncRemote().sendText(ow.writeValueAsString(response));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}