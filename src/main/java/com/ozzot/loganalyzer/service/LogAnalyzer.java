package com.ozzot.loganalyzer.service;

import com.ozzot.loganalyzer.httpmethods.HttpMethod;
import com.ozzot.loganalyzer.tokens.LogToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LogAnalyzer {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");

    public static ArrayList<LogToken> logTokenScanner(String path, LocalDateTime timeFrom, LocalDateTime timeTo) throws IOException {
        checkIsNotNull(path, timeFrom, timeTo);

        URLConnection connection = new URL(path).openConnection();
        ArrayList<LogToken> logTokensList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                if (line.contains("-0800")) {
                    LocalDateTime currentTime = LocalDateTime.parse(line.substring(line.indexOf("[") + 1, line.indexOf("]")), DATE_TIME_FORMATTER);

                    if (!currentTime.isAfter(timeTo) && !currentTime.isBefore(timeFrom)) {
                        logTokensList.add(new LogToken(getTime(line), getHttpMethod(line), getMessage(line)));
                    }
                }
            }
        }

        return logTokensList;
    }

    private static void checkIsNotNull(String path, LocalDateTime timeFrom, LocalDateTime timeTo) {
        if (path == null || timeFrom == null || timeTo == null) {
            throw new IllegalArgumentException("Arguments can't be Null");
        }
    }

    private static LocalDateTime getTime(String strLine) {
        return LocalDateTime.parse(strLine.substring(strLine.indexOf("[") + 1, strLine.indexOf("]")), DATE_TIME_FORMATTER);
    }

    private static String getMessage(String strLine) {
        String x = strLine.split(getHttpMethod(strLine).getMethod())[1];
        return x.substring(x.indexOf("/"));
    }

    private static HttpMethod getHttpMethod(String s) {
        return s.toUpperCase().contains(HttpMethod.GET.getMethod()) ? HttpMethod.GET : HttpMethod.POST;
    }

}
