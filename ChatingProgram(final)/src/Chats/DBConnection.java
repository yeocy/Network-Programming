package Chats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;


public class DBConnection {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	String DBID = "root";
	String DBPW = "projectGroup6";
	String DBName = "userinfo";
    String TableName = "information";
    String SQL;
			
	public DBConnection() {
		InfoConfig i = new InfoConfig();
		i.getInfo();
		
		String IP = i.Ip;
		String Port = i.Port;
		try {			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+IP+":"+Port+"/"+DBName+"?characterEncoding=UTF-8&serverTimezone=UTC";
			con = DriverManager.getConnection(url,DBID,DBPW);
			st = con.createStatement();
			
		} catch (Exception e) {
			System.out.println("�����ͺ��̽� ���� ����: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void CreateTable() {
		try {
			SQL = "CREATE TABLE "+TableName 
					+" ID VARCHAR(20) PRIMARY KEY NOT NULL,"
					+ "NickName VARCHAR(20) NOT NULL,"
					+ "Name VARCHAR(20) NOT NULL,"
					+ "Birth DATE NOT NULL,Email VARCHAR(30) NOT NULL,"
					+ "PhoneNum VARCHAR(13),"
					+ "StatusMessage VARCHAR(100),"
					+ "ConnectionStatus VARCHAR(7) DEFAULT 'offline',"
					+ "LatestAccess datetime DEFAULT NOW(),"
					+ "PW VARCHAR(100) NOT NULL";
			st.executeUpdate(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkID(String ID) { //DB�� ID�� �����ϸ� true, �׷��� ������ false�� return.
		try {
			SQL = "SELECT * FROM "+TableName+" WHERE ID = '"+ID+"'";
			rs = st.executeQuery(SQL);
			if(rs.next()) {return true;}
		} catch (Exception e) {
			e.printStackTrace();
		} return false;
	}
	public void createAccount(String ID,String NickName,String Name,String Birth,String Email,String PhoneNum,String StatusMessage,String PW) {
		//���� ����. DB�� �Է��� �ʼ� ��ҵ��� �Է¹޴´�.
		try {
			SQL = "INSERT INTO "+TableName+"(ID,NickName,Name,Birth,Email,PhoneNum,StatusMessage,PW) "
					+"VALUES ('"+ID+"','"+NickName+"','"+Name+"','"+Birth+"','"+Email+"','"+PhoneNum+"','"+StatusMessage+"', HEX(AES_ENCRYPT('"+PW+"','"+ID+"')))";
			st.executeUpdate(SQL);
			SQL = "CREATE TABLE "+ID+"friends (FriendID VARCHAR(20) PRIMARY KEY NOT NULL)";
			st.executeUpdate(SQL);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("ID�� �̹� �����մϴ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	public boolean cancelAccount(String ID, String PW, String Email) { // ���� ����.
		boolean res = false;
		try {
			SQL = "SELECT * FROM "+TableName+" WHERE ID='"+ID+"' and PW=HEX(AES_ENCRYPT('"+PW+"','"+ID+"')) and Email='"+Email+"'";
			rs = st.executeQuery(SQL);
			
			if(rs.next()) {
				SQL="DELETE FROM "+TableName+" WHERE ID='"+ID+"'";
				st.executeUpdate(SQL);
				SQL="DROP TABLE "+ID+"friends";
				st.executeUpdate(SQL);
				res=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	public boolean logIn(String ID, String PW) { // �α��� �����ϸ� true ����
		//�α���
		boolean res=false;
		try {
			SQL = "UPDATE "+ TableName+" SET connectionStatus='online' WHERE ConnectionStatus='offline'and ID='"+ID+"'";
			st.executeUpdate(SQL);
			SQL = "UPDATE "+ TableName+" SET LatestAccess=NOW() WHERE ID='"+ID+"'";
			st.executeUpdate(SQL);
			SQL = "SELECT * FROM "+TableName+" WHERE ID='"+ID+"' and PW=HEX(AES_ENCRYPT('"+PW+"','"+ID+"'))";
			rs = st.executeQuery(SQL);
			if(rs.next()) {res=true;}
		} catch (Exception e) {
			e.printStackTrace();
		} return res;
	}
	public void logOut(String ID) { 
		//�α׾ƿ�
		try {
			SQL = "UPDATE "+ TableName+" SET connectionStatus='offline' WHERE ConnectionStatus='online'and ID='"+ID+"'";
			st.executeUpdate(SQL);
			SQL = "UPDATE "+ TableName+" SET LatestAccess=NOW() WHERE ID='"+ID+"'";
			st.executeUpdate(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getConnectionStatus(String ID) { //���� ������ return.(online/offline)
		String cs=null; //connection status
		try {
			SQL = "SELECT ConnectionStatus FROM "+TableName+" WHERE ID='"+ID+"'";
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				cs = rs.getString("ConnectionStatus");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cs;
	}
	public void addFriend(String myID,String FrID) {
		try {
			if(checkID(FrID)==true) {
				SQL = "INSERT INTO "+myID+"friends (FriendID) SELECT ID from "+TableName+" where ID='"+FrID+"'";
				st.executeUpdate(SQL);
				SQL = "INSERT INTO "+FrID+"friends (FriendID) SELECT ID from "+TableName+" where ID='"+myID+"'";
				st.executeUpdate(SQL);
			} else { throw new SQLException();}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getStatusMessage(String ID) { //���� �޼���(������ �Ѹ���)�� return.
		String sm=null; //status message
		try {
			SQL = "SELECT StatusMessage FROM "+TableName+" WHERE ID='"+ID+"'";
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				sm = rs.getString("StatusMessage");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sm;
	}
	public String updateStatusMessage(String ID,String newmsg) {
		//���¸޼���(������ �Ѹ���)�� update.
		String msg=null;
		try{
			SQL = "UPDATE "+ TableName+" SET StatusMessage='"+newmsg+"' WHERE ID='"+ID+"'";
			st.executeUpdate(SQL);
			msg = newmsg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
	public String getPhoneNumber(String ID) { //��ȭ��ȣ�� return.
		String pn=null; //phone number
		try {
			SQL = "SELECT PhoneNum FROM "+TableName+" WHERE ID='"+ID+"'";
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				pn = rs.getString("PhoneNum");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pn;
	}
	public String updatePhoneNumber(String ID, String newnum) { //��ȭ��ȣ�� update
		String num=null;
		try {
			SQL = "UPDATE "+ TableName+" SET PhoneNum='"+newnum+"' WHERE ID='"+ID+"'";
			st.executeUpdate(SQL);
			num = newnum;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	public String getBirthDay(String ID) { //��������� return.
		String bth=null; //birthday
		try {
			SQL = "SELECT Birth FROM "+TableName+" WHERE ID='"+ID+"'";
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				bth = rs.getString("Birth");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return bth;
	}
	public String updateBirthDay(String ID, String newbth) { //��ȭ��ȣ�� update
		String bth=null;
		try {
			SQL = "UPDATE "+ TableName+" SET Birth='"+newbth+"' WHERE ID='"+ID+"'";
			st.executeUpdate(SQL);
			bth = newbth;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return bth;
	}
	public String getEmail(String ID) { //�̸����� return.
		String email=null;
		try {
			SQL = "SELECT Email FROM "+TableName+" WHERE ID='"+ID+"'";
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				email = rs.getString("Email");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return email;
	}
	public String updateEmail(String ID, String newEmail) { //��ȭ��ȣ�� update
		String email=null;
		try {
			SQL = "UPDATE "+ TableName+" SET Email='"+newEmail+"' WHERE ID='"+ID+"'";
			st.executeUpdate(SQL);
			email = newEmail;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return email;
	}
	public String getName(String ID) { //����� �̸��� return.
		String nm=null;
		try {
			SQL = "SELECT Name FROM "+TableName+" WHERE ID='"+ID+"'";
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				nm = rs.getString("Name");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return nm;
	}
	public String updateName(String ID,String newnm) { //����� �̸��� ������Ʈ�Ѵ�.
		String name = null;
		try{
			SQL = "UPDATE "+ TableName+" SET Name='"+newnm+"' WHERE ID='"+ID+"'";
			st.executeUpdate(SQL);
			name = newnm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	public String getNickName(String ID) { //����� �̸��� return.
		String nm=null;
		try {
			SQL = "SELECT NickName FROM "+TableName+" WHERE ID='"+ID+"'";
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				nm = rs.getString("NickName");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return nm;
	}
	public String updateNickName(String ID,String newnm) { //����� �̸��� ������Ʈ�Ѵ�.
		String nickname = null;
		try{
			SQL = "UPDATE "+ TableName+" SET NickName='"+newnm+"' WHERE ID='"+ID+"'";
			st.executeUpdate(SQL);
			nickname = newnm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nickname;
	}
//	public String searchItems(String keyword) { //ID �˻�
//		String searchId="";
//		String searchName="";
//		String searchNickname="";
//		String searchResult="";
//		try {
//			SQL = "SELECT * FROM "+TableName+" WHERE ID LIKE '%"+keyword+"%'";
//			rs=st.executeQuery(SQL);
//			while(rs.next()) {searchId = rs.getString("ID");}
//			
//			SQL = "SELECT * FROM "+TableName+" WHERE Name LIKE '%"+keyword+"%'";
//			rs=st.executeQuery(SQL);
//			while(rs.next()) {searchName = rs.getString("ID");}
//			
//			SQL = "SELECT * FROM "+TableName+" WHERE Nickname LIKE '%"+keyword+"%'";
//			rs=st.executeQuery(SQL);			
//			while(rs.next()) {searchNickname = rs.getString("ID");}
//			
//			searchResult = 
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return searchResult;
//	}
	public String searchPW(String ID, String Email, String PhoneNum) {
		String pw = null;
		try {
			SQL = "SELECT * FROM "+TableName+" WHERE ID='"+ID+"' and Email='"+Email+"' and PhoneNum = '"+PhoneNum+"'";
			rs = st.executeQuery(SQL);
			if(rs.next()) {
				SQL = "SELECT CAST(AES_DECRYPT(UNHEX(PW),'"+ID+"')AS CHAR(50)) FROM "+TableName+" WHERE ID='"+ID+"'";
				rs = st.executeQuery(SQL);
				while(rs.next()) {
					pw = rs.getString("CAST(AES_DECRYPT(UNHEX(PW),'"+ID+"')AS CHAR(50))");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} return pw;
	}
	public int getNumberOfFriends(String myID) { //ID �˻�
		int num=0;
		try {
			SQL = "SELECT COUNT(*) FROM "+myID+"friends";
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				 num = Integer.parseInt(rs.getString("COUNT(*)"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return num;
	}
	public String getFriendList(String myID) { //ID �˻�
		String flist="";
		try {
			SQL = "SELECT * FROM "+myID+"friends";
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				 flist += (rs.getString("FriendID")+" ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return flist;
	}
	public String getLatestAccess(String ID) {
		String time="";
		try {
			SQL = "SELECT LatestAccess FROM "+TableName+" WHERE ID='"+ID+"'";
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				 time = rs.getString("LatestAccess");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}
	
	

}