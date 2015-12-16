package com.corejava.chapter13;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import static java.lang.System.*;

public class DequeTest
{
	public static void main(String[] args)
	{
		Deque<String> deque = 
				new ArrayDeque<>(Arrays.asList(new String[]{"A"}));
		for (String str : deque)
			out.print(str + " ");
		out.println();
		
		deque.offerLast("world");
		deque.addFirst("hello");
		for (String str : deque)
			out.print(str + " ");
		out.println();
		
		deque.pollLast();//ɾ��β��Ԫ�أ���Ϊ�գ����׳��쳣��������null
		for (String str : deque)
			out.print(str + " ");
		out.println();
		
		out.println("peekFirst() = " + deque.peekFirst());//���ص�һ��Ԫ�أ���ɾ��Ԫ��
		for (String str : deque)
			out.print(str + " ");
		out.println();
	}
}
