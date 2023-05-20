package com.aadvika.vsdemo.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertDbToCsv implements Processor{

    Logger logger = LoggerFactory.getLogger(ConvertDbToCsv.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        String msg = exchange.getIn().getBody(String.class);
        logger.debug("Line Index: "+exchange.getProperty("CamelSplitIndex"));
        logger.debug("Message: "+msg);
        String csvMessage = msg.replace("|^|", ",").replace("|", "").trim();

        exchange.getIn().setBody(csvMessage);
    }
    
}
