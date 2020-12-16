package Chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
public class whetherAPI {
	
	/**
	 * ������ ������������ ����
	 */
	public String getColorCode(){
		 
		String ans="";
		
		try {
			String serviceKey = "nmwGSJ4MGuujj9LZoDn%2FNy8lczMP%2BK%2BR%2BmdNIlrsd3ZSatkP%2Fc6SM6hwUWpq8z%2BTr1cemXnmAuOku6QRLxLwrg%3D%3D";
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyyMMdd");  
			DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HHmm");  
			LocalDateTime now = LocalDateTime.now();

			String dataType = "JSON";
			String xcoordinate = "62"; //"������"�� x��ǥ
			String ycoordinate = "124"; //"������"�� y��ǥ
			String base_date = dtf1.format(now); //��ǥ����
			String base_time = dtf2.format(now); //��ǥ�ð�
		
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"); //URL
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey); //Service Key
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); //�� ������ ��� ��
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); //��������ȣ
			urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); //��û�ڷ�����: JSON
			urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(base_date, "UTF8")); // 2020�� 11�� 20�� ��ǥ
			urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(base_time, "UTF8"));  // 17�� ��ǥ
			urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(xcoordinate, "UTF-8")); // ���������� X ��ǥ��
			urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ycoordinate, "UTF8")); // ���������� Y ��ǥ��
			
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
			conn.setRequestMethod("GET"); //HTTP��û �߻�
			conn.setRequestProperty("Content-type", "application/json");
			BufferedReader rd;
	    
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder(); 
			String line;
	    
			while ((line = rd.readLine()) != null) { //InputStream�� null�� �ƴ϶�� StringBuilder�� line�� �߰�
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			
			//parsing
			JSONParser parser = new JSONParser();
		
        	JSONObject weather_obj = (JSONObject)parser.parse(sb.toString());
        	
        	JSONObject response = (JSONObject)weather_obj.get("response");
        	JSONObject body = (JSONObject)response.get("body");
        	JSONObject items = (JSONObject)body.get("items");
        	JSONArray item = (JSONArray)items.get("item");
        	
        	for(int i=0;i<item.size();i++) {
        		JSONObject info=(JSONObject)item.get(i);
        		String category = (String)info.get("category");
        		String value = (String)info.get("fcstValue");
        		
        		if(category.equals("PTY")) {
        			if (value.equals("0")){ //�� x
        				ans =  "204,204,204";
        			} else if (value.equals("1")){ //��
        				ans = "126,134,138";        				
        			} else if (value.equals("2")){ //��+��
        				ans = "221,221,221";
        			} else if (value.equals("3")){ //��
        				ans = "255,255,255";
        			} else if (value.equals("4")){ //�ҳ���
        				ans = "51,153,255";
        			} else if (value.equals("5")){ //�����
        				ans = "0,102,255";
        			} else if (value.equals("6")){ //�����+������
        				ans = "0,204,255";
        			} else if (value.equals("7")){ //������
        				ans = "0,255,255";
        			}
        		}
        	}
        } catch (Exception e) {
        	ans =  "204,204,204"; //default color
        }
        return ans;
	}
}


