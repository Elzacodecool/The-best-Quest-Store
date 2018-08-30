package com.codecool.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class CommonTest {

    @Mock
    private Headers headers;

    private Common common;

    @Mock
    private HttpExchange httpExchange;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProperCookie(){
        HttpCookie cookie = new HttpCookie("Session","cookie");
        when(httpExchange.getRequestHeaders()).thenReturn(headers);
        when(headers.getFirst("Cookie")).thenReturn("Session=cookie");
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.getValue());

        HttpCookie recievedCookie = Common.getCookie(httpExchange);
        System.out.println(recievedCookie.getValue()+ " recieved");
        assertEquals(cookie,recievedCookie);

        //when(httpExchange.getRequestHeaders().getFirst("Cookie")).thenReturn("cookie");

        //assertEquals(cookie.getValue(), new HttpCookie("Session","cookie"));
    }

}