package com.corejava.chapter11.activation.client;

import java.rmi.Naming;
import java.util.*;
import javax.naming.*;
import com.corejava.chapter11.activation.server.Warehouse;

// �ͻ�����λ��Զ�ֿ̲����Ĵ����������Զ�̵�getPrice������
public class WarehouseClient
{
	public static void main(String[] args)
	{
		try
		{
			System.setProperty("java.security.policy", "com/corejava/chapter11/activation/client/client.policy");
			System.setSecurityManager(new SecurityManager());
			
			Context namingContext = new InitialContext();
			// NameClassPair��һ�������ࣺ���������󶨶�������ֺ͸ö�������������֡�
			Enumeration<NameClassPair> e = namingContext.list("rmi://localhost:1099/");
			while (e.hasMoreElements())
				System.out.println(e.nextElement().getName());

			// �ͻ��˿���ͨ������ķ�ʽ����ָ����������Զ�̶�������֣��Դ˻�÷���Զ�̶�������Ĵ����
			String url = "rmi://localhost:1099/central_warehouse";

			Warehouse centralWarehouse = (Warehouse) Naming.lookup(url);
			String descr = "Blackwell Toaster";
			double price = centralWarehouse.getPrice(descr);
			System.out.println(descr + ": " + price);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
