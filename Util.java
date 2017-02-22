package com;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Util {
	
	
	public static int[] getPosition(int x, int y) {
		// board size��558, 620��һ��57
		int row = x / 57, col = y / 57;
		return new int[]{row, col};
	}
	
	public static int[] getCordinate(int row, int col) {
		int X = 24 + row * 57, Y = 56 + col * 57;
		return new int[]{X, Y};
	}
	
	
	// ������������֮�仹�м�������
	public static int countBetween(int[] curPos, int[] targetPos, JLabel[] play) {
		
		int cnt = 0;
		for(int i=0; i<32; i++) {
			int[] everyPos = Util.getPosition(play[i].getX(), play[i].getY());
			if(curPos[0] == everyPos[0] && curPos[1] == everyPos[1])		continue;
			if(targetPos[0] == everyPos[0] && targetPos[1] == everyPos[1])	continue;
			
			if(curPos[0] == targetPos[0] && curPos[0] == everyPos[0] && 
					(everyPos[1]-curPos[1])*(everyPos[1]-targetPos[1])<0)	
						cnt++;
			else if(curPos[1] == targetPos[1] && curPos[1] == everyPos[1] && 
					(everyPos[0]-curPos[0])*(everyPos[0]-targetPos[0])<0)	
						cnt++;
		}
		
		return cnt;
	}
	
	
	public static void newGame(JLabel[] play) {
		int i,k;
		
		// ��
		for (i=0,k=24;i<2;i++,k+=456){		
			play[i].setBounds(k,56,55,55);	
		}	
		
		//��
		for (i=4,k=81;i<6;i++,k+=342){	
			play[i].setBounds(k,56,55,55);
		}
		
		//��
		for (i=8,k=138;i<10;i++,k+=228){	
			play[i].setBounds(k,56,55,55);
		}
		
		//ʿ
		for (i=12,k=195;i<14;i++,k+=114){
			play[i].setBounds(k,56,55,55);
		}
		
		//��
		for (i=16,k=24;i<21;i++,k+=114){
			play[i].setBounds(k,227,55,55);
		}
		
		//��
		for (i=26,k=81;i<28;i++,k+=342){
			play[i].setBounds(k,170,55,55);
		}
		
		//��
		play[30].setBounds(252,56,55,55);

		//��ɫ����
		//��
		for (i=2,k=24;i<4;i++,k+=456){
			play[i].setBounds(k,569,55,55);
		}
		
		//��
		for (i=6,k=81;i<8;i++,k+=342){
			play[i].setBounds(k,569,55,55);
		}
		
		//��
		for (i=10,k=138;i<12;i++,k+=228){
			play[i].setBounds(k,569,55,55);
		}
		
		//ʿ
		for (i=14,k=195;i<16;i++,k+=114){
			play[i].setBounds(k,569,55,55);
		}
		
		//��
		for (i=21,k=24;i<26;i++,k+=114){
			play[i].setBounds(k,398,55,55);
		}
		
		//��
		for (i=28,k=81;i<30;i++,k+=342){
			play[i].setBounds(k,455,55,55);
		}
		
		//˧
		play[31].setBounds(252,569,55,55);		
		
		for (i=0;i<32;i++){
			play[i].setVisible(true);
		}
	}
	
	
	/**
	** ������ӷ���
	*/
	public static void drawChessMan(JLabel[] play) {
		//���̿���
		int i,k;
		//ͼ��
		Icon in;
				
		//��ɫ����
		
		//��(���ɻ�׼��24,56)
		in = new ImageIcon("src\\image\\�ڳ�.gif");
		for (i=0,k=24;i<2;i++,k+=456){		
			play[i] = new JLabel(in);
			play[i].setBounds(k,56,55,55);	
			play[i].setName("��1");		
		}	
		
		//��
		in = new ImageIcon("src\\image\\����.gif");
		for (i=4,k=81;i<6;i++,k+=342){	
			play[i] = new JLabel(in);
			play[i].setBounds(k,56,55,55);
			play[i].setName("��1");
		}
		
		//��
		in = new ImageIcon("src\\image\\����.gif");
		for (i=8,k=138;i<10;i++,k+=228){	
			play[i] = new JLabel(in);
			play[i].setBounds(k,56,55,55);
			play[i].setName("��1");
		}
		
		//ʿ
		in = new ImageIcon("src\\image\\��ʿ.gif");
		for (i=12,k=195;i<14;i++,k+=114){
			play[i] = new JLabel(in);
			play[i].setBounds(k,56,55,55);
			play[i].setName("ʿ1");
		}
		
		//��
		in = new ImageIcon("src\\image\\����.gif");
		for (i=16,k=24;i<21;i++,k+=114){
			play[i] = new JLabel(in);
			play[i].setBounds(k,227,55,55);
			play[i].setName("��1" + i);
		}
		
		//��
		in = new ImageIcon("src\\image\\����.gif");			
		for (i=26,k=81;i<28;i++,k+=342){
			play[i] = new JLabel(in);
			play[i].setBounds(k,170,55,55);
			play[i].setName("��1" + i);
		}
		
		//��
		in = new ImageIcon("src\\image\\�ڽ�.gif");
		play[30] = new JLabel(in);
		play[30].setBounds(252,56,55,55);
		play[30].setName("��1");

		//��ɫ����
		//��
		in = new ImageIcon("src\\image\\�쳵.gif");
		for (i=2,k=24;i<4;i++,k+=456){
			play[i] = new JLabel(in);
			play[i].setBounds(k,569,55,55);
			play[i].setName("��2");
		}
		
		//��
		in = new ImageIcon("src\\image\\����.gif");
		for (i=6,k=81;i<8;i++,k+=342){
			play[i] = new JLabel(in);
			play[i].setBounds(k,569,55,55);
			play[i].setName("��2");
		}
		
		//��
		in = new ImageIcon("src\\image\\����.gif");			
		for (i=10,k=138;i<12;i++,k+=228){
			play[i] = new JLabel(in);
			play[i].setBounds(k,569,55,55);
			play[i].setName("��2");
		}
		
		//ʿ
		in = new ImageIcon("src\\image\\��ʿ.gif");
		for (i=14,k=195;i<16;i++,k+=114){
			play[i] = new JLabel(in);
			play[i].setBounds(k,569,55,55);
			play[i].setName("ʿ2");
		}
		
		//��
		in = new ImageIcon("src\\image\\����.gif");
		for (i=21,k=24;i<26;i++,k+=114){
			play[i] = new JLabel(in);
			play[i].setBounds(k,398,55,55);
			play[i].setName("��2" + i);
		}
		
		//��
		in = new ImageIcon("src\\image\\����.gif");
		for (i=28,k=81;i<30;i++,k+=342){
			play[i] = new JLabel(in);
			play[i].setBounds(k,455,55,55);
			play[i].setName("��2" + i);
		}
		
		//˧
		in = new ImageIcon("src\\image\\�콫.gif");			
		play[31] = new JLabel(in);
		play[31].setBounds(252,569,55,55);		
		play[31].setName("˧2");
	}
}
