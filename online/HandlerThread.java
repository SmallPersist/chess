package merge2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HandlerThread extends Thread{
	
	private Socket s;
	public String anotherMoveInfo;
	public boolean fetched = false;

	public HandlerThread(Socket s, String anotherMoveInfo) {
		this.s = s;
		this.anotherMoveInfo = anotherMoveInfo;
	}

	@Override
	public void run() {
		
		try {
			
//			this.anotherMoveInfo = "kidding .............";
			
			// 读取来自客服端的信息
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String message = br.readLine();
			
			// 返回去的信息
			String sendBack = "no";
			if(message.equals("query")) {
				if(anotherMoveInfo != null)	{
					sendBack = anotherMoveInfo;
					this.fetched = true;
					this.anotherMoveInfo = null;
				}
			} else {
				this.anotherMoveInfo = message;
			}
			
			// 把sendBack发给另外一个人
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			
			bw.write(sendBack + "\n");
			bw.flush();
			
			s.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		
	}

}
