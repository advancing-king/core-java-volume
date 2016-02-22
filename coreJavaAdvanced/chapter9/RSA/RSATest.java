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
		if(args[0].equals("-genkey")) // ������Կ�ԣ���Կ+˽Կ��
		{
			// ��Կ��������
			KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
			SecureRandom sr = new SecureRandom();
			pairgen.initialize(KEYSIZE, sr); // ��Կ����������ʼ��
			KeyPair pair = pairgen.generateKeyPair(); // ������Կ�ԣ���Կ+˽Կ��
			
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
			// args[3]==public.key,args[2]==encryptedFile,args[1]=inputFile
			try(ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
				DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(args[2]));
					InputStream in = new FileInputStream(args[1]))
			{
				Key publicKey = (Key)keyIn.readObject();// ���빫Կ
				Cipher cipher = Cipher.getInstance("RSA");// RSA�������
				cipher.init(Cipher.WRAP_MODE, publicKey); // ͨ�����ô��ģʽ�͹�Կ ����RSA���������г�ʼ��
				
				byte[] wrappedKey = cipher.wrap(key);// ͨ�����й�Կ��RSA�㷨�������Կ����
				dataOut.writeInt(wrappedKey.length); // �����ܺ����Կд�뵽�����  dataOut
				dataOut.write(wrappedKey);
				
				cipher = Cipher.getInstance("AES"); // AES �������
				cipher.init(Cipher.ENCRYPT_MODE, key); // ͨ�����ü���ģʽ����Կ ���� AES ���������г�ʼ��
				Util.crypt(in, dataOut, cipher); // ����AES��������inFile ���м��ܲ�д�뵽����� dataOut
			} 
		}
		else // ����ģ��
		{
			//args[1]==encryptedFile,args[3]=private.key,args[2]=decryptedFile;
			try(DataInputStream dataIn = new DataInputStream(new FileInputStream(args[1]));
					ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
						OutputStream out = new FileOutputStream(args[2]))
			{ 
				int length = dataIn.readInt();
				byte[] wrappedKey = new byte[length];
				dataIn.read(wrappedKey, 0, length); // ������ܺ���ļ���������Կ���ܺ����Կ �� ������Կ���ܺ���ļ����ݣ�
				
				// unwrap with RSA private key
				Key privateKey = (Key)keyIn.readObject(); // ����private.key �� wrappedKey
				
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.UNWRAP_MODE, privateKey); // ͨ�����ý��ģʽ��˽Կ ����RSA���������г�ʼ��
				// ͨ������˽Կ��RSA�㷨�������Կ����
				Key key = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);
				
				cipher = Cipher.getInstance("AES"); // AES �������
				cipher.init(Cipher.DECRYPT_MODE, key);	 // ͨ�����ý���ģʽ����Կ ���� AES ���������г�ʼ��				
				Util.crypt(dataIn, out, cipher); // ͨ��ʹ�ý��ܺ����Կ �� ���ܺ���ļ����� ���н��ܲ�д�뵽����� out
			} 
		}
	}
}
