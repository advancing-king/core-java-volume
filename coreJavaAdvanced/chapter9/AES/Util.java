package com.corejava.chapter9.cryption;

import java.io.*;
import java.security.*;
import javax.crypto.*;

public class Util
{
   public static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException,
         GeneralSecurityException
   {
      int blockSize = cipher.getBlockSize();
      int outputSize = cipher.getOutputSize(blockSize);
      byte[] inBytes = new byte[blockSize];
      byte[] outBytes = new byte[outputSize];

      int inLength = 0;
      boolean more = true;
      while (more)
      {
    	  // inBytes ����һ��������
         inLength = in.read(inBytes);
         if (inLength == blockSize)
         {
        	// ֻҪ�������ݿ����ȫ���ȣ������ܹ���8�������� ��Ҫ����update������ 
            int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
            out.write(outBytes, 0, outLength);
         }
         else more = false;
      }
      // ������������ݿ鲻����ȫ���ȣ����Ȳ��ܱ�8��������ʱ��Ҫ��䣩: ��Ҫ���� doFinal ������
      if (inLength > 0) 
    	  outBytes = cipher.doFinal(inBytes, 0, inLength);
      else
    	  outBytes = cipher.doFinal();
      out.write(outBytes);
   }
}
