package com.aadvika.vsdemo.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrimHeaderAndTrailer implements Processor{

    Logger logger = LoggerFactory.getLogger(TrimHeaderAndTrailer.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String msg = exchange.getIn().getBody(String.class);
        int beginIndex = msg.indexOf("OUT_START")+14;
        logger.debug("Begin Index "+beginIndex);
        int endIndex = msg.indexOf("|#||#|")-1;
        logger.debug("End Index "+endIndex);
        String trimedMessage = msg.substring(beginIndex, endIndex);

        exchange.getIn().setBody(trimedMessage);

    }
    
}
