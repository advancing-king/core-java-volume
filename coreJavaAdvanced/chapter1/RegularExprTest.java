package com.corejava.chapter1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.System.*;

public class RegularExprTest
{
	public static void main(String[] args)
	{
		Pattern p;
		Matcher m;
		String input;
		
		input = "11:59am";
		p = Pattern.compile("((1?[0-9]):([0-5][0-9]))"); // ̰��ƥ��
		m = p.matcher(input); 
		if(m.find())
		{
			int start = m.start();
			int end = m.end();
			out.println("start = " + start + " end = " + end);
			String match = input.substring(start, end);
			out.println(match);
		}
		out.println(m.replaceAll("#"));
		// �滻�ַ������԰�����ģʽ��Ⱥ������ã� $n ��ʾ�滻�ɵ�n ������
		out.println(m.replaceAll("$1"));
		out.println(m.replaceAll("$2"));
		out.println(m.replaceAll("$3"));
	}
	public static void main8(String[] args)
	{
		Pattern p;
		Matcher m;
		String input;
		
		// ���ɻ�����ʹ��Ⱥ���������ӱ��ʽ��Ⱥ�������ţ��� ��������	
		// ���ɻ�����������ʽ�е��ַ���[] ��
		input = "11:59am";
		p = Pattern.compile("((1?[0-9]):([0-5][0-9]))"); 
		m = p.matcher(input); 
		if(m.find())
		{
			int start = m.start();
			int end = m.end();
			out.println("start = " + start + " end = " + end);
			String match = input.substring(start, end);
			out.println(match);
		}
	}
	
	public static void main7(String[] args)
	{
		Pattern p;
		Matcher m;
		// ���ɻ�����ʹ��Ⱥ���������ӱ��ʽ��Ⱥ�������ţ��� ��������	
		// ���ɻ�����������ʽ�е��ַ���[] ��
		p = Pattern.compile("((1?[0-9]):([0-5][0-9]))[ap]m"); 
		m = p.matcher("11:59am"); 
		if(m.find())
		{
			out.println(m.groupCount());
			for (int i = 0; i <= m.groupCount(); i++)
			{
				out.print("m.start(" + i + ") = " + m.start(i) + "  ");
				out.print("m.group(" + i + ") = " + m.group(i) + "  ");
				out.print("m.end(" + i + ") = " + m.end(i) + "\n");
			}
		}
	}
	public static void main6(String[] args)
	{
		Pattern p;
		Matcher m;
		// ���ɻ�����ʹ��Ⱥ���������ӱ��ʽ��Ⱥ�������ţ��� ��������	
		p = Pattern.compile("([a-z]+)(\\d+)"); 
		m = p.matcher("aaa2223bb"); 
		out.println("m.find() = " + m.find());   //ƥ��aaa2223 
		out.println("m.groupCount() = " + m.groupCount());   //����2,��Ϊ��2�� 
		out.println("m.start(1) = " + m.start(1));   //����0 ���ص�һ��ƥ�䵽�����ַ������ַ����е������� 
		out.println("m.start(2) = " + m.start(2));   //����3 
		out.println("m.end(1) = " + m.end(1));   //����3 ���ص�һ��ƥ�䵽�����ַ��������һ���ַ����ַ����е�����λ��. 
		out.println("m.end(2) = " + m.end(2));   //����7 
		out.println("m.group(1) = " + m.group(1));   //����aaa,���ص�һ��ƥ�䵽�����ַ��� 
		out.println("m.group(2) = " + m.group(2));   //����2223,���صڶ���ƥ�䵽�����ַ��� 
	}
	public static void main5(String[] args)
	{
		Pattern p;
		Matcher m;
		
		out.print("\n=== Matcher.find() ===\n");
		p = Pattern.compile("\\d+"); 
		m = p.matcher("aaa2223bb"); 
		//out.println(m.find());//ƥ��2223 
		if(m.find())
		{
			out.println(m.start());//����3 
			out.println(m.end());//����7,���ص���2223��������� 
			out.println(m.group());//����2223 
		}
		
		out.print("\n=== Matcher.lookingAt() ===\n");
		Matcher m2 = p.matcher("2223bb"); 
		out.println(m2.lookingAt());   //ƥ��2223
		if(m2.lookingAt())
		{
			//����0,����lookingAt()ֻ��ƥ��ǰ����ַ���,
			// ���Ե�ʹ��lookingAt()ƥ��ʱ,start()�������Ƿ���0
			out.println(m2.start()); //����0
			out.println(m2.end());   //����4 
			out.println(m2.group());   //����2223 
		}

		out.print("\n=== Matcher.matches() ===\n");
		Matcher m3 = p.matcher("2223bb"); 
		out.println(m3.matches());   //ƥ�������ַ���
		if(m3.matches())
		{
			out.println(m3.start());  			//
			out.println(m3.end());    
			out.println(m3.group());  
		}
	}
	
