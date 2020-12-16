package Chats;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.*;

import javax.swing.*;

public class Main {
    Scanner in;
    PrintWriter out;
    String id, name, info;
    LoginWindows lgw = new LoginWindows();
    ArrayList<Friend> friend = new ArrayList<Friend>();
    ArrayList<Friend> search_friend = new ArrayList<Friend>();
    JFrame frame = new JFrame("Chatter");
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel chatPanel = new JPanel();
    JPanel friendPanel;
    public JPanel searchPanel_list;
    JButton button1 = new JButton("<html>�̸�<br>����</html>");
	JButton button2 = new JButton("<html>�Ұ�<br>����</html>");		
	JButton button3 = new JButton("ģ��");
	JButton button4 = new JButton("ä��");
	JButton button5 = new JButton("<html>ģ��<br>���</html>");
	JButton button6 = new JButton("<html>�α�<br>�ƿ�</html>");
	JButton button7 = new JButton("<html>ȸ��<br>Ż��</html>");
	
	NewWindow window;
	NewGroupWindow groupwindow;
	
	boolean frag = false;
	
    public Main() {
    	
    }
    //ģ�� ����� ��Ÿ���� â
    private void Editfriend(JFrame frame, JPanel friendPanel, int i, PrintWriter out ) {
		JButton button = new JButton(Integer.toString(i));
		button.setPreferredSize(new Dimension(250,30));
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		
		button.setText(friend.get(i).name+"   "+friend.get(i).info);
    	button.setHorizontalAlignment(SwingConstants.LEFT);
    	button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] list = {"1:1��ȭ", "ģ������ ", "���"};
				int select = JOptionPane.showOptionDialog(frame, friend.get(i).getName()+" "+ friend.get(i).getNumber() + " "
						+ friend.get(i).getLast_date() +" " + friend.get(i).getOnline(), friend.get(i).getName(), 
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, list, list[0]);
				if(select == 0) {
					out.println("chat_start_0 "+ friend.get(i).id);
				}
				if(select == 1) {
					out.println("delete friend " + friend.get(i).id);
				}
			}
		});
		
		friendPanel.add(button);
    }
    //�˻������ ��Ÿ��
    private void EditSearch(JFrame frame, JPanel searchPanel, int i, PrintWriter out ) {
		JButton button = new JButton(Integer.toString(i));
		button.setPreferredSize(new Dimension(250,30));
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setText(search_friend.get(i).name+"   "+search_friend.get(i).info);
    	button.setHorizontalAlignment(SwingConstants.LEFT);
    	button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] list = {"1:1��ȭ", "ģ������ ", "���"};
				int select = JOptionPane.showOptionDialog(frame, friend.get(i).getName()+" "+ friend.get(i).getNumber() + " "
						+ friend.get(i).getLast_date() +" " + friend.get(i).getOnline(), friend.get(i).getName(), 
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, list, list[0]);
				if(select == 0) {
					out.println("chat_start_0 "+ friend.get(i).id);
				}
				if(select == 1) {
					out.println("delete feind " + friend.get(i).id);
				}
			}
		});
		
    	searchPanel.add(button);
    }

    //�������� ���� ����
    private void EditMainPage(JFrame frame, JPanel leftPanel, JPanel rightPanel, JPanel chatPanel, PrintWriter out) {
    	leftPanel.setBackground(new Color(000, 102, 153));
		leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
		leftPanel.setPreferredSize(new Dimension(80, 0));
		
		button1.setPreferredSize(new Dimension(75,60));
		button2.setPreferredSize(new Dimension(75,60));
		button3.setPreferredSize(new Dimension(75,60));
		button4.setPreferredSize(new Dimension(75,60));
		button5.setPreferredSize(new Dimension(75,60));
		button6.setPreferredSize(new Dimension(75,60));		
		button7.setPreferredSize(new Dimension(75,60));		
		
		button1.setBackground(new Color(204,204,204));
		button2.setBackground(new Color(204,204,204));
		button3.setBackground(new Color(204,204,204));
		button4.setBackground(new Color(204,204,204));
		button5.setBackground(new Color(204,204,204));
		button6.setBackground(new Color(204,204,204));
		button7.setBackground(new Color(204,204,204));
		
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String BtnValue = JOptionPane.showInputDialog(
			            "Change name:",
			            name
			        );
				if(BtnValue != null) {
					out.println("Change name "+ BtnValue);
				}
			}
			
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String BtnValue = JOptionPane.showInputDialog(
			            "Change Introduction:",
			            info
			        );
				if(BtnValue != null) {
					out.println("Change info "+ BtnValue);
				}
			}
		});
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(chatPanel);
				chatPanel.removeAll();
				EditMain(frame, rightPanel, out, false);
				frame.getContentPane().add(rightPanel);
				frame.revalidate();
				frame.repaint();
			}
		});
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(rightPanel);
				rightPanel.removeAll();
				EditChat(frame, chatPanel, out);
				frame.getContentPane().add(chatPanel);
				frame.revalidate();
				frame.repaint();
			}
		});
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String BtnValue = JOptionPane.showInputDialog(
			            "ģ�� �߰�"
			        );
				if(BtnValue != null) {
					out.println("Insert_Friend,/"+id+",/"+ BtnValue);
				}
			}
		});
		button6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				out.println("LOGOUT_CLICKED");
				frame.dispose();
			}
		});
		button7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               JFrame r = new JFrame("ȸ��Ż��â");
               JPanel p = new JPanel();
               Label l1 = new Label("���̵�");
               Label l2 = new Label("��й�ȣ");
               Label l3 = new Label("�̸��� �ּ�");
           
               JTextField t1 = new JTextField();
               TextField t2 = new TextField();
               JTextField t3 = new JTextField();
               
               t2.setEchoChar('*');
           
               JButton j1 = new JButton("Ż��");
               JButton j2 = new JButton("���");
           
               l1.setBounds(40,10,40,40); l2.setBounds(40,50,60,40); l3.setBounds(40,90,80,40);
               t1.setBounds(120,10,200,30); t2.setBounds(120,50,200,30); t3.setBounds(120,90,200,30);
               j1.setBounds(125,130,80,30); j2.setBounds(240,130,80,30);
           
               r.add(l1); r.add(l2); r.add(l3); 
               r.add(t1); r.add(t2); r.add(t3);
               r.add(j1); r.add(j2); r.add(p);
        
               r.setSize(400,250);
               r.setLayout(null);
               r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               r.setVisible(true);
           
               j1.addActionListener(new ActionListener() {
            	   @Override
            	   public void actionPerformed(ActionEvent T) {
            		   try {
            			   out.println("CANCEL_ACCOUNT_REQUEST,/"+t1.getText()+",/"+t2.getText()+",/"+t3.getText());
            			   r.dispose();
                     } catch (Exception ex) {
                    	 ex.printStackTrace();
                     }
                     }
               });
               j2.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent T) {
                      r.dispose();
                     }
                });  
              }
        });
		
		leftPanel.add(button1);
		leftPanel.add(button2);
		leftPanel.add(button5);
		leftPanel.add(button3);
		leftPanel.add(button4);
		leftPanel.add(button6);
		leftPanel.add(button7);
		
		EditMain(frame, rightPanel, out, false);
		//EditChat(frame, chatPanel, out);
		
		frame.getContentPane().add(leftPanel, BorderLayout.WEST);
		frame.getContentPane().add(rightPanel);
		frame.setVisible(true);
	}
    
    //ģ�� �˻�, ģ�� ���, �˻�â�� ��Ÿ��
    private void EditMain(JFrame frame, JPanel rightPanel,  PrintWriter out, boolean searchIs) {
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		rightPanel.setPreferredSize(new Dimension(285, 100));
		JTextField search = new JTextField(18);
		search.setEditable(true);
		rightPanel.add(new JLabel("ģ���˻�: "));
		rightPanel.add(search);	
        
		friendPanel = new JPanel();
		JLabel friendList = new JLabel("-ģ�����-");
		JPanel searchPanel = new JPanel();
		
		friendList.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		friendPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		friendPanel.setPreferredSize(new Dimension(285, 1000));
		friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		friendPanel.add(friendList);
		
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		searchPanel.setBackground(Color.BLUE);
		searchPanel.setPreferredSize(new Dimension(285, 1000));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

   		for(int i = 0;i < friend.size();i++) {
   			Editfriend(frame, friendPanel, i, out);
   		}

    	rightPanel.add(friendPanel);
    	
			
        search.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
				if(search.getText().equals("")) {
                	searchPanel_list.setVisible(false);
                	friendPanel.setVisible(true);
                	rightPanel.validate();
                	rightPanel.repaint();
				}else{
					out.println("Search: "+search.getText());
				}
				search.setText("");
				
            }
        });
	}
    private void EditSearch_Panel(JFrame frame, JPanel rightPanel,  PrintWriter out) {
		
		searchPanel_list = new JPanel();
		
		searchPanel_list.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		searchPanel_list.setPreferredSize(new Dimension(285, 1000));
		searchPanel_list.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));


   		for(int i = 0;i < search_friend.size();i++) {
   			EditSearch(frame, searchPanel_list, i, out);
   		}

    	rightPanel.add(searchPanel_list);
	}
    private void EditChat(JFrame frame, JPanel chatPanel,  PrintWriter out) {
    	chatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
    	chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    	chatPanel.setPreferredSize(new Dimension(285, 100));
    	
    	JButton button = new JButton();
		button.setPreferredSize(new Dimension(250,30));
		button.setContentAreaFilled(false);
		
		button.setText("�׷�ä�� �����");
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> chat_list = new ArrayList<String>(); 
				JPanel al = new JPanel();
				for (int i = 0; i< friend.size();i ++){
				    JCheckBox box = new JCheckBox(friend.get(i).getName()+"("+friend.get(i).getId()+")");
				    box.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(chat_list.contains(box.getText())) {
								chat_list.remove(box.getText());
							}else {
								chat_list.add(box.getText());
							}
						}
				    	
				    });
				    al.add(box);
				}
				int d = JOptionPane.showConfirmDialog(null, al, "Information", JOptionPane.YES_NO_OPTION);
				if(d == 0) {
					ArrayList<String> idList = new ArrayList<String>();
					for(String i : chat_list) {
						idList.add(i.substring(i.lastIndexOf("(")+1, i.lastIndexOf(")")));
					}
					String output = "";
					for(int i = 0; i < idList.size(); i++) {
						output = output + ",/" + idList.get(i);
					}
					out.println("chat_start_Group"+output);
				}
			}
		});
		
		chatPanel.add(button);
    }
    
    
    private void run() throws IOException {
    	String localhost = "localhost";
		int socketnum = 59001;
		
		try {
        	Socket socket = new Socket(localhost, socketnum);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String [] lines;
                //�α��� ����
                if (line.startsWith("SUBMITLOGIN")) {
                	System.out.println(line.substring(12));
                	if(line.substring(12).equals("1")) {
                		lgw.loginsuccess();
                	}else {
                		lgw.loginfail();
                	}
                } else if(line.startsWith("SUBMIT")) {
                    lgw.login(out);
                } else if (line.startsWith("NAMEACCEPTED")) { //���� ������� ������ �޾ƿ�
                	frame.setVisible(true);
                	lines =  line.substring(13).split(",/");
                	this.id = lines[0];
                	this.name = lines[1];
                	this.info =  lines[2];
                    this.frame.setTitle("Chatter - " + this.name);
                } else if(line.startsWith("FRIENDLIST")) {
                	lines = line.split(",/");
                	friend.add(new Friend(lines[1], lines[2], lines[3], lines[4], lines[5], lines[6], lines[7], lines[8], lines[9]));
                } else if(line.startsWith("SET")) {
                	EditMainPage(frame, leftPanel, rightPanel, chatPanel, out);
                } else if(line.startsWith("FRIEND_ADD")) {
                	lines = line.split(",/");
                	friend.add(new Friend(lines[1], lines[2], lines[3], lines[4], lines[5], lines[6], lines[7], lines[8], lines[9]));
                	Editfriend(frame, friendPanel, friend.size()-1, out );
                	this.friendPanel.validate();
                	this.friendPanel.repaint();          	
                } else if(line.startsWith("search_value")) {
                	this.search_friend.clear();
                	String[] search_value;
                	lines = line.split(":/");
                	for(int i = 1; i < lines.length;i++) {
                		search_value = lines[i].split(",/");
                		search_friend.add(new Friend(search_value[0], search_value[1], search_value[2], search_value[3], 
                				search_value[4], search_value[5], search_value[6], search_value[7], search_value[8]  ));                				
                	}
                	this.friendPanel.setVisible(false);
                	EditSearch_Panel(frame, rightPanel, out);
                	
                	this.rightPanel.validate();
                	this.rightPanel.repaint();
                	
                } else if (line.startsWith("Change name")) {
                	this.name = line.substring(12);
                	this.frame.setTitle("Chatter - " + this.name);
                } else if (line.startsWith("Change info")) {
                	this.info = line.substring(12);
                } else if (line.startsWith("chat_start")) {
                	System.out.println(line.substring(11));
                	if(window == null) {
                    	window = new NewWindow(this.id, line.substring(11));
                	}
                } else if(line.startsWith("Message")) {
                	String[] inputs = line.split(",/");
                	window.changeMessage(inputs);
                } else if(line.startsWith("WindowQuit")) {
                	window.quit();
                	window = null;
                } else if (line.startsWith("group_chat_start")) {
                	String[] inputs = line.split(",/");
                	if(groupwindow == null) {
                		groupwindow = new NewGroupWindow(this.id, inputs);
                	}
                } else if(line.startsWith("Group_Message")) {
                	String[] inputs = line.split(",/");
                	groupwindow.changeMessage(inputs);
                } else if(line.startsWith("ACCOUNT_CANCELED")) {
     			   	JOptionPane.showMessageDialog(null, "ȸ��Ż��Ǿ����ϴ�.");
                } else if(line.startsWith("DUPLICATED_ID")) {
                	JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ���̵��Դϴ�.");
                } else if(line.startsWith("CREATE_SUCCESS")) {
                	JOptionPane.showMessageDialog(null, "ȸ�������� �����մϴ�!!!");
            }
            }
        } catch (java.net.ConnectException e){
        	//������ ������ �ȵ��� ��� ������ �ȵƴٴ� �޼����� ��� 
        	System.out.println("Can't Connect Server");
        } catch (Exception e) {
        	//Error Message���
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        } finally {
        	//GUIâ�� ������
            frame.setVisible(false);
            frame.dispose();
            System.out.println("Chat Finish");
        }
    }
    
    class NewWindow extends JFrame {
        JTextField textField = new JTextField(50);
        JTextArea messageArea = new JTextArea(16, 50);
        NewWindow(String sndId, String rcvId) {
        	
            setTitle(rcvId);
            
            textField.setEditable(true);
            messageArea.setEditable(false);
            getContentPane().add(textField, BorderLayout.SOUTH);
            getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
            pack();
            setSize(300, 400);
            
            setVisible(true);
            textField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    out.println("Message,/"+sndId+",/"+rcvId+",/"+textField.getText());
                    textField.setText("");
                }
            });
        }
        private void changeMessage(String[] message){
        	messageArea.append(message[1]+": "+message[3]+"\n");
        }
        private void quit() {
        	setVisible(false);
        }
    }
    
    class NewGroupWindow extends JFrame {
        JTextField textField = new JTextField(50);
        JTextArea messageArea = new JTextArea(16, 50);
    	String title = "Group_Chat";
        NewGroupWindow(String sndId, String[] list) {
        	for(int i = 1; i < list.length;i++) {
        		title = title +","+ list[i]; 
        	}
            setTitle(title);
            System.out.println(title);
            title = title.replace(",", ",/");
            title = title+",/" + sndId;

            System.out.println(title);
            
            textField.setEditable(true);
            messageArea.setEditable(false);
            getContentPane().add(textField, BorderLayout.SOUTH);
            getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
            pack();
            setSize(300, 400);
            
            setVisible(true);
            textField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    out.println(title+",/"+textField.getText());
                    textField.setText("");
                }
            });
        }
        private void changeMessage(String[] message){
        	messageArea.append(message[message.length-2]+": "+message[message.length - 1]+"\n");
        }
        private void quit() {
        	setVisible(false);
        }
    }
    
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Main client = new Main();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setSize(400, 600);
        System.out.println("Chat Start...");
        client.run();
		
		
	}
}
