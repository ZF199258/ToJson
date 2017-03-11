package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 安卓客户端拼sql 
 * 返回json
 * 查询
 * @author ZF
 *哈哈哈哈哈哈哈
 */
public class SqlToJson {
 
	private  static PreparedStatement ps;
	private  static ResultSet rs;
	private static Connection con; 
	
	/**
	 * 该方法根据列得到要查询的列 和值 返回 键值对
	 * @param line 列明
	 * @param sql  
	 * @param split 行分隔符可自己定义
	 * @return
	 */
	public static String getTestcon(String line,String sql  ,String  split)
	{
		con = JdbcUtil.getConnection();
		
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			String keyvalue = "";
				while (rs.next()) {
		        String[] ary = line.split(",");
				 for (int i = 0; i <ary.length; i++) {
				 keyvalue +="\"" + ary[i] + "\"" +":"+ "\"" + rs.getString(i + 1)+ "\"" +",";
				}
				 keyvalue =deletezh(keyvalue)+split; 
				}
				keyvalue = deletezh(keyvalue);
				
				 String[] entitys = keyvalue.split(split);
				 int mentitys = entitys.length;  
				 String proto_json =keyvalue.replace(split,"},{"); 
				 if(mentitys>1)
					{  
						StringBuffer json =new StringBuffer();
						json.append("{"+"\""+"date"+"\""+":");
						json.append("["+"{");
						json.append(proto_json);
						json.append("}"+"]"+"}");
						System.out.println("json"+json);
						return json.toString();
					}
					else
					{
			           String  json  =  "{"+proto_json+"}";
			           System.out.println("json"+json);
			           return json;
					}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return null;
	}
	private static String deletezh(String keyvalue) {
		return keyvalue.substring(0,keyvalue.length()-1);
	}
	private  static void close() {
		JdbcUtil.closeResources(con, ps, rs);
	}
}
