package com;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Util {
	
	
	public static int[] getPosition(int x, int y) {
		// board size：558, 620，一格57
		int row = x / 57, col = y / 57;
		return new int[]{row, col};
	}
	
	public static int[] getCordinate(int row, int col) {
		int X = 24 + row * 57, Y = 56 + col * 57;
		return new int[]{X, Y};
	}
	
	
	// 计算两个棋子之间还有几颗棋子
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
		
		// 车
		for (i=0,k=24;i<2;i++,k+=456){		
			play[i].setBounds(k,56,55,55);	
		}	
		
		//马
		for (i=4,k=81;i<6;i++,k+=342){	
			play[i].setBounds(k,56,55,55);
		}
		
		//相
		for (i=8,k=138;i<10;i++,k+=228){	
			play[i].setBounds(k,56,55,55);
		}
		
		//士
		for (i=12,k=195;i<14;i++,k+=114){
			play[i].setBounds(k,56,55,55);
		}
		
		//卒
		for (i=16,k=24;i<21;i++,k+=114){
			play[i].setBounds(k,227,55,55);
		}
		
		//炮
		for (i=26,k=81;i<28;i++,k+=342){
			play[i].setBounds(k,170,55,55);
		}
		
		//将
		play[30].setBounds(252,56,55,55);

		//红色棋子
		//车
		for (i=2,k=24;i<4;i++,k+=456){
			play[i].setBounds(k,569,55,55);
		}
		
		//马
		for (i=6,k=81;i<8;i++,k+=342){
			play[i].setBounds(k,569,55,55);
		}
		
		//相
		for (i=10,k=138;i<12;i++,k+=228){
			play[i].setBounds(k,569,55,55);
		}
		
		//士
		for (i=14,k=195;i<16;i++,k+=114){
			play[i].setBounds(k,569,55,55);
		}
		
		//兵
		for (i=21,k=24;i<26;i++,k+=114){
			play[i].setBounds(k,398,55,55);
		}
		
		//炮
		for (i=28,k=81;i<30;i++,k+=342){
			play[i].setBounds(k,455,55,55);
		}
		
		//帅
		play[31].setBounds(252,569,55,55);		
		
		for (i=0;i<32;i++){
			play[i].setVisible(true);
		}
	}
	
	
	/**
	** 添加棋子方法
	*/
	public static void drawChessMan(JLabel[] play) {
		//流程控制
		int i,k;
		//图标
		Icon in;
				
		//黑色棋子
		
		//车(看成基准点24,56)
		in = new ImageIcon("src\\image\\黑车.gif");
		for (i=0,k=24;i<2;i++,k+=456){		
			play[i] = new JLabel(in);
			play[i].setBounds(k,56,55,55);	
			play[i].setName("车1");		
		}	
		
		//马
		in = new ImageIcon("src\\image\\黑马.gif");
		for (i=4,k=81;i<6;i++,k+=342){	
			play[i] = new JLabel(in);
			play[i].setBounds(k,56,55,55);
			play[i].setName("马1");
		}
		
		//相
		in = new ImageIcon("src\\image\\黑象.gif");
		for (i=8,k=138;i<10;i++,k+=228){	
			play[i] = new JLabel(in);
			play[i].setBounds(k,56,55,55);
			play[i].setName("象1");
		}
		
		//士
		in = new ImageIcon("src\\image\\黑士.gif");
		for (i=12,k=195;i<14;i++,k+=114){
			play[i] = new JLabel(in);
			play[i].setBounds(k,56,55,55);
			play[i].setName("士1");
		}
		
		//卒
		in = new ImageIcon("src\\image\\黑卒.gif");
		for (i=16,k=24;i<21;i++,k+=114){
			play[i] = new JLabel(in);
			play[i].setBounds(k,227,55,55);
			play[i].setName("卒1" + i);
		}
		
		//炮
		in = new ImageIcon("src\\image\\黑炮.gif");			
		for (i=26,k=81;i<28;i++,k+=342){
			play[i] = new JLabel(in);
			play[i].setBounds(k,170,55,55);
			play[i].setName("炮1" + i);
		}
		
		//将
		in = new ImageIcon("src\\image\\黑将.gif");
		play[30] = new JLabel(in);
		play[30].setBounds(252,56,55,55);
		play[30].setName("将1");

		//红色棋子
		//车
		in = new ImageIcon("src\\image\\红车.gif");
		for (i=2,k=24;i<4;i++,k+=456){
			play[i] = new JLabel(in);
			play[i].setBounds(k,569,55,55);
			play[i].setName("车2");
		}
		
		//马
		in = new ImageIcon("src\\image\\红马.gif");
		for (i=6,k=81;i<8;i++,k+=342){
			play[i] = new JLabel(in);
			play[i].setBounds(k,569,55,55);
			play[i].setName("马2");
		}
		
		//相
		in = new ImageIcon("src\\image\\红象.gif");			
		for (i=10,k=138;i<12;i++,k+=228){
			play[i] = new JLabel(in);
			play[i].setBounds(k,569,55,55);
			play[i].setName("象2");
		}
		
		//士
		in = new ImageIcon("src\\image\\红士.gif");
		for (i=14,k=195;i<16;i++,k+=114){
			play[i] = new JLabel(in);
			play[i].setBounds(k,569,55,55);
			play[i].setName("士2");
		}
		
		//兵
		in = new ImageIcon("src\\image\\红卒.gif");
		for (i=21,k=24;i<26;i++,k+=114){
			play[i] = new JLabel(in);
			play[i].setBounds(k,398,55,55);
			play[i].setName("卒2" + i);
		}
		
		//炮
		in = new ImageIcon("src\\image\\红炮.gif");
		for (i=28,k=81;i<30;i++,k+=342){
			play[i] = new JLabel(in);
			play[i].setBounds(k,455,55,55);
			play[i].setName("炮2" + i);
		}
		
		//帅
		in = new ImageIcon("src\\image\\红将.gif");			
		play[31] = new JLabel(in);
		play[31].setBounds(252,569,55,55);		
		play[31].setName("帅2");
	}
}
