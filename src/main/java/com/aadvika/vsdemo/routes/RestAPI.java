package com.aadvika.vsdemo.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointProperties.Logging;
import org.springframework.stereotype.Component;

import com.aadvika.vsdemo.process.ConvertDbToCsv;
import com.aadvika.vsdemo.process.LinesAggStrategy;
import com.aadvika.vsdemo.process.TrimHeaderAndTrailer;


@Component
public class RestAPI extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        restConfiguration()
        // .component("jetty")
        // .host("127.0.0.1")
        .port(80)
        .bindingMode(RestBindingMode.auto)
        .enableCORS(true);


        rest("/boomi/utilities/v1")
        .produces("text")
        .get("/version").to("direct:version")
        .post("/dbtocsv").type(String.class).to("direct:convertDBtoCSVProfile");


        from("direct:convertDBtoCSVProfile")
        .routeId("DB_PROFILE_TO_CSV_PROFILE")
        .process(new TrimHeaderAndTrailer())
        .log(LoggingLevel.DEBUG,"Data after Trim: ${body}")
        .split(body().tokenize("#"), new LinesAggStrategy())
        .process(new ConvertDbToCsv())
        .end()
        .log(LoggingLevel.INFO,"Data: ${body}");
        // .to("file:src/data/output?fileName=DBTOCSVData.csv");

        from("direct:version")
        .routeId("VERSION_ROUTE")
        .setBody(constant("version is 1.0"))
        .log(LoggingLevel.INFO,"get version route: ${body}");
    }
    
}
