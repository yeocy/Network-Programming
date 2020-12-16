package Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Arrays;


public class Server {
   /*
    * clients : Client�� name�� printWriter�� HashMasp ���·� key�� value�� Mapping�Ͽ� ������ ����
    */   
   private static Map<String, PrintWriter> clients = new HashMap<String, PrintWriter>();
   private static String GroupChat="";

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
      String id; //�߰���
      private String name;
      String info; //�߰���
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
            out.println("SUBMIT");
            while (true) {
               String input = in.nextLine();
               
               if (input == null) {
                  return;
               } else if (input.startsWith("Sign_Up")) {
                  String[] lines = input.split(",/");
                  try {
                     for(int i = 0; i< 9; i++) {
                        System.out.println(lines[i]); // TODO �α� ���ܾ� �ϴ��� ���庸��
                     }
                     if (db.checkID(lines[1])==false) {
                        db.createAccount(lines[1], lines[2], lines[3], lines[4], lines[5], lines[6], lines[7], lines[8]);
                        out.println("CREATE_SUCCESS");
                     } else {out.println("DUPLICATED_ID");}
                                          
                  } catch (ArrayIndexOutOfBoundsException e) {out.println("EXCEPTION_OCCURED");}
                   
               } else if (input.startsWith("send_ID_PW")) {
                  String[] lines = input.split(" ");
                  System.out.println(input); // TODO ��ȣȭ���� ���� password���� �����. �����ʿ��ҵ���
                  try {
                     this.id = lines[1];
                     this.info = db.getStatusMessage(id);
                     this.name = db.getName(id);
                  
                  if (db.logIn(lines[1], lines[2])==true) {
                     out.println("SUBMITLOGIN 1");
                     break;
                  } else { out.println("SUBMITLOGIN 0"); }
                  } catch (ArrayIndexOutOfBoundsException e) {
                     out.println("SUBMITLOGIN 0");
                  }
               } else if (input.startsWith("FIND_ID_REQUEST")) {
                  System.out.println(input);
                  String[] lines = input.split(",/");
                  try {
                     String foundId =db.findID(lines[1], lines[2], lines[3]); 
                     if(foundId==null) {throw new Exception();}
                     out.println("FIND_ID_SUCCESS "+foundId);                     
                  } catch (ArrayIndexOutOfBoundsException e) {
                     out.println("EXCEPTION_OCCURED");
                  } catch (Exception e) {
                     out.println("EXCEPTION_OCCURED");
                  }
               } else if (input.startsWith("FIND_PW_REQUEST")) {
                  System.out.println(input);
                  String[] lines = input.split(",/");
                  try {
                     String foundPw = db.findID(lines[1], lines[2], lines[3]);
                     if(foundPw==null) {throw new ArrayIndexOutOfBoundsException();}
                     out.println("FIND_PW_SUCCESS "+foundPw);                     
                  } catch (ArrayIndexOutOfBoundsException e) {
                     out.println("EXCEPTION_OCCURED");
                  }
               }
            }            
            clients.put(id, out);
            out.println("NAMEACCEPTED " + id + ",/" + name + ",/" + info);
            String[] friends = db.getFriendList(id).split(" ");
            for(int i = 0; i< db.getNumberOfFriends(id); i++) {
               String frId = friends[i];
               out.println("FRIENDLIST" + ",/" + frId + ",/" + db.getNickName(frId) + ",/" + db.getName(frId) + ",/"
                     + db.getBirthDay(frId) + ",/" + db.getEmail(frId) + ",/" + db.getStatusMessage(frId) + ",/"
                     + db.getPhoneNumber(frId) + ",/" + db.getConnectionStatus(frId)   + ",/" + db.getLatestAccess(frId));
            } 
            out.println("SET");   
            
