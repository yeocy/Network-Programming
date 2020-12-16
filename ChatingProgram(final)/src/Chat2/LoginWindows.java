package Chat;
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
	JFrame r;

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
	public void signInSuccess() {
		JOptionPane.showMessageDialog(null, "ȸ�������� �����մϴ�!!!");
		r.dispose();
	}
	public void findIdSuccess(String ID){
		JOptionPane.showMessageDialog(null, "���̵�� "+ID+" �Դϴ�.");
		r.dispose();
	}
	public void findPwSuccess(String PW){
		JOptionPane.showMessageDialog(null, "��й�ȣ�� "+PW+" �Դϴ�.");     
		r.dispose();
	}
	public void fail() {
		JOptionPane.showMessageDialog(null, "�Է°��� Ȯ���ϼ���");
	}
	
    public void login(PrintWriter out) {
    	
    	f=new JFrame("LOGIN"); // ������ ����
		  	
    	JButton b = new JButton("�α���"); 
    	JButton c = new JButton("ȸ������");
    	JButton d = new JButton("���̵� ã��");
    	JButton e = new JButton("��й�ȣ ã��");
    	
        JLabel l1=new JLabel("ID: "); // ���̵� �� 
        JLabel l2=new JLabel("PW: "); // ��й�ȣ ��
        
        JTextField text = new JTextField(); // ���̵� �Է�â        
        TextField value = new TextField(); // ��й�ȣ �Է�â        
                
        b.setBounds(20, 130, 320, 40);
        c.setBounds(20, 180, 150, 30);
        d.setBounds(190, 180, 150, 15);
        e.setBounds(190, 195, 150, 15);
        
        l1.setBounds(45, 20, 30, 40);
        l2.setBounds(45, 75, 30, 40); 
        
        text.setBounds(100, 20, 240, 40);
        value.setBounds(100, 75, 240, 40);
        value.setEchoChar('*');       

        f.add(b); f.add(c); f.add(d); f.add(e); // �����ӿ� ��й�ȣ ��, �Է�â, ��ư ���̱�
        f.add(l1); f.add(l2);
        f.add(text); f.add(value); //�����ӿ� ����� ��, �Է�â ���̱�             
        
        f.setSize(380, 270);
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
        		r = new JFrame("ȸ������");
      		  	
        		JPanel p = new JPanel();
        		Label l1 = new Label("���̵�:");
        		Label l2 = new Label("��й�ȣ:");
        		Label l3 = new Label("�̸�:");
        		Label l4 = new Label("��Ī:");
        		Label l5 = new Label("�̸��� �ּ�:");
        		Label l6 = new Label("�������:");
        		Label l7 = new Label("��ȭ��ȣ:");
        		Label l8 = new Label("���¸޼���:");
        		
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
          
        		l1.setBounds(40,20,40,40); l2.setBounds(40,60,60,40); l3.setBounds(40,100,60,40); l4.setBounds(40,140,40,40);
        		l5.setBounds(40,180,40,40); l6.setBounds(40,220,60,40); l7.setBounds(40,260,60,40); l8.setBounds(40,300,60,40);
        		
        		t1.setBounds(120,20,230,30); t2.setBounds(120,60,230,30); t3.setBounds(120,100,230,30); t4.setBounds(120,140,230,30);
        		t5.setBounds(120,180,230,30); t6.setBounds(120,220,230,30); t7.setBounds(120,260,230,30); t8.setBounds(120,300,230,30);
        		
        		j1.setBounds(120,340,105,30); j2.setBounds(245,340,105,30);
          
        		r.add(l1); r.add(l2); r.add(l3); r.add(l4); r.add(l5); r.add(l6); r.add(l7); r.add(l8);
        		r.add(t1); r.add(t2); r.add(t3); r.add(t4); r.add(t5); r.add(t6); r.add(t7); r.add(t8);
        		r.add(j1); r.add(j2); r.add(p);
       
        		r.setSize(420,430);
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
        d.addActionListener(new ActionListener() { //���̵�ã��
        	public void actionPerformed(ActionEvent e) { 
        		r = new JFrame("���̵� ã��");
      		  	
        		JPanel p = new JPanel();
        		Label l1 = new Label("�������:");
        		Label l2 = new Label("�̸���:");
        		Label l3 = new Label("��ȭ��ȣ:");
        		
        		JTextField t1 = new JTextField();
        		JTextField t2 = new JTextField();
        		JTextField t3 = new JTextField();
        		
        		JButton j1 = new JButton("ã��");
        		JButton j2 = new JButton("���");
        		
        		l1.setBounds(40,20,60,40); l2.setBounds(40,60,60,40); l3.setBounds(40,100,60,40);
        		t1.setBounds(120,20,230,30); t2.setBounds(120,60,230,30); t3.setBounds(120,100,230,30);
        		j1.setBounds(120,140,105,30); j2.setBounds(245,140,105,30);
        		
        		r.add(l1); r.add(l2); r.add(l3);
        		r.add(t1); r.add(t2); r.add(t3);
        		r.add(j1); r.add(j2); r.add(p);
        		
        		r.setSize(420,230);
        		r.setLayout(null);
        		r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		r.setVisible(true);
        		
        		j1.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent T) {
        				try {
        					out.println("FIND_ID_REQUEST,/"+t1.getText()+",/"+t2.getText()+",/"+t3.getText());    					
        				} catch (Exception ex) {
        					JOptionPane.showMessageDialog(null,"�Է°��� Ȯ���ϼ���.");
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
        e.addActionListener(new ActionListener() { //��й�ȣã��
        	public void actionPerformed(ActionEvent e) { 
        		r = new JFrame("��й�ȣ ã��");
      		  	
        		JPanel p = new JPanel();
        		Label l1 = new Label("���̵�:");
        		Label l2 = new Label("�̸���:");
        		Label l3 = new Label("��ȭ��ȣ:");
        		
        		JTextField t1 = new JTextField();
        		JTextField t2 = new JTextField();
        		JTextField t3 = new JTextField();
        		
        		JButton j1 = new JButton("ã��");
        		JButton j2 = new JButton("���");
        		
        		l1.setBounds(40,20,60,40); l2.setBounds(40,60,60,40); l3.setBounds(40,100,60,40);
        		t1.setBounds(120,20,230,30); t2.setBounds(120,60,230,30); t3.setBounds(120,100,230,30);
        		j1.setBounds(120,140,105,30); j2.setBounds(245,140,105,30);
        		
        		r.add(l1); r.add(l2); r.add(l3);
        		r.add(t1); r.add(t2); r.add(t3);
        		r.add(j1); r.add(j2); r.add(p);
        		
        		r.setSize(420,230);
        		r.setLayout(null);
        		r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		r.setVisible(true);
        		
        		j1.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent T) {
        				try {
        					out.println("FIND_PW_REQUEST,/"+t1.getText()+",/"+t2.getText()+",/"+t3.getText());      					
        				} catch (Exception ex) {
        					JOptionPane.showMessageDialog(null,"�Է°��� Ȯ���ϼ���.");
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