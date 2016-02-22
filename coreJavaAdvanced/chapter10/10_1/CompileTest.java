package com.corejava.chapter10;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CompileTest
{
	public static void main(String[] args) throws FileNotFoundException, ScriptException
	{
		// �ű����������
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		
		// ��ȡ��
		Reader reader = new FileReader("com/corejava/chapter10/test.js");
		CompiledScript script = null;
		if(engine instanceof Compilable)
		{
		    script = ((Compilable)engine).compile(reader); // ����ű�
		}
		if(script != null)
		{
		    script.eval();
		    System.out.println("ִ�б����Ľű�");
		}
		else
		{
		    engine.eval(reader);
		    System.out.println("ִ�нű�Դ��");
		}
		
		Object goodbyeGreeter = engine.eval("new SimpleGreeter('goodbye')");
		Greeter g = ((Invocable)engine).getInterface(goodbyeGreeter, Greeter.class);
		String result = g.greet("World");
		System.out.println(result);// goodbye, World
	}
}
