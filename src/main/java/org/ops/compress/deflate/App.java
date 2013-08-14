package org.ops.compress.deflate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
        if (args.length > 0)
        {
            try {
                byte[] output = deflateAndBase64(args[0]);
                OutputStream out = new FileOutputStream("output.dat");
                out.write(output, 0, output.length);
                out.flush();
                out.close();
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            } 
        }
    }

    public static byte[] deflateAndBase64 (String message) throws Exception
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

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int read = 0;
            do {
              byte[] buffer = new byte[2048];
              read = inflater.read(buffer, 0, buffer.length);
              if (read > 0)
              {
                output.write(buffer, 0, read);
              }
            }
            while (read > 0);
             
            return output.toByteArray();
        }
        catch (Exception e)
        {
            throw new Exception("Unable to inflate SAML message", e);
        }
    } 
}


