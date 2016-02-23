package com.corejava.chapter8;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PersistentTest
{
	public static void main(String[] args)
	{
		XMLEncoder e = null;
		// �־û� java�ൽ XML
		try
		{
			e = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("com/corejava/chapter8/persistent.xml")));
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		Hello hello = new Hello();
		hello.setMsg("this is a test");
		hello.setSpeaker("persistent tang");
		e.writeObject(hello);
		e.close();
		// �־û��������
		
		System.out.println("write Sucess");

		// ��ȡ�־û�����
		XMLDecoder d = null;
		try
		{
			d = new XMLDecoder(new BufferedInputStream(
					new FileInputStream("com/corejava/chapter8/persistent.xml")));
		} catch (Exception f)
		{
			f.printStackTrace();
		}
		Object result = d.readObject();
		Hello test = (Hello) result;
		System.out.println(test.getMsg() + test.getSpeaker());
		// ��ȡ�־û��������
	}
}













