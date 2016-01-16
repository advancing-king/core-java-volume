package com.corejava.chapter1;

import static java.lang.System.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.List;

public class FileAndPathTest
{
	private static String workDir = System.getProperty("user.dir") + File.separator + "src" + File.separator +  
			"com" + File.separator + "corejava" + File.separator +  "chapter1" + File.separator;
	public static void main(String[] args) throws IOException
	{		
		FileSystem fs = FileSystems.newFileSystem(Paths.get(workDir, "testZip.zip"), null);
		Files.walkFileTree(fs.getPath("/"), new SimpleFileVisitor<Path>()
	    {
	        public FileVisitResult visitFile(Path file , BasicFileAttributes attrs)
	        {
	            out.println(file);
	            return FileVisitResult.CONTINUE;
	        }
	    });
	}
	
	public static void main9(String[] args) throws IOException
	{
		Path path = Paths.get("E:", "tempdocument");
		Files.walkFileTree(path, new SimpleFileVisitor<Path>()
		{
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException
			{
				out.println(file);
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException
			{
				out.print("\npreVisitDirectory");
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException
			{
				out.print("\npostVisitDirectory");
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException
			{
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	public static void main8(String[] args) throws IOException
	{
		Path path = Paths.get("E:", "tempdocument");
		
		try(DirectoryStream<Path> entries = Files.newDirectoryStream(path))
		{
		    for(Path entry : entries)
		    {
		    	out.println(entry.getFileName());
		    }
		        
		}
		out.println("\n�� glob ģʽ������(*.txt)");
		try(DirectoryStream<Path> entries = Files.newDirectoryStream(path, "*.txt"))
		{
		    for(Path entry : entries)
		    {
		    	out.println(entry.getFileName());
		    }
		        
		}
	}
	public static void main7(String[] args) throws IOException
	{
		Path path = Paths.get(workDir, "test.txt");
		// ��̬����list�� 
		// exists + isHidden + isReadable + isWritable + isExecutable 
		// + isRegularFile + isDirectory + isSymbolicLink
		out.println("exists = " + Files.exists(path, LinkOption.NOFOLLOW_LINKS));
		out.println("isHidden = " + Files.isHidden(path));
		out.println("isReadable = " + Files.isReadable(path));
		out.println("isWritable = " + Files.isWritable(path));
		out.println("isExecutable = " + Files.isExecutable(path));
		out.println("isRegularFile = " + Files.isRegularFile(path));
		out.println("isDirectory = " + Files.isDirectory(path));
		out.println("isSymbolicLink = " + Files.isSymbolicLink(path));
		
		// ���е��ļ�ϵͳ���ᱨ��һ���������Լ��� ���Ǳ���װ�� BasicFileAttributes �ӿ���
		out.println("\nBasicFileAttributes as follows: ");
		BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
		out.println(attributes.isDirectory());
		out.println(attributes.isRegularFile());
		out.println(attributes.isSymbolicLink());		
		
		//������˽⵽�ļ�ϵͳ���� POSIX�� ���Ի�ȡһ�� PosiXFileAttributes ʵ����
		out.println("\nPosixFileAttributes as follows:");
		PosixFileAttributes attrs = Files.readAttributes(path, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
		out.println(attrs.isDirectory()); 
		out.println(attrs.creationTime());
		out.println(attrs.lastAccessTime());
		out.println(attrs.group());
		out.println(attrs.owner());
	}
	
	public static void main6(String[] args) throws IOException
	{
		Path path = Paths.get("E:", "tempdocument", "xiaotang");
		Files.deleteIfExists(path);
		//����Ŀ¼
		Files.createDirectory(path);
		out.println("create dir over !");
		
		path = Paths.get("E:", "tempdocument", "xiaoxiaotang", "xiaoxiaoxiaotang");
		Files.deleteIfExists(path);
		//���������м�Ŀ¼
		Files.createDirectories(path);
		out.println("iteratively create dirs over !");
		
		// �������ļ�
		path = Paths.get("E:", "tempdocument", "tangtang.txt");
		Files.deleteIfExists(path);
		Files.createFile(path);
		out.println(" create new file over !");

		// ��Щ��ݷ��������ڸ���λ�û�ϵͳָ��λ�ô�����ʱ�ļ��� ��ʱĿ¼��
		Path newPath = Files.createTempFile(path.getParent(), null, null);
		/*Path newPath = Files.createTempFile( prefix, suffix);
		Path newPath = Files.createTempFile(dir, prefix);
		Path newPath = Files.createTempFile(prefix);*/
		out.println(newPath.getFileName());
		
		newPath = Files.createTempFile(path.getParent(), "a", "b");
		out.println(newPath.getFileName());
		
		newPath = Files.createTempFile(path.getParent(), "a", "b.txt");
		out.println(newPath.getFileName());
	}
	
	public static void main5(String[] args) throws IOException
	{
		Path source = Paths.get(workDir, "test.txt");
		Path destination = Paths.get("E:", "tempdocument", "copy.txt");
		
		// ���Ҫɾ�����ļ������ڣ� delete �����ͻ��׳��쳣��ʹ�� deleteIfExists �����׳��쳣
		Files.deleteIfExists(destination);
		
		// �����ļ���Files.copy(fromPath, toPath);
		// 1�������Ҫ�������е�Ŀ��·���� ����ʹ�� REPLACE_EXISTING ѡ�
		// 2�������Ҫ�����ļ������ԣ� ����ʹ�� COPY_ATTRIBUTES ��
		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
		out.println("copy over! ");
		
		// �ƶ��ļ� �����Ʋ�ɾ��Դ�ļ���
		source = Paths.get(workDir, "test.dat");
		destination = Paths.get("E:", "tempdocument", source.getFileName().toString());
		
		// ���ƶ���������Ϊ ԭ���Եģ�Ҫôȫ���ɹ��� Ҫô�Ͳ��ɹ���
		Files.move(source, destination, StandardCopyOption.ATOMIC_MOVE);
		out.println("move over! ");
		
		Files.delete(source);
	}
	
	public static void main4(String[] args) throws IOException
	{
		Path absolute = Paths.get(workDir, "output.txt");
		// ������ķ�ʽ�����׵Ķ�ȡ�ļ����������ݣ�
		byte[] bytes = Files.readAllBytes(absolute);
		
		//��ָ���ļ�׷������
		Files.write(absolute.resolveSibling("test.txt"), bytes, StandardOpenOption.APPEND);
		out.print("\n writing over !");
		
		//���ϣ�� ���ļ���������������
		List<String> lines = Files.readAllLines(absolute);
		// ��һ�м���д�����ļ���
		Files.write(absolute.resolveSibling("test.txt"), lines);
	}
	
	public static void main3(String[] args) throws IOException
	{
		Path absolute = Paths.get(workDir, "output.txt");
		// ������ķ�ʽ�����׵Ķ�ȡ�ļ����������ݣ�
		byte[] bytes = Files.readAllBytes(absolute);
		for (byte b : bytes)
		{
			out.print((char)b);
		}
		
		// ����뽫�ļ������ַ�������
		String content = new String(bytes, Charset.defaultCharset());
		out.print("\n" + content + "\n");
		
		//���ϣ�� ���ļ���������������
		List<String> lines = Files.readAllLines(absolute);
		for (String str : lines)
		{
			out.println(str);
		}
		
		//���ϣ��д��һ���ַ������ļ���
		Files.write(absolute.resolveSibling("test.txt"), content.getBytes());
		out.print("\n writing over !");
	}
	public static void main2(String[] args) throws IOException
	{
//		Path absolute = Paths.get(workDir, "output.txt");
		Path absolute = Paths.get(workDir);
		out.println("absolute path = " + absolute.toString());
		
		Path child1 =  absolute.resolve("output.txt");
		out.println("child1 path = " + child1.toString());
		
		Path child2 =  absolute.resolve("E:\\");
		out.println("child2 path = " + child2.toString());
		
		Path brother1 =  absolute.resolveSibling("xiaotang");
		out.println("brother1 path = " + brother1.toString());
		
	}
	
	public static void main1(String[] args) throws IOException
	{
		Path absolute = Paths.get(workDir, "output.txt");
		out.println("root = " + absolute.getRoot());  
        out.println("full path = " + absolute.toString());  
        out.println("file name = " + absolute.getFileName());  
        // 8 ��·���ָ���'\\'
        out.println("name count = " + absolute.getNameCount());  
        out.println("sub path = " + absolute.subpath(0, 7)); 
        
        out.println("\n ===relative path as follows ");
        Path relative = Paths.get("myprog", "cay");
        out.println(relative.getRoot());  
        out.println(relative.toString());  
        out.println(relative.getFileName());  
	}
}
