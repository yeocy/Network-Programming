package Chats;
import javax.swing.*;

import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginWindows {
	String ID=null;
	String PW=null;
	JFrame f;

	public void sendLoginInfo(String ID, String PW) {
     	this.ID=ID;
     	this.PW=PW;
     }
	
	public void loginsuccess(){
		JOptionPane.showMessageDialog(null, "�α��ο� �����Ͽ����ϴ�");        			
		f.dispose();
	}	
	public void loginfail(){
		JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� Ȯ���ϼ���");     
	}
	
    public void login(PrintWriter out) {
    	
    	f=new JFrame("�α��� â");          // ������ ����
		  	
        JLabel l1=new JLabel("ID: ");             // ���̵� �� 
        l1.setBounds(20,20, 120,30);
        JTextField text = new JTextField();          // ���̵� �Է�â 
        text.setBounds(100,20, 240,30);
        JLabel l2=new JLabel("PW: ");            // ��й�ȣ ��
        l2.setBounds(20,75, 60,30);    
        TextField value = new TextField(); // ��й�ȣ �Է�â
        value.setEchoChar('*');
        value.setBounds(100,75, 240,30);
        JButton b = new JButton("�α���");         // �α��� ��ư
        b.setBounds(20,120, 150,30);
        JButton c = new JButton("ȸ������");         // ȸ������ ��ư
        c.setBounds(190,120, 150,30);

        f.add(l1); f.add(text);    //�����ӿ� ����� ��, �Է�â ���̱�             
        f.add(l2); f.add(value); 
        f.add(b); f.add(c); // �����ӿ� ��й�ȣ ��, �Է�â, ��ư ���̱�
        f.setSize(380, 220);
        f.setLayout(null);
        f.setVisible(true);
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       

        b.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {        
        		sendLoginInfo(text.getText(),value.getText()); 
        		out.println("send_ID_PW "+ ID + " " + PW);
        		text.setText("");
        		value.setText("");
        	}
        });
             
        c.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFrame r = new JFrame("ȸ������â");
      		  	
        		JPanel p = new JPanel();
        		Label l1 = new Label("���̵�");
        		Label l2 = new Label("��й�ȣ");
        		Label l3 = new Label("�̸�");
        		Label l4 = new Label("��Ī");
        		Label l5 = new Label("�̸��� �ּ�");
        		Label l6 = new Label("�������");
        		Label l7 = new Label("��ȭ��ȣ");
        		Label l8 = new Label("���¸޼���");
        		
        		JTextField t1 = new JTextField();
        		TextField t2 = new TextField();
        		JTextField t3 = new JTextField();
        		JTextField t4 = new JTextField();
        		JTextField t5 = new JTextField();
        		JTextField t6 = new JTextField();
        		JTextField t7 = new JTextField();
        		JTextField t8 = new JTextField();
        		
        		t2.setEchoChar('*');
          
        		JButton j1 = new JButton("����");
        		JButton j2 = new JButton("���");
          
        		l1.setBounds(40,10,40,40); l2.setBounds(40,50,60,40); l3.setBounds(40,90,60,40); 
        		l4.setBounds(40,130,40,40); l5.setBounds(40,170,40,40); l6.setBounds(40,210,60,40); l7.setBounds(40,250,60,40); l8.setBounds(40,290,60,40);
        		t1.setBounds(120,10,200,30); t2.setBounds(120,50,200,30); t3.setBounds(120,90,200,30);
        		t4.setBounds(120,130,200,30); t5.setBounds(120,170,200,30); t6.setBounds(120,210,200,30); t7.setBounds(120,250,200,30); t8.setBounds(120,290,200,30);
        		j1.setBounds(125,330,80,30); j2.setBounds(240,330,80,30);
          
        		r.add(l1); r.add(l2); r.add(l3); r.add(l4); r.add(l5); r.add(l6); r.add(l7); r.add(l8);
        		r.add(t1); r.add(t2); r.add(t3); r.add(t4); r.add(t5); r.add(t6); r.add(t7); r.add(t8);
        		r.add(j1); r.add(j2); r.add(p);
       
        		r.setSize(450,430);
        		r.setLayout(null);
        		r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		r.setVisible(true);
          
        		j1.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent T) {
        				try {
        					out.println("Sign_Up,/"+t1.getText()+",/"+t4.getText()+",/"+t3.getText()+",/"+t6.getText()+",/"+t5.getText()+",/"+
        							t7.getText()+",/"+t8.getText()+",/"+ t2.getText());
        					r.dispose();
        				} catch (Exception ex) {
        					JOptionPane.showMessageDialog(null,"ȸ�����Կ� �����Ͽ����ϴ�.");
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
    }
    
}