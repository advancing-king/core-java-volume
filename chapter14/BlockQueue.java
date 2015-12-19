package com.corejava.chapter14_6;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import static java.lang.System.*;

public class BlockQueue
{
    public static void main(String[] args) 
    {  
        BigPlate plate = new BigPlate();  
        
        for(int i = 0; i < 10; i++) // ������10���ż����߳�  
        {  
            new Thread(new BigPlate.AddThread(plate)).start();  
        }  
        
        for(int i = 0; i < 10; i++) // ������10��ȡ�����߳�  
        {  
            new Thread(new BigPlate.GetThread(plate)).start();  
        }  
    } 
}

/** ����һ�������࣬���Էż�����ȡ���� */  
class BigPlate 
{  
    /** װ���������ӣ���СΪ5 */  
    private BlockingQueue<Object> eggs = new ArrayBlockingQueue<>(5);  
      
    /** �ż��� */  
    public void putEgg(Object egg) 
    {  
        try 
        {  
            eggs.put(egg);// ������ĩβ��һ������������������ˣ���ǰ�߳�����  
            
        } catch (InterruptedException e) 
        {  
            e.printStackTrace();  
        }  
        // ���������ʱ��׼ȷ����Ϊ��put��������һ��ԭ�Ӳ���  
        System.out.println("���뼦��");  
    }  
      
    /** ȡ���� */  
    public Object getEgg() 
    {  
        Object egg = null;  
        try 
        {  
            egg = eggs.take();// �����ӿ�ʼȡһ��������������ӿ��ˣ���ǰ�߳�����  
        } catch (InterruptedException e) 
        {  
            e.printStackTrace();  
        }  
        // ���������ʱ��׼ȷ����Ϊ��take��������һ��ԭ�Ӳ���  
        System.out.println("�õ�����");  
        return egg;  
    }  
      
    /** �ż����߳� */  
    static class AddThread extends Thread 
    {  
        private BigPlate plate;  
        private Object egg = new Object();  
  
        public AddThread(BigPlate plate) 
        {  
            this.plate = plate;  
        }  
  
        public void run() 
        {  
            plate.putEgg(egg);  
        }  
    }  
  
    /** ȡ�����߳� */  
    static class GetThread extends Thread 
    {  
        private BigPlate plate;  
  
        public GetThread(BigPlate plate) 
        {  
            this.plate = plate;  
        }  
  
        public void run() 
        {  
            plate.getEgg();  
        }  
    } 
     
} 
