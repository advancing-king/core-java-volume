package com.corejava.chapter1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static java.lang.System.*;

public class BinaryIO
{
	private static String path = System.getProperty("user.dir") + File.separator + "src" + File.separator +  
			"com" + File.separator + "corejava" + File.separator +  "chapter1" + File.separator;
	
	//LargeMappedFiles ��֦ 
	public static void main(String[] args) throws IOException
	{
		int length = 0x8000000; // 128mb == buffer size
		// Ϊ���Կɶ���д�ķ�ʽ���ļ�������ʹ��RandomAccessFile�������ļ��� 
		FileChannel channel = new RandomAccessFile(path + "randomBinary.dat", "rw").getChannel();
		//ע�⣬�ļ�ͨ���Ŀɶ���дҪ�������ļ�������ɶ�д�Ļ���֮��  
		MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
		//д128M������  
		for (int i = 0; i < length/3; i++)
		{
			mappedByteBuffer.put((byte)'x');
			mappedByteBuffer.put((byte)'y');
			mappedByteBuffer.put((byte)'z');
		}
		out.println("writing over!");
		//��ȡ�ļ��м�6���ֽ�����
		for (int i = length/2; i < length/2+6; i++)
		{
			out.print((char)mappedByteBuffer.get(i) + " ");
		}
	}
	public static void main2(String[] args) throws IOException
	{
		RandomAccessFile raf = new RandomAccessFile(path + "randomBinary.dat", "rw");
		for (int i = 0; i < 5; i++)
		{
			raf.writeInt(i); //д���������int���� 
		}
		raf.close();
		raf = new RandomAccessFile(path + "randomBinary.dat", "rw");
		raf.seek(3*4);//ֱ�ӽ��ļ�ָ���Ƶ���3��int���ݺ��� 
		raf.writeInt(10000);//���ǵ�4��int ���� 
		raf.close();
		raf = new RandomAccessFile(path + "randomBinary.dat", "r");
		for (int i = 0; i < 5; i++)
		{
			out.print(raf.readInt() + " ");
		}
		raf.close();
	}
	public static void main1(String[] args) throws IOException
	{
		InputStreamReader reader = new InputStreamReader(System.in);
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(path + "binary.dat"));
		
		int t;
		out.println("input data: ");
		while((t=in.read()) != 48)
		{
			out.print(t + " ");
			dos.writeInt(t);
		}
		dos.flush();
		dos.close();
		
		DataInputStream dis = new DataInputStream(new FileInputStream(path + "binary.dat"));
		for (int i = 0; i < dis.available(); i++)
		{
			out.print(dis.readInt() + " ");
		}
	}
}
