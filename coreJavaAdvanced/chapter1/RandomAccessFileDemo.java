package com.corejava.chapter1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import static java.lang.System.*;

public class RandomAccessFileDemo
{
	public static String path = System.getProperty("user.dir") + File.separator + "src" + File.separator +  
			"com" + File.separator + "corejava" + File.separator +  "chapter1" + File.separator;
	
	public static void main2(String[] args) throws IOException
	{
		insertCase(5, "xiaoTangTang", path+"binary1.dat");
	}
	/**
	 * RandomAccessFile ����дʾ����
	 * @param skip �������ֽ���
	 * @param str Ҫ������ַ���
	 * @param fileName �ļ�·��
	 * @throws IOException 
	 */
	public static void insertCase(long skip, String str, String fileName) throws IOException
	{
		RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
		byte[] array = str.getBytes();
		raf.setLength(raf.length() + array.length);
		for (long i = raf.length()-1; i > array.length + skip - 1; i--)
		{
			raf.seek(i-array.length);
			byte temp = raf.readByte();
			raf.seek(i);
			raf.writeByte(temp);
		}
		raf.seek(skip);
		raf.write(array);
		raf.close();
		out.println("successful insertion !");
	}
	public static void main1(String[] args) throws IOException
	{
		RandomAccessFile file = new RandomAccessFile(path + "binary1.dat", "rw");
		file.writeInt(20); // ռ4���ֽ�  
		file.writeDouble(2.3); // ռ8���ֽ�
		// �������д�ڵ�ǰ�ļ�ָ���ǰ�����ֽڴ�������readShort()��ȡ
		file.writeUTF("this is a utf str");   
		file.writeBoolean(true); // ռ1���ֽ�
		file.writeShort(399); // ռ2���ֽ�
		file.writeLong(321321); // ռ8���ֽ�
		file.writeUTF("this is a also utf str"); 
		file.writeFloat(2.3f); // ռ4���ֽ�
		file.writeChar('a'); // ռ2���ֽ�
		file.seek(0); // ���ļ�ָ��λ�����õ��ļ���ʼ��  
		
		// ���´�file�ļ��ж����ݣ�Ҫע���ļ�ָ���λ��  
		out.println("��file�ļ�ָ��λ�ö�����");
		out.print(file.readInt() + " ");
		out.print(file.readDouble()+ " ");
		out.print(file.readUTF() + " ");
		// ���ļ�ָ������3���ֽڣ������м�������һ��booleanֵ��shortֵ��
		file.skipBytes(3);
		out.print(file.readLong() + " ");
		// �����ļ��С�also utf str����ռ�ֽڣ�ע��readShort()�������ƶ��ļ�ָ�룬���Բ��ü�2��		
		file.skipBytes(file.readShort());
		out.print(file.readFloat() + " ");
		//������ʾ�ļ����Ʋ���  
		out.println("\n�������������ļ����ƣ���file��fileCopy��������������");
		file.seek(0);
		RandomAccessFile filecopy = new RandomAccessFile("filecopy.dat", "rw");
		int len = (int) file.length(); //ȡ���ļ����ȣ��ֽ�����  
		byte[] array = new byte[len];
		file.readFully(array);
		filecopy.write(array);
		out.println("copy over!");
	}
	
//	�������ö��߳̽����ļ���д����
	public static void main(String[] args) throws IOException
	{
		RandomAccessFile file = new RandomAccessFile(path + "binary2.dat", "rw");
		 // Ԥ�����ļ���ռ�Ĵ��̿ռ䣬�����лᴴ��һ��ָ����С���ļ�  
		file.setLength(1024*1024); // Ԥ���� 1M ���ļ��ռ�  
		file.close();
		
		 // ��Ҫд����ļ�����  
		String s1 = "first str";
		String s2 = "second str";
		String s3 = "third str";
		
		// ���ö��߳�ͬʱд��һ���ļ�  
		 // ���ļ���1024�ֽ�֮��ʼд������  
		new Thread(new FileWriterThread(1024*1, s1.getBytes()), "thread1").start();
		 // ���ļ���1024*2�ֽ�֮��ʼд������  
		new Thread(new FileWriterThread(1024*2, s1.getBytes()), "thread2").start();
		 // ���ļ���1024*3�ֽ�֮��ʼд������  
		new Thread(new FileWriterThread(1024*3, s1.getBytes()), "thread3").start();
		
		file = new RandomAccessFile(path + "binary2.dat", "rw");
		for (int i = 0; i < 3; i++)
		{
			out.println(file.readUTF() + " ");
		}
	}
}

//�����߳����ļ���ָ��λ��д��ָ������  
class FileWriterThread implements Runnable
{
	private int skip;
	private byte[] content;
	
	public FileWriterThread(int skip, byte[] content)
	{
		this.skip = skip;
		this.content = content;
	}
	
	@Override
	public void run()
	{
		RandomAccessFile raf = null;
		try
		{
			raf = new RandomAccessFile(RandomAccessFileDemo.path + "binary2", "rw");
			raf.seek(skip);
			raf.write(content);
		} catch (IOException e)
		{
			e.printStackTrace();
		}finally 
		{
			try
			{
				raf.close();
				out.println(Thread.currentThread().getName() + " write over!");
			} catch (Exception e2)
			{
			}
		}
	}
}



