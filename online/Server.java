package merge2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		
		ServerSocket server = null;
		
		// 从client返回的数据
		String anotherMoveInfo = null;
		// 是否已经把这个数据发给了另外一个人？
//		boolean fetched = false;
		
		try {
			
			server = new  ServerSocket(8000);
			System.out.println("server has start up, waiting for client...");
			
			// 
			
			
			while(true) {
				Socket s = server.accept();
				
				// 通过Thread中定义类变量实现线程计算参数的返回
				HandlerThread ht = new HandlerThread(s, anotherMoveInfo);
				ht.start();
				ht.join();
				
				if(anotherMoveInfo != null && ht.fetched) {
					anotherMoveInfo = null;
				} else if(ht.anotherMoveInfo != null) {
					anotherMoveInfo = ht.anotherMoveInfo;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(server != null)
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
