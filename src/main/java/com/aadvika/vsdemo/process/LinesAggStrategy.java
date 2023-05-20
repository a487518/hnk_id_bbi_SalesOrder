package com.aadvika.vsdemo.process;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class LinesAggStrategy implements AggregationStrategy{

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if(oldExchange == null){
            return newExchange;
        }

        String oldLines = oldExchange.getIn().getBody(String.class);
        String newLine = newExchange.getIn().getBody(String.class);
        
        oldLines = oldLines +System.getProperty("line.separator")+newLine;

        oldExchange.getIn().setBody(oldLines);
        return oldExchange;
        
    }
    
}
