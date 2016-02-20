package com.corejava.chapter11.server;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;

import javax.naming.*;

/**
 * This server program instantiates a remote warehouse object, registers it with the naming
 * service, and waits for clients to invoke methods.
 * @version 1.12 2007-10-09
 * @author Cay Horstmann
 */
public class WarehouseServer
{
	public static void main(String[] args)
	{
		try
		{
			WarehouseImpl warehouseService = new WarehouseImpl();
			// ע��ͨѶ�˿�
			LocateRegistry.createRegistry(1099);
			// ע��ͨѶ·��
			Naming.rebind("rmi://localhost:1099/warehouseService", warehouseService);
			System.out.println("warehouse service starts");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// another version about rmi registry
	/*public static void main(String[] args) throws RemoteException, NamingException
   {
      System.out.println("Constructing server implementation...");
      WarehouseImpl centralWarehouse = new WarehouseImpl();

      System.out.println("Binding server implementation to registry...");
      
      Context namingContext = new InitialContext();
      // rmi://localhost:99/central_warehouse, 
      // Ĭ������£���������localhost���˿�Ϊ1099��
      // ��һ��WarehouseImpl����ע�ᵽ��ͬһ���������ϵ�RMIע�����
      namingContext.bind("rmi://localhost:1099/central_warehouse", centralWarehouse);

      System.out.println("Waiting for invocations from clients...");
   }*/
}
