package merge2;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;


class RedClient extends JFrame implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	public static int server_port = 8000;
	
	JLabel play[] = new JLabel[32];
	JLabel image;	
	Container con;
	JToolBar jmain;	
	JButton anew;
	JButton repent;
	JButton exit;
	JLabel text;
	
	static int Man, i;
	
	// ˭����
	int chessPlayClick=2;
	
	// ����
	Rule rule = new Rule();
	
	
	// ��һ���ǲ����߶��ˣ�
	int[] movePass = null;
	boolean eatPass = false;
	
	// ���ص������ƶ���Ϣ����׼����(����˰��չ����������)��
	// ������ƶ���move,i,coordinateX,coordinateY
	// ����ǳ��ӣ�eat,i,j
	// ����ǲ�ѯ:query
	// �޸�Rule�ķ���ֵ��int[]�������pass�ͷ���null��pass�ͷ������ӵ����꣬stepPass = (return null)
	String stepInfo = "";
	
	// ��û��ѡ������
	boolean chessManClick = false;
	
	@SuppressWarnings("deprecation")
	public RedClient(String title) throws Exception {
		con = this.getContentPane();
		con.setLayout(null);
		
		jmain = new JToolBar();
		text = new JLabel("��ӭʹ���������ϵͳ");
		//����������ʾ��Ϣ
		text.setToolTipText("��Ϣ��ʾ");
		anew = new JButton(" �� �� Ϸ ");
		anew.setToolTipText("���¿�ʼ�µ�һ��");
		exit = new JButton(" ��  �� ");
		exit.setToolTipText("�˳�����������");
		repent = new JButton(" ��  �� ");
		repent.setToolTipText("���ص��ϴ������λ��");
		// �������ӵ�������
		jmain.setLayout(new GridLayout(0, 4));
		jmain.add(anew);
		jmain.add(repent);
		jmain.add(exit);
		jmain.add(text);
		jmain.setBounds(0, 0, 558, 30);
		con.add(jmain);
		//ע�ᰴŤ����
		anew.addActionListener(this);
		repent.addActionListener(this);
		exit.addActionListener(this);	
		
		//������ӱ�ǩ
		Util.drawChessMan(play);
		//ע�������ƶ�����
		for (int i=0;i<32;i++){
			con.add(play[i]);
			play[i].addMouseListener(this);
		}
		
		// �������
		con.add(image = new JLabel(new ImageIcon("src\\image\\main.gif")));
		image.setBounds(0, 30, 558, 620);
		image.setName("board");
		image.addMouseListener(this);
		
		//ע�ᴰ��رռ���
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// ����
		this.setResizable(false);
		this.setTitle(title);
		this.setSize(558,670);
		this.setVisible(true);
		this.show();
		
		gameStart();
	}
	
	
	private void gameStart() throws Exception {
		
		while(true) {
			// ��Ϸ��ʼ�ˣ���һֱ��ѭ��
			
			while(movePass == null && !eatPass) {
				
				// ����һֱ��ѭ�������ó�CPU���������¼�
				Thread.sleep(200);
			}
			
			movePass = null;
			eatPass = false;
			
			System.out.println("sending info to black...:" + stepInfo);
			// �ⲽpass��֮�󣬰��ƶ�����ʲô����server
			Socket s = new Socket("127.0.0.1", 8000);
			OutputStream os = s.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			
			bw.write(stepInfo + "\n");
			bw.flush();
			stepInfo = null;
			s.close();
			
			// �Ⱥڷ��Ȱ�����query��
			Thread.sleep(300);
			
			// �������󣺶Է���û������
			boolean flag = false;
			while(!flag) {
				Socket ss = new Socket("127.0.0.1", 8000);
				OutputStream oss = ss.getOutputStream();
				InputStream iss = ss.getInputStream();
				BufferedWriter bws = new BufferedWriter(new OutputStreamWriter(oss));
				BufferedReader brs = new BufferedReader(new InputStreamReader(iss));
				bws.write("query\n");
				bws.flush();
				
				// ����һ�������������
				String msg = brs.readLine();
				if(!"no".equals(msg)) {
					flag = true;
					update(msg);
				}
				
				ss.close();
				Thread.sleep(100);
			}
			
			
			
			// ����������������ݣ�˵���ü����ˣ���break
//			if(gameOver)	break;
		}
	}
	
	
	public void update(String msg) {
		String[] ss = msg.split(",");
		if(ss[0].equals("move")) {
			int i  = Integer.valueOf(ss[1]);
			int coordX = Integer.valueOf(ss[2]);
			int coordY = Integer.valueOf(ss[3]);
			play[i].setBounds(coordX, coordY, 55, 55);
		} else {
			int i  = Integer.valueOf(ss[1]);
			int j  = Integer.valueOf(ss[2]);
			play[i].setBounds(play[j].getBounds());
			play[j].setVisible(false);
		}
	}


	
	// ��ťע��ķ���
	@Override
	public void actionPerformed(ActionEvent e) {
		//���¿�ʼ��ť
		if(e.getSource().equals(anew)) {
			System.out.println("a new game...");
			Util.newGame(play);
			chessPlayClick = 2;
		}
	}

	// �������¼�
	@Override
	public void mouseClicked(MouseEvent e) {
		
		// �����������̣��������ӣ�ֻ����һ��
		if(e.getSource().equals(image)) {
			
			// �ú���
			if(chessPlayClick == 2 && play[Man].getName().charAt(1) == '2') {
				
				if(Man > 15 && Man < 26) {
					// �ƶ���
					movePass = rule.armsRule(Man, play[Man], e);
				} else if(Man > 25 && Man < 30) {
					// �ƶ���
					movePass = rule.cannonRule(play[Man], play, e);
				} else if(Man >= 0 && Man < 4) {
					// �ƶ���
					movePass = rule.cannonRule(play[Man], play, e);
				} else if(Man > 3 && Man < 8) {
					// �ƶ���
					movePass = rule.horseRule(play[Man], play, e);
				} else if(Man > 7 && Man < 12) {
					// �ƶ���
					movePass = rule.elephantRule(0, play[Man], play, e);
				} else if(Man > 11 && Man < 16) {
					// �ƶ�ʿ
					movePass = rule.chapRule(0, play[Man], e, play);
				} else if(Man == 30 || Man == 31) {
					// �ƶ�˧
					movePass = rule.willRule(0, play[Man], e, play);
				} 
				
				if (movePass != null)	chessPlayClick = 1;
			}
			
			//�ú��� 
			else if(chessPlayClick == 1 && play[Man].getName().charAt(1) == '1') {
				
				if(Man > 15 && Man < 26) {
					// �ƶ���
					movePass = rule.armsRule(Man, play[Man], e);
				} else if(Man > 25 && Man < 30) {
					// �ƶ���
					movePass = rule.cannonRule(play[Man], play, e);
				} else if(Man >= 0 && Man < 4) {
					// �ƶ���
					movePass = rule.cannonRule(play[Man], play, e);
				} else if(Man > 3 && Man < 8) {
					// �ƶ���
					movePass = rule.horseRule(play[Man], play, e);
				} else if(Man > 7 && Man < 12) {
					// �ƶ���
					movePass = rule.elephantRule(1, play[Man], play, e);
				} else if(Man > 11 && Man < 16) {
					// �ƶ�ʿ
					movePass = rule.chapRule(1, play[Man], e, play);
				} else if(Man == 30 || Man == 31) {
					// �ƶ�˧
					movePass = rule.willRule(1, play[Man], e, play);
				}

				if (movePass != null)	chessPlayClick = 2;
			}
			
			if(movePass != null)		stepInfo = "move," + Man + "," + movePass[0] + "," + movePass[1];
		}
		
		// �����������ӣ�������ѡ�У�Ҳ�����ǳ���
		else {
			//��һ��ѡ������(��˸����)��ȷ�����������ĸ�
			if(!chessManClick) {
				for(int i=0; i<32; i++) {
					if(e.getSource().equals(play[i])) {
						Man = i;
						chessManClick = true;
						break;
					}
				}
			}
			
			//����
			else if(chessManClick) {
				chessManClick = false;
				
				// �ҵ����Ե�������
				for(int i=0; i<32; i++) {
					if(e.getSource().equals(play[i])) {
						// �������
						if(chessPlayClick == 2 && play[Man].getName().charAt(1) == '2') {
							if(Man > 15 && Man < 26) {
								eatPass = rule.armsRuleEat(Man, play[Man], play[i]);
							} else if(Man > 25 && Man < 30) {
								eatPass = rule.cannonRuleEat(play[Man], play[i], play);
							} else if (Man >=0 && Man < 4){
								eatPass = rule.cartRuleEat(play[Man], play[i], play);
							} else if (Man > 3 && Man < 8){
								eatPass = rule.horseRuleEat(play[Man], play[i], play);	
							} else if (Man > 7 && Man < 12){
								eatPass = rule.elephantRuleEat(0, play[Man], play[i], play);
							} else if (Man > 11 && Man < 16){
								eatPass = rule.chapRuleEat(0, play[Man], play[i], play);
							} else if (Man == 30 || Man == 31){
								eatPass = rule.willRuleEat(0, play[Man], play[i], play);	
							}
							
							if(eatPass)	chessPlayClick = 1;
						}
						
						// �ú���
						else {
							if(Man > 15 && Man < 26) {
								eatPass = rule.armsRuleEat(Man, play[Man], play[i]);
							} else if(Man > 25 && Man < 30) {
								eatPass = rule.cannonRuleEat(play[Man], play[i], play);
							} else if (Man >=0 && Man < 4){
								eatPass = rule.cartRuleEat(play[Man], play[i], play);
							} else if (Man > 3 && Man < 8){
								eatPass = rule.horseRuleEat(play[Man], play[i], play);	
							} else if (Man > 7 && Man < 12){
								eatPass = rule.elephantRuleEat(0, play[Man], play[i], play);
							} else if (Man > 11 && Man < 16){
								eatPass = rule.chapRuleEat(0, play[Man], play[i], play);
							} else if (Man == 30 || Man == 31){
								eatPass = rule.willRuleEat(0, play[Man], play[i], play);	
							}
							
							if(eatPass)	chessPlayClick = 2;
						}
						
						if(eatPass)		stepInfo = "eat," + Man + "," + i;
					}
				}
			}
		}
		
		
		// ��Ϸ�Ƿ����
		if(!play[31].isVisible()) {
			JOptionPane.showConfirmDialog(this, "����win", "paly 1 win", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			// ������������
			chessPlayClick = 3;
		} else if(!play[30].isVisible()) {
			JOptionPane.showConfirmDialog(this, "����win", "paly 2 win", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			// ������������
			chessPlayClick = 3;
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

}