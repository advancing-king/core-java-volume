package com.corejava.chapter1;

import static java.lang.System.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class compressFileUtil
{
	public static void main(String[] args) throws Exception
	{
		compressFiles("d:" + File.separator + "srcZip" + File.separator, 
				"d:" + File.separator + "desZip");
		out.println("successful compression !");
	}
	public static void compressFiles(String srcPath, String desPath) throws Exception
	{
		File srcfile = new File(srcPath);
		 
		FileOutputStream fos = new FileOutputStream(desPath + ".zip");
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
		
		createCompressedFile(zos, srcfile, "");
		zos.close();
	}
	
	public static void createCompressedFile(ZipOutputStream zos, File file, String dir) throws Exception
	{
		if(file.isDirectory()) //�����ǰ�ļ��������ļ��У�����н�һ������  
		{
			File[] files = file.listFiles(); //�õ��ļ��б���Ϣ  
			zos.putNextEntry(new ZipEntry(dir + File.separator)); //���ļ�����ӵ���һ�����Ŀ¼  
			dir = dir.length() == 0 ? "" : dir + File.separator; 
			for (int i = 0; i < files.length; i++) //ѭ�����ļ����е��ļ����  
			{
				createCompressedFile(zos, files[i], dir + files[i].getName());
			}
		}
		else //��ǰ�ļ��������ļ����������  
		{
			FileInputStream fis = new FileInputStream(file); //�ļ�������  
			zos.putNextEntry(new ZipEntry(dir));
			int j = 0;
			byte[] buffer = new byte[1024];
			while((j = fis.read(buffer)) > 0)
			{
				zos.write(buffer, 0, j); // ����д���� 
			}
			fis.close(); //�ر�������  
		}
	}
}
