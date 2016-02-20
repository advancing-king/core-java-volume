package com.corejava.chapter11.server;

import java.rmi.*;

/**
   The remote interface for a simple warehouse.
   @version 1.0 2007-10-09
   @author Cay Horstmann
*/
// Զ�̶���Ľӿڱ�����չRemote�ӿ�
public interface Warehouse extends Remote
{  
   double getPrice(String description) throws RemoteException;
}
