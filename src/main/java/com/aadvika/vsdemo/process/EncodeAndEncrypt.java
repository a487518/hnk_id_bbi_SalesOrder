package com.aadvika.vsdemo.process;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodeAndEncrypt implements Processor{

    Logger logger = LoggerFactory.getLogger(EncodeAndEncrypt.class);

    @Override
    public void process(Exchange exchange) throws Exception {
       String message = exchange.getIn().getBody(String.class);

       String encodedMessage = encryptString(message);
       String haString = getSHA1Hash(encodedMessage);

       exchange.getIn().setHeader("hashCode", haString);
       exchange.getIn().setBody(encodedMessage);

    }


    private String encryptString(String srcTxt) throws IOException {
		ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
		try (GZIPOutputStream zos = new GZIPOutputStream(rstBao)) {
		zos.write(srcTxt.getBytes());
    }
		byte[] bytes = rstBao.toByteArray();
		return Base64.getEncoder().encodeToString(bytes);
    }

    private String getSHA1Hash(String input) {
        String SHA1Hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
                SHA1Hash = hexStr;
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
        logger.error("Error while creating hascode", ex);;
        }
        return SHA1Hash;
        }
    
}
