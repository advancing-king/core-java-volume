package com.corejava.chapter11.client;

import java.util.*;
import javax.naming.*;
import com.corejava.chapter11.server.Warehouse;

/**
 * A client that invokes a remote method.
 * 
 * @version 1.0 2007-10-09
 * @author Cay Horstmann
 */
// �ͻ�����λ��Զ�ֿ̲����Ĵ����������Զ�̵�getPrice������
public class WarehouseClient
{
	public static void main(String[] args)
	{
		try
		{
			Context namingContext = new InitialContext();
			// NameClassPair��һ�������ࣺ���������󶨶�������ֺ͸ö�������������֡�
			Enumeration<NameClassPair> e = namingContext
					.list("rmi://localhost:1099/");
			while (e.hasMoreElements())
				System.out.println(e.nextElement().getName());

			// �ͻ��˿���ͨ������ķ�ʽ����ָ����������Զ�̶�������֣��Դ˻�÷���Զ�̶�������Ĵ����
			String url = "rmi://localhost:1099/warehouseService";
			Warehouse centralWarehouse = (Warehouse) namingContext.lookup(url);

			String descr = "A";
			double price = centralWarehouse.getPrice(descr);
			System.out.println(descr + ": " + price);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * public static void main(String[] args) throws NamingException,
	 * RemoteException { Context namingContext = new InitialContext();
	 * 
	 * System.out.print("RMI registry bindings: "); //
	 * NameClassPair��һ�������ࣺ���������󶨶�������ֺ͸ö�������������֡� Enumeration<NameClassPair> e =
	 * namingContext.list("rmi://localhost:1099/warehouseService"); while
	 * (e.hasMoreElements()) System.out.println(e.nextElement().getName());
	 * 
	 * // �ͻ��˿���ͨ������ķ�ʽ����ָ����������Զ�̶�������֣��Դ˻�÷���Զ�̶�������Ĵ���� String url =
	 * "rmi://localhost/central_warehouse"; Warehouse centralWarehouse =
	 * (Warehouse) namingContext.lookup(url);
	 * 
	 * String descr = "Blackwell Toaster"; double price =
	 * centralWarehouse.getPrice(descr); System.out.println(descr + ": " +
	 * price); }
	 */
}
