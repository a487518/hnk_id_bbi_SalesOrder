package com.aadvika.vsdemo.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.azure.key.vault.KeyVaultConstants;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        // from("jms:queue:{{solace.queue.name}}?subscriptionName=SunilMacBook")
        // .routeId("ListenToSolaceRoute")
        // .log(LoggingLevel.INFO,"data from message: ${body}")
        // .to("atlasmap:users-mapping.adm")
        // .log(LoggingLevel.INFO,"Body of the message after transfer: ${body}");

        from("timer:Azure-vault-timer?period=10000")
        .routeId("GET_VAULT_FROM_AZURE")
        .setHeader(KeyVaultConstants.SECRET_NAME, constant("database"))
        .to("azure-key-vault:{{camel.vault.azure.vaultName}}?clientId=RAW({{camel.vault.azure.clientId}})&clientSecret={{camel.vault.azure.clientSecret}}&tenantId={{camel.vault.azure.tenantId}}&operation=getSecret")
        .log(LoggingLevel.INFO,"Response from the vault: ${body}")
        .log(LoggingLevel.INFO, "Header from the vault: ${headers}");
    }
    
}
