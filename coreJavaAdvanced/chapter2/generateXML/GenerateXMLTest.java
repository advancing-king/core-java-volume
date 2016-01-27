package com.corejava.chapter2;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class GenerateXMLTest
{
	private static String workDir = System.getProperty("user.dir") + File.separator + "src" + File.separator +  
			"com" + File.separator + "corejava" + File.separator +  "chapter2" + File.separator;
	
	// ���� XML �ĵ�
	public static void main(String[] args) throws Exception
	{
		// 3rd method: adapt StAX parser
		// ��ʼ����ת��
		XMLOutputFactory factory =  XMLOutputFactory.newInstance();
		FileOutputStream out = new FileOutputStream(new File(workDir+"generateStAX.xml"));
		XMLStreamWriter writer = factory.createXMLStreamWriter(out);
		
		//Ҫ����XML �ļ�ͷ�� ��Ҫ����
		writer.writeStartDocument();	
		
		writer.writeStartDocument("font");		
		// ������Ե���
		writer.writeAttribute("name" , "ab");
		writer.writeAttribute("size" , "36 pt");
		
		// �ٴε��� writeStartElement ����µ��ӽڵ�
		writer.writeStartDocument("name");
		// ������ �����д���ַ�
		writer.writeCharacters("abcdefg");
		writer.writeEndElement();
		
		writer.writeStartDocument("size");
		writer.writeAttribute("unit", "pt");
		writer.writeCharacters("36");
		writer.writeEndElement();
		
		// ����������ӽڵ�� ���� writeEndElement
		writer.writeEndElement();		
		writer.writeEndDocument();
		System.out.println("generating over!");
	}
	
	// ���� XML �ĵ�
	public static void main2(String[] args) throws Exception
	{
		//step1: Ҫ����һ��DOM�������Դ�һ�����ĵ���ʼ
		Document doc = DocumentBuilderFactory.newInstance().
												newDocumentBuilder().newDocument();
		
		//step2: ʹ��Document�� �� createElement �������Թ����ĵ����Ԫ��
		Element root = doc.createElement("font");
		// ��Ҫ����Ԫ�����ԣ� ֻ��Ҫ���� Element��� setAttribute����
		root.setAttribute("name", "ab");
		root.setAttribute("size", "36 pt");
		
		Element name = doc.createElement("name");	
		// step3: ʹ�� createTextNode �������Թ����ı��ڵ㣺
		name.appendChild(doc.createTextNode("abcdefg"));
		
		Element size = doc.createElement("size");
		size.setAttribute("unit", "pt");
		size.appendChild(doc.createTextNode("36"));
		
		// step4: ���ĵ���Ӹ�Ԫ�أ������ڵ�����ӽڵ�
		root.appendChild(name);
		root.appendChild(size);
		doc.appendChild(root);
		
		// 2nd method: adapt LSSerializer interface
		// step5: ��ʼ����ת��
		DOMImplementation impl = doc.getImplementation();
		DOMImplementationLS imlpLS = (DOMImplementationLS)impl.getFeature("LS", "3.0");
		LSSerializer ser = imlpLS.createLSSerializer();
		
		// step5.2  �����Ҫ�ո�ͻ��У�������������ı�־
		ser.getDomConfig().setParameter("format-pretty-print", true);
		// �� �ĵ�ת��Ϊ �ַ���
		//String str = ser.writeToString(doc);
		
		// step5.3  �����Ҫ�����ֱ��д�뵽�ļ��У� ����Ҫһ�� LSOutput��
		LSOutput out = imlpLS.createLSOutput();
		out.setEncoding("UTF-8");
		out.setByteStream(new FileOutputStream(new File(workDir + "generate2.xml")));		
		ser.write(doc, out);
		
		System.out.println("generate xml over");
	}	
	
	// ���� XML �ĵ�
	public static void main1(String[] args) throws Exception
	{
		//step1: Ҫ����һ��DOM�������Դ�һ�����ĵ���ʼ
		Document doc = DocumentBuilderFactory.newInstance().
												newDocumentBuilder().newDocument();
		
		//step2: ʹ��Document�� �� createElement �������Թ����ĵ����Ԫ��
		Element root = doc.createElement("font");
		// ��Ҫ����Ԫ�����ԣ� ֻ��Ҫ���� Element��� setAttribute����
		root.setAttribute("name", "ab");
		root.setAttribute("size", "36 pt");
		
		Element name = doc.createElement("name");	
		// step3: ʹ�� createTextNode �������Թ����ı��ڵ㣺
		name.appendChild(doc.createTextNode("abcdefg"));
		
		Element size = doc.createElement("size");
		size.setAttribute("unit", "pt");
		size.appendChild(doc.createTextNode("36"));
		
		// step4: ���ĵ���Ӹ�Ԫ�أ������ڵ�����ӽڵ�
		root.appendChild(name);
		root.appendChild(size);
		doc.appendChild(root);
		
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, workDir+"myfont.dtd");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(
				new File(workDir+"generate.xml"))));
		System.out.println("generate xml over");
	}	
}



