	public static void main4(String[] args)
	{
		Pattern p;
		Matcher m;
		
		p = Pattern.compile("[a-z]*ab");// +ƥ��0������
		m = p.matcher("cab");
		out.println(m.matches()); // true
		
		p = Pattern.compile("[a-z]*+ab");// +ƥ��1������
		m = p.matcher("cab");
		// ̰���汾 [a-z]*+ ��ƥ���ַ� cab�� ģʽ ��ʣ�ಿ�ֽ��޷�ƥ�䣻
		out.println(m.matches()); // false
	}
	
	public static void main3(String[] args)
	{
		Pattern p;
		Matcher m;
		
		p = Pattern.compile("[Jj]ava.+");// +ƥ��1������
		m = p.matcher("java");
		out.println(m.matches()); // false
		
		p = Pattern.compile("[Jj]ava.*");// *ƥ��0������
		m = p.matcher("java");
		out.println(m.matches()); // true
		
		p = Pattern.compile("[Jj]ava.?");// ?ƥ��0����1��
		m = p.matcher("java");
		out.println(m.matches());// true
		
		p = Pattern.compile("[Jj]ava.+");
		m = p.matcher("javaa");
		out.println(m.matches());// true
	}
	
	public static void main2(String[] args)
	{
		out.print("\n=== Pattern.pattern() ===\n");
		Pattern p = Pattern.compile("\\w+");
		out.println(p.pattern());//���� \w+
		
		out.print("\n=== Pattern.split() ===\n");
		p = Pattern.compile("\\d+"); // d = digit
		String[] str=p.split("�ҵ�QQ��:456456�ҵĵ绰��:0532214�ҵ�������:aaa@aaa.com"); 
		for (String single : str)
		{
			out.println(single);
		}
		
		out.print("\n=== Pattern.matches() ===\n");
		out.println(Pattern.matches("\\d+","2223"));//����true 
		//����false,��Ҫƥ�䵽�����ַ������ܷ���true,����aa����ƥ�䵽
		out.println(Pattern.matches("\\d+","2223aa"));
		//����false,��Ҫƥ�䵽�����ַ������ܷ���true,����bb����ƥ�䵽
		out.println(Pattern.matches("\\d+","22bb23"));
		
		out.print("\n=== Matcher.pattern()===\n");
		p = Pattern.compile("\\d+"); 
		Matcher m = p.matcher("22bb23"); 
		out.println(m.pattern());//����p Ҳ���Ƿ��ظ�Matcher���������ĸ�Pattern����Ĵ�����
		
		out.print("\n=== Matcher.matches()===\n");
		p = Pattern.compile("\\d+"); 
		m = p.matcher("22bb23"); 
		out.println(m.matches());//����false,��Ϊbb���ܱ�\d+ƥ��,���������ַ���ƥ��δ�ɹ�. 
		Matcher m2 = p.matcher("2223"); 
		out.println(m2.matches());//����true,��Ϊ\d+ƥ�䵽�������ַ���
		
		// lookingAt()��ǰ����ַ�������ƥ��,ֻ��ƥ�䵽���ַ�������ǰ��ŷ���true 
		out.print("\n=== Matcher.lookingAt()===\n");
		p = Pattern.compile("\\d+"); 
		m = p.matcher("22bb23"); 
		out.println(m.lookingAt());//����true,��Ϊ\d+ƥ�䵽��ǰ���22 
		m2 = p.matcher("aa2223"); 
		out.println(m2.lookingAt());//����false,��Ϊ\d+����ƥ��ǰ���aa
		
		// find()���ַ�������ƥ��,ƥ�䵽���ַ����������κ�λ��.
		out.print("\n=== Matcher.find()===\n");
		p = Pattern.compile("\\d+"); 
		m = p.matcher("22bb23"); 
		out.println(m.find());//����true 
		m2 = p.matcher("aa2223"); 
		out.println(m2.find());//����true 
		Matcher m3=p.matcher("aa2223bb"); 
		out.println(m3.find());//����true 
		Matcher m4=p.matcher("aabb"); 
		out.println(m4.find());//����false
	}
	public static void main1(String[] args)
	{
		String patternStr = "[Ji]ava";
		String input = "java";
		
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(input);
		if(matcher.matches())
		{
			out.println("bingo!");
		}else
		{
			out.println("o m g !");
		}
	}
}
