package merge2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/*
 * һ��socket������֮��Ϳ���һֱ�ӷ�������
 */
		
public class Client {
	public static void main(String[] args) {
		try {
			
			Socket s = new Socket("127.0.0.1", 8000);
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			
			// �����������һ�����ݣ�һ��Ҫ�ӻ��а�����Ȼ������һֱ�ȴ�һ�����У���������
			bw.write("test net info from client...., please response...\n");
			bw.flush();
			
			// ��ȡ���������ص�����
			String msg = br.readLine();
			if("OK".equals(msg)) {
				System.out.println("Accept...");
			} else {
				System.out.println("Reject...");
			}
			
			s.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
