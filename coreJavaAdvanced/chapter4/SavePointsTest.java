package com.corejava.chapter4;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Properties;

public class SavePointsTest
{
	private static String cur_dir = System.getProperty("user.dir")
			+ File.separator + "com" + File.separator + "corejava"
			+ File.separator + "chapter4" + File.separator;

	public static void main(String[] args)
	{
		Connection conn = null;
		Savepoint savepoint1 = null;
		try
		{
			Connection conn1 = getConnection();
			conn = conn1;
			conn.setAutoCommit(false);// step1.�ر��Զ��ύ
			Statement stat = conn.createStatement();
			
			// set a Savepoint
			savepoint1 = conn.setSavepoint("savepoint1");	
			
			// step2.�ռ�����������add batch update��
			stat.addBatch("insert into student values(1,'savepoint1')");
			stat.addBatch("insert into student values(1,'savepoint2')");
			
			// step3.ִ�в��ύ�ò���
			stat.executeBatch();
			// If there is no error, commit the changes.
			conn.commit();
			
			// step4.�ظ�������Զ��ύģʽ
			conn.setAutoCommit(true);
			System.out.println("successful update!");
			
			conn.releaseSavepoint(savepoint1); //��������Ҫ����� ��ʱ�� �����ͷ���
			conn.close();//�ر����ݿ�����
		 
		} catch (Exception e)
		{
			try
			{
				conn.rollback(savepoint1);//�ع�
				System.out.println("failed update!");
				e.printStackTrace(); 
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	public static Connection getConnection() throws IOException, SQLException
	{
		Properties prop = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get(cur_dir
				+ "database.properties")))
		{
			prop.load(in);
		}
		String drivers = prop.getProperty("jdbc.drivers");
		if (drivers != null)
		{
			System.setProperty("jdbc.drivers", drivers); // register drivers for
															// accessing
															// database
		}

		String url = prop.getProperty("jdbc.url");
		String user = prop.getProperty("jdbc.username");
		String pass = prop.getProperty("jdbc.password");
		return DriverManager.getConnection(url, user, pass);
	}
}
