package org.ops.compress.deflate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if (args.length > 0) {
	    try{
                deflateAndBase64(args[0]);
	    } catch (Exception e) {
		System.out.println(e);
	    }
	}
    }

    public static String deflateAndBase64 (String message) throws Exception
    {
        byte[] decodedBytes = Base64.decodeBase64(message);
        if(decodedBytes == null)
	{
            throw new UnsupportedEncodingException("Unable to Base64 decode incoming message");
        }
        
        try
	{
            ByteArrayInputStream bytesIn = new ByteArrayInputStream(decodedBytes);
            InflaterInputStream inflater = new InflaterInputStream(bytesIn, new Inflater(true));

	    String output = new String();

	    int read = 0;
	    do {
	      byte[] buffer = new byte[2048];
	      read = inflater.read(buffer, 0, buffer.length);

	      output = output + new String(buffer);
	    }
	    while (read > 0);
	     
	    System.out.println(output);
	    return output;
	} catch (Exception e) {
            throw new Exception("Unable to Base64 decode and inflate SAML message", e);
        }
    } 
}


