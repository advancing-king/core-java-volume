package com.corejava.chapter11.activation.server;

import java.io.*;
import java.rmi.*;
import java.rmi.activation.*;
import java.util.*;

import javax.naming.*;

public class WarehouseActivator
{
	public static void main(String[] args) throws RemoteException,
			NamingException, ActivationException, IOException,
			AlreadyBoundException
	{
		System.out.println("Constructing activation descriptors...");
		System.setProperty("java.security.policy",
				"com/corejava/chapter11/activation/server/server.policy");
		System.setSecurityManager(new SecurityManager());

		// ��ι����������
		Properties props = new Properties();
		// use the server.policy file in the current directory
		props.put("java.security.policy", new File(
				"com/corejava/chapter11/activation/server/server.policy")
				.getCanonicalPath());
		// step1)��Ҫ����һ��������
		// step2�� Ȼ�����¹���һ����������������
		ActivationGroupDesc group = new ActivationGroupDesc(props, null);
		// step3)����һ����ID
		ActivationGroupID id = ActivationGroup.getSystem().registerGroup(group);

		Map<String, Double> prices = new HashMap<>();
		prices.put("Blackwell Toaster", 24.95);
		prices.put("ZapXpress Microwave Oven", 49.95);

		MarshalledObject<Map<String, Double>> param = new MarshalledObject<Map<String, Double>>(
				prices);

		String codebase = "http://localhost:8080/";

		// step4�� ����һ�������������ˡ�������Ҫ�����ÿһ�����󣬶�Ӧ�ð����������ݣ�contents����
		// ������ID + ������� + URL�ַ��� + �����Ĺ�����Ϣ
		// LocateRegistry.createRegistry(1099);
		ActivationDesc desc = new ActivationDesc(id,
				"com.corejava.chapter11.activation.server.WarehouseImpl",
				codebase, param);
		Warehouse centralWarehouse = (Warehouse) Activatable.register(desc);

		Naming.rebind("rmi://localhost:1099/central_warehouse", centralWarehouse);
		System.out.println("Exiting...");
	}
}
