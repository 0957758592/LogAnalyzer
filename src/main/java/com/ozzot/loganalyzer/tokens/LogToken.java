package com.ozzot.loganalyzer.tokens;

import com.ozzot.loganalyzer.httpmethods.HttpMethod;

import java.time.LocalDateTime;

public class LogToken {
    private LocalDateTime localDateTime;
    private HttpMethod httpMethod;
    private String message;

    public LogToken(LocalDateTime localDateTime, HttpMethod httpMethod, String message) {
        this.localDateTime = localDateTime;
        this.httpMethod = httpMethod;
        this.message = message;
    }

    @Override
    public String toString() {
        return "LogToken{" +
                "localDateTime=" + localDateTime +
                ", httpMethod=" + httpMethod +
                ", message='" + message + '\'' +
                '}';
    }
}
