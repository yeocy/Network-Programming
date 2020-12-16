package Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;


public class Server {

	/*
	 * clients : Client�� name�� printWriter�� HashMasp ���·� key�� value�� Mapping�Ͽ� ������ ����
	 */	
	private static Map<String, PrintWriter> clients = new HashMap<String, PrintWriter>();


	public static void main(String[] args) throws Exception {
		System.out.println("The chat server is running...");
		ExecutorService pool = Executors.newFixedThreadPool(500);
		try (ServerSocket listener = new ServerSocket(59001)) {
			while (true) {
				pool.execute(new Handler(listener.accept()));
			}
		}
	}

	/**
	 * The client handler task.
	 */
	private static class Handler implements Runnable {
		/*
		 * name : ������� �̸��� ������ ����
		 * socket : socket����� �� Server socket�� ����
		 * in : ���� �Է� �޾ƿ� Scanner
		 * out : ���� ���� �� PrintWriter
		 */
		private String name;
		private Socket socket;
		private Scanner in;
		private PrintWriter out;

		/**
		 * Constructs a handler thread, squirreling away the socket. All the interesting
		 * work is done in the run method. Remember the constructor is called from the
		 * server's main method, so this has to be as short as possible.
		 */
		public Handler(Socket socket) {
			//����� ��� ���� socket�� socket�������� ����
			this.socket = socket;
		}

		/**
		 * Services this thread's client by repeatedly requesting a screen name until a
		 * unique one has been submitted, then acknowledges the name and registers the
		 * output stream for the client in a global set, then repeatedly gets inputs and
		 * broadcasts them.
		 */
		public void run() {
			DBConnection db = new DBConnection();
			try {
				/*
				 * in�� socket���� ���� �޾ƿ� ����, out�� socket�� ���� ������ ����
				 */
				in = new Scanner(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream(), true);
				
				
				// Keep requesting a name until we get a unique one.
//				while (true) {
//					out.println("SUBMITNAME");
//					name = in.nextLine();
//					if (name == null) {
//						return;
//					}
//					synchronized (clients) {
//						if (name.length() > 0 && !clients.containsKey(name)) {
//							for (PrintWriter writer : clients.values()) {
//								writer.println("MESSAGE " + name + " has joined");
//							}
//							clients.put(name, out);
//							break;
//						}
//					}
//				}
				//TODO �ӽ÷� ������� �̸��� �־�� ������ ������ ���� �ڵ�
				String id = "123";
				name = "������";
				String info = "����";
				ArrayList<Friend> friend = new ArrayList<Friend>();
				
				   //id, �г���, �̸�, ����, �̸���, ���ټҰ�(���¸޼���), ��ȭ��ȣ, �¶���, �ֱ����ӳ�¥ ������ ���� 
	            friend.add(new Friend("111", "��μ��г���","��μ�", "2020-12-12","aaa@gachon.ac.kr","�����մϴ�.", "010-2839-2892", "(��������)", "10/26"));
	            friend.add(new Friend("222", "��ä���г���","��ä��", "2020-12-12","aaa@gachon.ac.kr","�ڹ� �ڵ� ���� ����� �Ф�", "010-5985-9287", "(�¶���)", "11/26"));
	            friend.add(new Friend("333", "�������г���","������", "2020-12-12","aaa@gachon.ac.kr","���� �ʹ� �ϱ� �ȴ� ��¥ ��...", "010-7547-2874", "(��������)", "11/24"));
	            friend.add(new Friend("444", "����δг���","�����", "2020-12-12","aaa@gachon.ac.kr","�̹� ������ ���� ������", "010-3456-9298", "(��������)", "11/1"));
	            friend.add(new Friend("555", "Ȳ�����г���","Ȳ����", "2020-12-12","aaa@gachon.ac.kr","������ �Ѱ� �� ����", "010-4782-5895", "(�¶���)", "11/26"));
				
				//����ڰ� �α��� �Ϸ�ƴٴ� �޼���
				out.println("NAMEACCEPTED " + id + " " + name + " " + info);
				for(int i = 0; i< friend.size(); i++) {
					out.println("FRIENDLIST"+ ",/" + friend.get(i).id+ ",/" + friend.get(i).nickname + ",/" + friend.get(i).name + ",/" +friend.get(i).birth 
							+ ",/" + friend.get(i).email + ",/" + friend.get(i).info + ",/" + friend.get(i).getNumber() + ",/" + friend.get(i).getOnline() + ",/" + friend.get(i).getLast_date());
				}
				out.println("SET");
				
				
				while (true) {
					String input = in.nextLine();
					String[] lines;
					//����� ���� ����
					if(input.startsWith("Change name")) {
						//db.updateName("�����������̵�",input.substring(12));
						name = input.substring(12);
						out.println("Change name:"+name);
						//TODO ����� �̸� ���� DB ���� // "�����������̵�" �������
					}
					else if(input.startsWith("Change info")) {
						//db.updateStatusMessage("�����������̵�", input.substring(12));
						info = input.substring(12);
						out.println("Change info:"+info);
						//TODO ����� ���� �Ұ� ���� DB ����.// "�����������̵�" �������
					}
					//�˻����
					else if(input.startsWith("search")) {
						db.searchID(input.substring(7));
//						System.out.println(input.substring(7));
						//TODO �˻� ���� ���� DB���� //Console â���� ��µ�. 
					}
					//1:1��ȭ ���
					else if(input.startsWith("chat_start_0")) {
						//TODO 1:1 ��ȭ ���� ����
						System.out.println(input.substring(13));
						String chat = input.substring(13);
//						if (db.getConnectionStatus(input.substring(13)).equals("online")){
//							// TODO ���̵� input.substring(13)�� ������ �¶����̶�� ���� ��û
//						}
						out.println("chat_start:"+chat);
					}
					else if(input.startsWith("Message")) {
						//TODO 1:1 ��ȭ ���� ����
						lines = input.split(",/");
						//�޼��� ������ ����
						if(lines[3].equalsIgnoreCase("/quit")) {
							out.println("WindowQuit");
						}else {
							out.println("Message,/"+lines[1]+",/"+lines[2]+",/"+ lines[3]);
						}
					}
					else if(input.startsWith("send_ID_PW")) {
						lines = input.split(" ");
						System.out.println("lines");
						if (db.logIn(lines[0], lines[1])==true) {
							out.println("SUBMITLOGIN");
						}
					}
//					
//					if(input.startsWith("<")) {
//						try{
//							int idx = input.indexOf("/");
//							String whisper = input.substring(1, idx);
//							input = input.substring(idx+2);
//							if(clients.containsKey(whisper)) {
//								PrintWriter writer = clients.get(whisper);
//								writer.println("MESSAGE " + name + ": " + input);
//							}else {
//								PrintWriter writer = clients.get(name);
//								writer.println("MESSAGE " + "ERROR : whisper type: <name/> content");
//							}
//						}catch(StringIndexOutOfBoundsException e) {
//							for (PrintWriter writer : clients.values()) {
//								writer.println("MESSAGE " + name + ": " + input);
//							}
//						}
//					}else {
//						for (PrintWriter writer : clients.values()) {
//							writer.println("MESSAGE " + name + ": " + input);
//						}
//					}				
				}
	
			}catch (Exception e) {
				//Error Message ó��
				System.out.println(e);
			} finally {
				if (out != null && name!= null) {
					System.out.println(name + " is leaving");
					clients.remove(name);
					for (PrintWriter writer : clients.values()) {
						writer.println("MESSAGE " + name + " has left");
					}
				}
				try { socket.close(); } catch (IOException e) {}
			}
		}
	}

}
