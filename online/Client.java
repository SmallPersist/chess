package merge2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/*
 * 一个socket建立了之后就可以一直接发数据吗？
 */
		
public class Client {
	public static void main(String[] args) {
		try {
			
			Socket s = new Socket("127.0.0.1", 8000);
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			
			// 向服务器发送一条数据，一定要加换行啊，不然服务器一直等待一个换行，就阻塞了
			bw.write("test net info from client...., please response...\n");
			bw.flush();
			
			// 读取服务器返回的数据
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
