package com.example.demo;

public class ThreadLocalVariable {
    private static ThreadLocal<String> sessionId = new ThreadLocal<>();

    public static void setSessionId(String value){
        sessionId.set(value);
    }

    public static String getSessionId(){
        return sessionId.get();
    }
}
