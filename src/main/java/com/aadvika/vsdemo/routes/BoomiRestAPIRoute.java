package com.aadvika.vsdemo.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aadvika.vsdemo.process.EncodeAndEncrypt;

//@Component
public class BoomiRestAPIRoute  extends RouteBuilder{

    @Value("${api.host}")
    private String host;

    @Value("${api.authorization}")
    private String auth;

    @Override
    public void configure() throws Exception {
        
        restConfiguration()
        .host(host)
        .port(443);

        // from("file:src/data/input?fileName=order.json")
        // .routeId("BoomiRestApiID")
        // .log(LoggingLevel.INFO,"Making request to https end point")
        // .setHeader("Authorization", constant("Basic "+{{api.authorization}}))
        // // .toD("https://${header.host}/${header.apiURI}")
        // .toD("rest:POST:{{api.path}}")
        // .log(LoggingLevel.INFO,"response body ${body}")
        // .log(LoggingLevel.INFO,"respose from API: ${header.CamelHttpResponseCode}")
        // .log(LoggingLevel.INFO,"response headers ${headers}");



        from("jms:queue:{{solace.queue.name}}?subscriptionName=SunilMacBook")
        .routeId("HNK_ID_12345_DIS_BBI_SalesOrder")
        .log(LoggingLevel.INFO,"Data from the file: ${body}")
        .process(new EncodeAndEncrypt())
        .setHeader("Authorization", simple("Basic "+ auth))
        .log(LoggingLevel.INFO,"Data after encoding: ${body}")
        .log(LoggingLevel.INFO,"Value of Hash Code: ${header.hashCode}")
        .toD("rest:PUT:{{api.path}}?hashCode=${header.hashCode}")
        .log(LoggingLevel.INFO,"Response Code: ${header.CamelHttpResponseCode}")
        .log(LoggingLevel.INFO,"Response from the OrlanSoft; ${body}");

    }
    
}
