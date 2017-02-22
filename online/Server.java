package merge2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		
		ServerSocket server = null;
		
		// ��client���ص�����
		String anotherMoveInfo = null;
		// �Ƿ��Ѿ���������ݷ���������һ���ˣ�
//		boolean fetched = false;
		
		try {
			
			server = new  ServerSocket(8000);
			System.out.println("server has start up, waiting for client...");
			
			// 
			
			
			while(true) {
				Socket s = server.accept();
				
				// ͨ��Thread�ж��������ʵ���̼߳�������ķ���
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
