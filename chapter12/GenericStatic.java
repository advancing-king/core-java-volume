package com.corejava.chapter12_3;

import static java.lang.System.*;

public class GenericStatic
{
	public static void main(String[] args)
	{
		
	}
	
	public static <T extends Throwable> void dowork(Class<T> t)
	{
	    try
		{
	    	out.print("hhe");
	    //catch �Ӿ��в���ʹ�����ͱ����� ���·�������ͨ�����룺
		}catch(T e) // ERROR--can't catch type variable
	    {
	    }
	}
}
//��������չ Throwable Ҳ�ǲ��Ϸ���
class Problem<T> extends Exception 
{ }

class Singleton<T>
{
	
	
    /*private static T single; // ERROR
    private static getSingle() // ERROR
    {
    	//.....
    }*/
}
