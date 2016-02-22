package com.corejava.chapter9.cryption;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class RSATest
{
	private static final int KEYSIZE = 512;
	public static void main(String[] args) throws GeneralSecurityException, IOException, ClassNotFoundException 
	{
		if(args[0].equals("-genkey"))
		{
			// ��Կ��������
			KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
			SecureRandom sr = new SecureRandom();
			pairgen.initialize(KEYSIZE, sr);
			KeyPair pair = pairgen.generateKeyPair(); // ������Կ��
			
			try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1])))
			{
				out.writeObject(pair.getPublic()); // д�빫Կ���ļ�
			}
			try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[2])))
			{
				out.writeObject(pair.getPrivate()); // д��˽Կ���ļ�
			}
		}
		else if(args[0].equals("-encrypt")) // ����ģ��
		{
			// ������Կ
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			SecureRandom sr = new SecureRandom();
			keygen.init(sr);
			SecretKey key = keygen.generateKey();
			
			// wrap with RSA public key
			try(ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
				DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(args[2]));
					InputStream in = new FileInputStream(args[1]))
			{
				Key publicKey = (Key)keyIn.readObject();
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.WRAP_MODE, publicKey);
				byte[] wrappedKey = cipher.wrap(key);
				dataOut.writeInt(wrappedKey.length);
				dataOut.write(wrappedKey);
				
				cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, key);
				Util.crypt(in, dataOut, cipher);
			} 
		}
		else // ����ģ��
		{
			try(DataInputStream dataIn = new DataInputStream(new FileInputStream(args[1]));
					ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
						OutputStream out = new FileOutputStream(args[2]))
			{ 
				int length = dataIn.readInt();
				byte[] wrappedKey = new byte[length];
				dataIn.read(wrappedKey, 0, length);
				
				// unwrap with RSA private key
				Key privateKey = (Key)keyIn.readObject();
				
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.UNWRAP_MODE, privateKey);
				Key key = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);
				
				cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.DECRYPT_MODE, key);	
				
				Util.crypt(dataIn, out, cipher);
			} 
		}
	}
}