            while (true) {
               String input = in.nextLine();
               //����� ���� ����
               if(input.startsWith("Change name")) {
                  name = input.substring(12);
                  db.updateName(id,name);
                  out.println("Change name:"+name);
               }
               else if(input.startsWith("Change info")) {
                  info = input.substring(12);
                  db.updateStatusMessage(id, info);
                  out.println("Change info:"+info);
               }
               else if(input.startsWith("Insert_Friend")) {
                  System.out.println(input);
                  try {                     
                     String frId = input.split(",/")[2];
                     if (!id.equals(frId)&&db.checkID(frId)==true) {
                        db.addFriend(id,frId);
                        out.println("FRIEND_ADD"+ ",/" + frId + ",/" + db.getNickName(frId) +",/" + db.getName(frId) + 
                              ",/" +db.getBirthDay(frId) +",/"+db.getEmail(frId)+ ",/" + db.getStatusMessage(frId) +
                              ",/" + db.getPhoneNumber(frId) + ",/" + db.getConnectionStatus(frId) + ",/" + db.getLatestAccess(frId));                        
                     } else { out.println("EXCEPTION_OCCURED"); }
                  } catch (ArrayIndexOutOfBoundsException e) {
                     out.println("EXCEPTION_OCCURED");
                  }
               } 
               
               //�˻����
               else if(input.startsWith("Search")) { 
                  String keyword = input.substring(8);
                  System.out.println(keyword);
                  String send="search_value";
                  if(db.searchItems(id,keyword).equals("")) {
                     send += (":/"+" "+",/"+" "+",/"+" "+",/"+" "+",/"+" "+",/"+" "+",/"+" "+",/"+" "+",/"+" "+",/");               
                  } else {
                     String[] lines = db.searchItems(id,keyword).split(",/");
                     lines = new HashSet<String>
                     (Arrays.asList(lines)).toArray(new String[0]); //�ߺ�����
                     
                     for (String frid:lines) {
                        send += ":/" +frid+ ",/" + db.getNickName(frid)+ ",/" + db.getName(frid)+ ",/" 
                              + db.getBirthDay(frid) + ",/" + db.getEmail(frid)+ ",/" + db.getStatusMessage(frid)+ ",/" +db.getPhoneNumber(frid)
                              + ",/" + db.getConnectionStatus(frid) + ",/" +db.getLatestAccess(frid);
                     }
                  }
                  System.out.println(send);
                  out.println(send);
                   
               }
               //1:1��ȭ ��� 
               else if(input.startsWith("chat_start_0")) {
                  //1:1 ��ȭ ����
                  String[] lines = input.split(",/");
                  out.println("chat_start:"+lines[2]); //lines[2]: ģ�� ���̵�
                  if(clients.containsKey(lines[2])) {
                     clients.get(lines[2]).println("chat_question " + lines[1]);
                  }else {
                     out.println("chat_fail");
                  }
               }else if(input.startsWith("chat_fail")) {
                  clients.get(input.substring(10)).println("chat_failed");
               }else if(input.startsWith("chat_success")) {
                  System.out.println(input);
                  String[] lines = input.split(",/");
                  clients.get(lines[1]).println("chat_start:"+lines[2]);
                  clients.get(lines[2]).println("chats_start:"+lines[1]);
                  clients.get(lines[1]).println("chats_start:"+lines[2]);
               }else if(input.startsWith("chat_start_Group")) {
                  //TODO �׷��ȭ ���� ����
                  String[] chats = input.split(",/");
//                  String chat = "";
                  for(int i = 1; i < chats.length; i++) {
                     if(clients.containsKey(chats[i])){
                        clients.get(chats[i]).println("group_chat_question " + id);
                     }
                  }
                  if(GroupChat.equals("")) {
                     GroupChat = GroupChat + id;                     
                  }
               }else if(input.startsWith("group_chat_success")) {
                  GroupChat = GroupChat +",/"+ input.substring(19);
                  System.out.println(GroupChat);
                  String[] line = GroupChat.split(",/");
                  for(int i = 0; i < line.length ; i++) {
                     clients.get(line[i]).println("group_chat_start"+GroupChat);
                  }
               }else if(input.startsWith("Message")) {
                  //TODO 1:1 ��ȭ ���� ����
                  String[] lines = input.split(",/");
                  //�޼��� ������ ����
                  if(lines[3].equalsIgnoreCase("/quit")) {
                     clients.get(lines[1]).println("WindowQuit");
                     clients.get(lines[2]).println("WindowQuit");
                  } else {
                     clients.get(lines[1]).println("Message,/"+lines[1]+",/"+lines[2]+",/"+ lines[3]);
                     clients.get(lines[2]).println("Message,/"+lines[1]+",/"+lines[2]+",/"+ lines[3]);
                  }
               }
               else if(input.startsWith("Group_Chat")) {
                  //TODO 1:1 ��ȭ ���� ����
                  System.out.println(input);
                  String[] lines = input.split(",/");
                  String line = "";
                  for(int i = 1; i< lines.length;i++) {
                     line = line + ",/" + lines[i];
                  }
                  System.out.println(line);
//                  �޼��� ������ ����
                  if(lines[2].equalsIgnoreCase("/quit")) {
                     out.println("GroupWindowQuit");
                  } else {
                     String[] client = GroupChat.split(",/");
                     for(int i = 0; i < client.length ; i++) {
                        clients.get(client[i]).println("Group_Message"+line);
                     }
                  }
               } else if (input.startsWith("LOGOUT_CLICKED")) {
                  //�α׾ƿ� (�ֱ����ӳ�¥ ������Ʈ)
                  db.logOut(id);
               } else if (input.startsWith("CANCEL_ACCOUNT_REQUEST")) {
                  //ȸ�� Ż�� ����
                  System.out.println(input);
                  String[] lines = input.split(",/");
                  if (db.cancelAccount(lines[1], lines[2],lines[3])==true) {
                     out.println("ACCOUNT_CANCELED");                     
                  }                  
               } else if (input.startsWith("delete friend")) {
                  //ģ�� ����
                  String frId = input.substring(14);
                  db.deleteFriend(id, frId);
               } 
            }
         } catch (java.util.NoSuchElementException e) {
            db.logOut(id);
         } catch (Exception e) {
            //Error Message ó��
            e.printStackTrace();
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