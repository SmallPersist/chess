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
			
			// ��ȡ���Կͷ��˵���Ϣ
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String message = br.readLine();
			
			// ����ȥ����Ϣ
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
			
			// ��sendBack��������һ����
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			
			bw.write(sendBack + "\n");
			bw.flush();
			
			s.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		
	}

}
