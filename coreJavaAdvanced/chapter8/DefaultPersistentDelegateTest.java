package com.corejava.chapter8;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class DefaultPersistentDelegateTest
{
	public static void main(String[] args)
	{
		XMLEncoder out = null;
		// �־û� java�ൽ XML
		try
		{
			out = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("com/corejava/chapter8/default_persistent_delegate.xml")));
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		// ��װ�־û��������
		// �����߱������� ��Ҫ����һ�� Employee ���� 
		// ��Ҫȡ������ name, salary, age ���ԡ��� ���������������ù�������
		out.setPersistenceDelegate(Employee.class, 
				new DefaultPersistenceDelegate(new String[]{"name","salary","age"}));
		Employee employee = new Employee("default xiao tang", 110, 25);
		out.writeObject(employee);
		out.close();
		// �־û��������
		
		System.out.println("DefaultPersistentDelegateTest write Sucess");
	}
}
