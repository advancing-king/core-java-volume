package com.corejava.chapter9.cryption;

import java.io.*;
import java.security.*;
import javax.crypto.*;

public class AESTest
{
   public static void main(String[] args) 
      throws IOException, GeneralSecurityException, ClassNotFoundException
   {
      if (args[0].equals("-genkey")) // ������Կ
      {
    	  // ��ȡ��Կ������
         KeyGenerator keygen = KeyGenerator.getInstance("AES");
         // �������Դ
         SecureRandom random = new SecureRandom();
         // �����Դ����ʼ����Կ������
         keygen.init(random);
         // ������Կ
         SecretKey key = keygen.generateKey();
         try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1])))
         {
            out.writeObject(key); // д����Կ���ļ�
         }
      }
      else // ���ܻ��߽���
      {
         int mode;
         // ���ܣ����ܣ�ģʽ
         if (args[0].equals("-encrypt")) mode = Cipher.ENCRYPT_MODE;
         else mode = Cipher.DECRYPT_MODE;

         // ����Դ��try ���, args[3]==secret.key
         try (ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
            InputStream in = new FileInputStream(args[1]);
            OutputStream out = new FileOutputStream(args[2]))
         {
            Key key = (Key) keyIn.readObject();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(mode, key); // ����ģʽ����Կ�����ʼ��
            Util.crypt(in, out, cipher);
         }
      }
   }
}

