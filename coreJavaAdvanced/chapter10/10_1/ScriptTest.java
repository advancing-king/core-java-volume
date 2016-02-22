package com.corejava.chapter10;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptTest
{
	public static void main(String[] args) throws ScriptException, FileNotFoundException
	{
		// �ű����������
		//ScriptEngineManager manager = new ScriptEngineManager();
		/*List<ScriptEngineFactory> list = manager.getEngineFactories();
		for(ScriptEngineFactory engine : list)
		{
			System.out.println(engine.getEngineName());
		}*/
		
		// �ű����������
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		
		// ����������ӱ�����
		engine.eval("n=1728");
		Object result = engine.eval("n+1"); // 1729.0
		System.out.println(result);
		
		// ��ȡ�ɽű����󶨵ı��� 
		engine.put("k", 100);
		result = engine.eval("k+1"); // 101
		System.out.println(result);
		
		// �����������ȫ����������Ӱ��⣬�����Խ����ռ���һ������Ϊ Bindings �Ķ����У� 
		// Ȼ���䴫�ݸ� eval ������
		Bindings scope = engine.createBindings();
		scope.put("a", "hello, ");
		result = engine.eval("a+ 'world!'", scope); // hello, world!
		System.out.println(result);
		
		// �ض�����������
		// �κ��� js �� print �� println����������������ᷢ�͵� writer
		StringWriter writer = new StringWriter();
		engine.getContext().setWriter(new PrintWriter(
				new File("com/corejava/chapter10/output.txt")));
		engine.eval("print('hello world, this msg is from java app')");
	}
}














