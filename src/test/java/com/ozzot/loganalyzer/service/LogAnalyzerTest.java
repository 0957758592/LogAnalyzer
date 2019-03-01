package com.ozzot.loganalyzer.service;

import com.ozzot.loganalyzer.tokens.LogToken;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class LogAnalyzerTest {
    private final String URL_PATH = "http://www.monitorware.com/en/logsamples/apache.php";
    private LocalDateTime timeFrom = LocalDateTime.parse("07/Mar/2004:16:10:02 -0800", LogAnalyzer.DATE_TIME_FORMATTER);
    private LocalDateTime timeTo = LocalDateTime.parse("07/Mar/2004:16:20:55 -0800", LogAnalyzer.DATE_TIME_FORMATTER);

    @Test
    public void logTokenScanner() throws IOException {
        //prepare
        String token1 = "LogToken{localDateTime=2004-03-07T16:10:02, httpMethod=GET, message='/mailman/listinfo/hsdivision HTTP/1.1\" 200 6291'}";
        String token2 = "LogToken{localDateTime=2004-03-07T16:11:58, httpMethod=GET, message='/twiki/bin/view/TWiki/WikiSyntax HTTP/1.1\" 200 7352'}";
        String token3 = "LogToken{localDateTime=2004-03-07T16:20:55, httpMethod=GET, message='/twiki/bin/view/Main/DCCAndPostFix HTTP/1.1\" 200 5253'}";
        ArrayList<LogToken> tokens = LogAnalyzer.logTokenScanner(URL_PATH, timeFrom, timeTo);

        //then
        assertEquals(token1, tokens.get(0).toString());
        assertEquals(token2, tokens.get(1).toString());
        assertEquals(token3, tokens.get(2).toString());

    }

    @Test(expected = IllegalArgumentException.class)
    public void logTokenScannerException() throws IOException {
        LogAnalyzer.logTokenScanner(null, timeFrom, timeTo);
    }
}