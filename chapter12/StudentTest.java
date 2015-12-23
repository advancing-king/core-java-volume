package com.corejava.chapter12_4;

import static java.lang.System.*;
public class StudentTest
{
	public static void main(String[] args)
	{
		CollegeStudent[] stu = new CollegeStudent[]{
				   new CollegeStudent(3),new CollegeStudent(2),
				   new CollegeStudent(5),new CollegeStudent(4)};
		sortWithoutWildCard(stu);
		for(CollegeStudent cs : stu)
		{
			out.println(cs.toString());
		}
	}
	
	// take insertion sort by id from small to larger order
	// T=CollegeStudent��Ȼ��CollegeStudent��û�м̳�Comparable<CollegeStudent>,
	// �����Ǽ̳���Comparable<Student>,��Comparable<Student> �� Comparable<CollegeStudent> ��û���κι�ϵ
	// ��ʹ CollegeStudent extends Student
	// �������ﲢû�б������������������˵��ͨ�ģ���ʵ����ȴû�б���
	public static <T extends Comparable<T>> void sortWithoutWildCard(T[] a)
	{		// Compa
		T temp;
		int j;
		
		for (int i = 1; i < a.length; i++)
		{
			temp = a[i];
			for (j = i; j > 0 && a[j-1].compareTo(temp) > 0; j--)
			{
				a[j] = a[j-1];
			}
			a[j] = temp;
		}
	}
	
	// ����T=CollegeStudent����������˵��ͨ�ģ�ʵ�ʱ���Ҳ����ͨ����
	// ��Ϊ����ķ��ͷ����������޶�Ϊ <T extends Comparable<? super T>>
	// �� Comparable<Student> ��  Comparable<? super T> �����࣬��CollegeStudent extends Comparable<Student>
	public static <T extends Comparable<? super T>> void sortWithWildCard(T[] a)
	{
		T temp;
		int j;
		
		for (int i = 1; i < a.length; i++)
		{
			temp = a[i];
			for (j = i; j > 0 && a[j-1].compareTo(temp) > 0; j--)
			{
				a[j] = a[j-1];
			}
			a[j] = temp;
		}
	}
}

class Student implements Comparable<Student>
{
	private int id;
	public Student(int id)
	{
		this.id = id;
	}
	@Override
	public int compareTo(Student o)
	{
		return this.id - o.id;
	}
	@Override
	public String toString()
	{
		return id + " ";
	}
}

class CollegeStudent extends Student //implements Comparable<CollegeStudent>
{
	public CollegeStudent(int id)
	{
		super(id);
	}
}