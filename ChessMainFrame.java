package com;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;


/*
 * ������ʾ�����Ӳ���ʾ��ȥ�����̣�������ʾ��˵����������ס�����ӣ�����һ�����˳��ͺ���
 */
class ChessMainFrame extends JFrame implements ActionListener, MouseListener, Runnable{
	private static final long serialVersionUID = 1L;
	
	//���
	JLabel play[] = new JLabel[32];
	//����
	JLabel image;	
	//����
	Container con;
	//������
	JToolBar jmain;	
	//���¿�ʼ
	JButton anew;
	//����
	JButton repent;
	//�˳�
	JButton exit;
	//��ǰ��Ϣ
	JLabel text;
	
	// ����������˸
	boolean chessManClick;
	static int Man, i;
	Thread tmain;
	
	// ˭����
	int chessPlayClick=2;
	
	// ����
	Rule rule = new Rule();
	
	
	@SuppressWarnings("deprecation")
	public ChessMainFrame(String title) {
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
		
		
		//�����ڵ�����Ļ����
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		
		if (frameSize.height > screenSize.height){
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width){
			frameSize.width = screenSize.width;
		}
		this.setLocation((screenSize.width - frameSize.width) / 2 - 280 ,
						(screenSize.height - frameSize.height ) / 2 - 350);
		
		// ����
		this.setResizable(false);
		this.setTitle(title);
		this.setSize(558,670);
		this.setVisible(true);
		this.show();
		
	}
	
	
	// ������˸���߳�
	@SuppressWarnings({ "static-access", "static-access" })
	@Override
	public void run() {
		while(true) {
			// �������ѡ����ĳ��������˸ѡ�е�
			if(chessManClick) {
				play[Man].setVisible(false);
				try {
					tmain.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				play[Man].setVisible(true);
			}
			
			try {
				tmain.sleep(350);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		// ����tmain�߳�
		if(tmain == null) {
			tmain = new Thread(this);
			tmain.start();
		}
		
		// ���ϲ����Ϲ���
		boolean passRule = false;
		
		// �����������̣��������ӣ�ֻ����һ��
		if(e.getSource().equals(image)) {
			
			// �ú���
			if(chessPlayClick == 2 && play[Man].getName().charAt(1) == '2') {
				
				if(Man > 15 && Man < 26) {
					// �ƶ���
					System.out.println("checking arms rule...");
					passRule = rule.armsRule(Man, play[Man], e);
				} else if(Man > 25 && Man < 30) {
					// �ƶ���
					System.out.println("check cannon rule...");
					passRule = rule.cannonRule(play[Man], play, e);
				} else if(Man >= 0 && Man < 4) {
					// �ƶ���
					System.out.println("check cannon rule...");
					passRule = rule.cannonRule(play[Man], play, e);
				} else if(Man > 3 && Man < 8) {
					// �ƶ���
					System.out.println("check horse rule...");
					passRule = rule.horseRule(play[Man], play, e);
				} else if(Man > 7 && Man < 12) {
					// �ƶ���
					System.out.println("check elephant rule...");
					passRule = rule.elephantRule(0, play[Man], play, e);
				} else if(Man > 11 && Man < 16) {
					// �ƶ�ʿ
					System.out.println("check chap rule...");
					passRule = rule.chapRule(0, play[Man], e, play);
				} else if(Man == 30 || Man == 31) {
					// �ƶ�˧
					System.out.println("check will rule...");
					passRule = rule.willRule(0, play[Man], e, play);
				} 
				
				if (passRule)	chessPlayClick = 1;
			}
			
			//�ú��� 
			else if(chessPlayClick == 1 && play[Man].getName().charAt(1) == '1') {
				
				if(Man > 15 && Man < 26) {
					// �ƶ���
					System.out.println("checking arms rule...");
					passRule = rule.armsRule(Man, play[Man], e);
				} else if(Man > 25 && Man < 30) {
					// �ƶ���
					System.out.println("check cannon rule...");
					passRule = rule.cannonRule(play[Man], play, e);
				} else if(Man >= 0 && Man < 4) {
					// �ƶ���
					System.out.println("check cannon rule...");
					passRule = rule.cannonRule(play[Man], play, e);
				} else if(Man > 3 && Man < 8) {
					// �ƶ���
					System.out.println("check horse rule...");
					passRule = rule.horseRule(play[Man], play, e);
				} else if(Man > 7 && Man < 12) {
					// �ƶ���
					System.out.println("check elephant rule...");
					passRule = rule.elephantRule(1, play[Man], play, e);
				} else if(Man > 11 && Man < 16) {
					// �ƶ�ʿ
					System.out.println("check chap rule...");
					passRule = rule.chapRule(1, play[Man], e, play);
				} else if(Man == 30 || Man == 31) {
					// �ƶ�˧
					System.out.println("check will rule...");
					passRule = rule.willRule(1, play[Man], e, play);
				}

				if (passRule)	chessPlayClick = 2;
			}
			
			// ������û���ߣ���Ϊ����һ�£�������˸��
			chessManClick = false;
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
								System.out.println("check arms eating....");
								passRule = rule.armsRuleEat(Man, play[Man], play[i]);
							} else if(Man > 25 && Man < 30) {
								System.out.println("check cannon eating...");
								passRule = rule.cannonRuleEat(play[Man], play[i], play);
							} else if (Man >=0 && Man < 4){
								System.out.println("check cart eating...");
								passRule = rule.cartRuleEat(play[Man], play[i], play);
							} else if (Man > 3 && Man < 8){
								passRule = rule.horseRuleEat(play[Man], play[i], play);	
							} else if (Man > 7 && Man < 12){
								passRule = rule.elephantRuleEat(0, play[Man], play[i], play);
							} else if (Man > 11 && Man < 16){
								passRule = rule.chapRuleEat(0, play[Man], play[i], play);
							} else if (Man == 30 || Man == 31){
								passRule = rule.willRuleEat(0, play[Man], play[i], play);	
							}
							
							if(passRule)	chessPlayClick = 1;
						}
						
						// �ú���
						else {
							if(Man > 15 && Man < 26) {
								System.out.println("check arms eating....");
								passRule = rule.armsRuleEat(Man, play[Man], play[i]);
							} else if(Man > 25 && Man < 30) {
								System.out.println("check cannon eating...");
								passRule = rule.cannonRuleEat(play[Man], play[i], play);
							} else if (Man >=0 && Man < 4){
								System.out.println("check cart eating...");
								passRule = rule.cartRuleEat(play[Man], play[i], play);
							} else if (Man > 3 && Man < 8){
								passRule = rule.horseRuleEat(play[Man], play[i], play);	
							} else if (Man > 7 && Man < 12){
								passRule = rule.elephantRuleEat(0, play[Man], play[i], play);
							} else if (Man > 11 && Man < 16){
								passRule = rule.chapRuleEat(0, play[Man], play[i], play);
							} else if (Man == 30 || Man == 31){
								passRule = rule.willRuleEat(0, play[Man], play[i], play);	
							}
							
							if(passRule)	chessPlayClick = 2;
						}
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