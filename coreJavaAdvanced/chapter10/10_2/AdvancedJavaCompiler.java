package com.corejava.chapter10_2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class AdvancedJavaCompiler
{
	public static void main(String[] args) throws IOException
	{
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); // ����java ������
		// DiagnosticCollector �Ǽ�������һ��ʵ��
		DiagnosticCollector<JavaFileObject>  diagnostics = new DiagnosticCollector<>(); 
		// java �ļ�������
		StandardJavaFileManager manager = compiler.getStandardFileManager(diagnostics, Locale.CHINA, Charset.forName("UTF-8")); 
		/* Iterable<? extends JavaFileObject> getJavaFileObjectsFromFiles(Iterable<? extends File> files) 
		   Iterable<? extends JavaFileObject> getJavaFileObjectsFromStrings(Iterable<String> names) */
		
		// ��Ҫ�����Դ�ļ�
		Iterable<? extends JavaFileObject> compilationUnits = manager.getJavaFileObjects("com/corejava/chapter10_2/StandardJavaFileManagerTest.java");
		CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, compilationUnits);
		// ���û�б��뾯��ʹ���,���call() ������������е� compilationUnits ����ָ�����ļ�,�Լ���������ϵ�Ŀɱ�����ļ�. 
		Boolean suc = task.call();
		
		/* ֻ�е����еı��뵥Ԫ��ִ�гɹ���,��� call() �����ŷ��� Boolean.TRUE  . һ�����κδ���,��������ͻ᷵�� Boolean.FALSE .
		 * ��չʾ�����������֮ǰ,������������һ������,DiagnosticListener , ���߸�ȷ�е�˵,  DiagnosticCollector .��ʵ����.
		 * ���������������getTask()�ĵ������������ݽ�ȥ,��Ϳ����ڱ���֮�����һЩ��ʽ��Ϣ�Ĳ�ѯ��. */
		for(Diagnostic diagnostic : diagnostics.getDiagnostics())
		{
			System.console().printf(
				"Code: %s%n" + 
		        "Kind: %s%n" + 
		        "Position: %s%n" + 
		        "Start Position: %s%n" + 
		        "End Position: %s%n" + 
		        "Source: %s%n" + 
		        "Message:  %s%n", 
		        diagnostic.getCode(), diagnostic.getKind(), 
		        diagnostic.getPosition(), diagnostic.getStartPosition(), 
		        diagnostic.getEndPosition(), diagnostic.getSource(), 
		        diagnostic.getMessage(null));
		}
		manager.close();
		System.out.println("success : " + suc);
	}
}
