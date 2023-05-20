package com.aadvika.vsdemo.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.azure.key.vault.KeyVaultConstants;
import org.springframework.stereotype.Component;

import com.aadvika.vsdemo.process.ConvertDbToCsv;
import com.aadvika.vsdemo.process.LinesAggStrategy;
import com.aadvika.vsdemo.process.TrimHeaderAndTrailer;

//@Component
public class HelloWorldRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        // from("jms:queue:{{solace.queue.name}}?subscriptionName=SunilMacBook")
        // .routeId("ListenToSolaceRoute")
        // .log(LoggingLevel.INFO,"data from message: ${body}")
        // .to("atlasmap:users-mapping.adm")
        // .log(LoggingLevel.INFO,"Body of the message after transfer: ${body}");

        // from("timer:Azure-vault-timer?period=10000")
        // .routeId("GET_VAULT_FROM_AZURE")
        // .setHeader(KeyVaultConstants.SECRET_NAME, constant("database"))
        // .to("azure-key-vault:{{camel.vault.azure.vaultName}}?clientId=RAW({{camel.vault.azure.clientId}})&clientSecret={{camel.vault.azure.clientSecret}}&tenantId={{camel.vault.azure.tenantId}}&operation=getSecret")
        // .log(LoggingLevel.INFO,"Response from the vault: ${body}")
        // .log(LoggingLevel.INFO, "Header from the vault: ${headers}");

        from("file:src/data/input?fileName=Database File.txt&noop=true")
        .routeId("DB_PROFILE_TO_CSV_PROFILE")
        .process(new TrimHeaderAndTrailer())
        .log(LoggingLevel.DEBUG,"Data after Trim: ${body}")
        .split(body().tokenize("#"), new LinesAggStrategy())
        .process(new ConvertDbToCsv())
        .end()
        .log(LoggingLevel.INFO,"Data: ${body}")
        .to("file:src/data/output?fileName=DBTOCSVData.csv");

    }
    
}
