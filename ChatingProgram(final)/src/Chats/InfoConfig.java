package Chats;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InfoConfig { //serverinfo.txt���Ϸκ��� Port number�� IP�ּҸ� ���� ������ �ϴ� class.
    String Port;
    String Ip;
    public void getInfo() {
        try{
            File f = new File("serverinfo.txt");
            Scanner sc = new Scanner(f);
            
            Ip = sc.nextLine().split(":")[1]; //Scanner�� ���� ù ��° ���� ":"�� �������� ���� ��, �� ��° ��Ҹ� ���ϸ� IP�ּҸ� ���� �� �ִ�.
            Port = sc.nextLine().split(":")[1]; //Client�� SocketŬ������ �޴� Port number���ڴ� �������̹Ƿ� ����ȯ�� �����ش�.
            if(sc!=null) sc.close(); 

        } catch (FileNotFoundException e){ //FileNotFoundException �߻� �� default ���� �Ҵ��Ѵ�
            Ip = "localhost";
            Port = "3306";
        }
    }
}
