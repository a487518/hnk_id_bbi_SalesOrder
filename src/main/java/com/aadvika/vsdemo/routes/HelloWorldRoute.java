package com.aadvika.vsdemo.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class HelloWorldRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        from("jms:queue:{{solace.queue.name}}?subscriptionName=SunilMacBook")
        .routeId("ListenToSolaceRoute")
        .log(LoggingLevel.INFO,"data from message: ${body}")
        .to("atlasmap:users-mapping.adm")
        .log(LoggingLevel.INFO,"Body of the message after transfer: ${body}");
    }
    
}
