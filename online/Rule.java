package merge2;

import java.awt.event.MouseEvent;

import javax.swing.JLabel;

/*
 * 一个是57个单位
 * 移动的规则和吃子的规则分开
 * 
 * 有些棋子规则还是相似的，比如车和炮
 * 马走后行列号相差加起来是3，士是2，象是4
 */
public class Rule {
	
	// 卒子的移动规则
	public int[] armsRule(int Man, JLabel play, MouseEvent e) {
		// 黑卒向下
		if(Man < 21) {
			// 向下移动，在一定的范围内都被看为是向下，可以更改27，86的值
			if((e.getY() - play.getY() > 27) && (e.getY() - play.getY() < 86) &&
					(e.getX()-play.getX() < 55) && (e.getX()-play.getX() > 0)) {
				// 落子
				play.setBounds(play.getX(), play.getY()+57, 55, 55);
				return new int[]{play.getX(), play.getY()+57};
			}
			
			// 向右移动，但是先要过河
			else if(play.getY() > 284 && (e.getX() - play.getX() >= 57) && (e.getX() - play.getX() <= 112)) {
				play.setBounds(play.getX()+57, play.getY(), 55, 55);
				return new int[]{play.getX()+57, play.getY()};
			}
			
			// 向左移动，但是先要过河
			else if(play.getY() > 284 && (play.getX() - e.getX() >= 2) && (play.getX() - e.getX() <= 58)) {
				play.setBounds(play.getX()-57, play.getY(), 55, 55);
				return new int[]{play.getX()-57, play.getY()};
			}
		}
		
		// 红卒向上
		else {
			//向上移动、得到终点的坐标模糊成合法的坐标
			if ((e.getX()-play.getX()) >= 0 && (e.getX()-play.getX()) <= 55 && 
					(play.getY()-e.getY()) >27 && play.getY()-e.getY() < 86){
				play.setBounds(play.getX(),play.getY()-57,55,55);
				return new int[]{play.getX(),play.getY()-57};
			}
			
			//向右移动、得到终点的坐标模糊成合法的坐标、必须过河
			else if (play.getY() <= 341 && (e.getX() - play.getX()) >= 57 && (e.getX() - play.getX()) <= 112){
				play.setBounds(play.getX()+57,play.getY(),55,55);
				return new int[]{play.getX()+57,play.getY()};
			}				
			
			//向左移动、得到终点的坐标模糊成合法的坐标、必须过河
			else if (play.getY() <= 341 && (play.getX() - e.getX()) >= 3 && (play.getX() - e.getX()) <=58){
				play.setBounds(play.getX()-57,play.getY(),55,55);
				return new int[]{play.getX()-57,play.getY()};
			}
		}
		
		// 不和规则
		return null;
	}
	
	
	/**卒吃棋规则:通过设置setVisible(false)就把这个删除了，这样再点就不会有反应了
	 * 遍历每一种可能 兵 吃子的可能
	 * */
	public boolean armsRuleEat(int Man, JLabel play1, JLabel play2){
		boolean canEat = false;
		
		// 黑卒向下
		if(Man < 21) {
			// 向下移动，在一定的范围内都被看为是向下，可以更改27，86的值
			if((play2.getY() - play1.getY() > 27) && (play2.getY() - play1.getY() < 86) &&
					(play2.getX()-play1.getX() < 55) && (play2.getX()-play1.getX() > 0)) {
				canEat = true;
			}
			
			// 向右移动，但是先要过河
			else if(play1.getY() > 284 && (play2.getX() - play1.getX() >= 57) && (play2.getX() - play1.getX() <= 112)) {
				canEat = true;
			}
			
			// 向左移动，但是先要过河
			else if(play1.getY() > 284 && (play1.getX() - play2.getX() >= 2) && (play1.getX() - play2.getX() <= 58)) {
				canEat = true;
			}
		}
		
		// 红卒向上
		else {
			//向上移动、得到终点的坐标模糊成合法的坐标
			if ((play2.getX()-play1.getX()) >= 0 && (play2.getX()-play1.getX()) <= 55 && 
					(play1.getY()-play2.getY()) >27 && play1.getY()-play2.getY() < 86){
				canEat = true;
			}
			
			//向右移动、得到终点的坐标模糊成合法的坐标、必须过河
			else if (play1.getY() <= 341 && (play2.getX() - play1.getX()) >= 57 && (play2.getX() - play1.getX()) <= 112){
				canEat = true;
			}				
			
			//向左移动、得到终点的坐标模糊成合法的坐标、必须过河
			else if (play1.getY() <= 341 && (play1.getX() - play2.getX()) >= 3 && (play1.getX() - play2.getX()) <=58){
				canEat = true;
			}
		}
		
		if(canEat) {
			play2.setVisible(false);
			play1.setBounds(play2.getX(), play2.getY(), 55, 55);
			play2.setBounds(-1, -1, 0, 0);
		}
		return canEat;
	}

	// 炮的移动规则
	public int[] cannonRule(JLabel choosen, JLabel[] play, MouseEvent e) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(e.getX(), e.getY());
		
		if(curPos[0] != targetPos[0] && curPos[1] != targetPos[1])		return null;
		if(curPos[0] == targetPos[0] && curPos[1] == targetPos[1])		return null;
		
		// 在同一行或者同一列，再判断中间是否也有棋子
		int cnt = Util.countBetween(curPos, targetPos, play);
		
		if(cnt != 0)	return null;
		
		// 根据行标，列表确定放的位置
		int[] targetCordinate = Util.getCordinate(targetPos[0], targetPos[1]);
		choosen.setBounds(targetCordinate[0], targetCordinate[1], 55, 55);
		
		return targetCordinate;
	}

	// 炮吃子
	public boolean cannonRuleEat(JLabel choosen, JLabel target, JLabel[] play) {
		
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(target.getX(), target.getY());
		
		if(curPos[0] != targetPos[0] && curPos[1] != targetPos[1])		return false;
		if(curPos[0] == targetPos[0] && curPos[1] == targetPos[1])		return false;
		
		// 在同一行或者同一列，再判断中间是否隔一个棋子
		int cnt = Util.countBetween(curPos, targetPos, play);
		
		if(cnt != 1)	return false;
		
		target.setVisible(false);
		choosen.setBounds(target.getX(), target.getY(), 55, 55);
		target.setBounds(-1, -1, 0, 0);
		return true;
	}

	// 车吃子
	public boolean cartRuleEat(JLabel choosen, JLabel target, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(target.getX(), target.getY());
		
		if(curPos[0] != targetPos[0] && curPos[1] != targetPos[1])		return false;
		if(curPos[0] == targetPos[0] && curPos[1] == targetPos[1])		return false;
		
		int cnt = Util.countBetween(curPos, targetPos, play);
		
		if(cnt != 0)	return false;
		target.setVisible(false);
		choosen.setBounds(target.getX(), target.getY(), 55, 55);
		target.setBounds(-1, -1, 0, 0);
		return true;
	}

	// 马移动
	public int[] horseRule(JLabel choosen, JLabel[] play, MouseEvent e) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(e.getX(), e.getY());
		
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return null;
		
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 3)	return null;
		
		// 判断中点处是不是有棋子
		int row_mid = (curPos[0] + targetPos[0]) / 2, col_mid = (curPos[1] + targetPos[1]) / 2;
		for(int i=0; i<32; i++) {
			int[] everyPos = Util.getPosition(play[i].getX(), play[i].getY());
			if(row_mid == everyPos[0] && col_mid == everyPos[1])
				return null;
		}
		
		// 根据行标，列表确定放的位置
		int[] targetCordinate = Util.getCordinate(targetPos[0], targetPos[1]);
		choosen.setBounds(targetCordinate[0], targetCordinate[1], 55, 55);
		
		return targetCordinate;
	}


	public boolean horseRuleEat(JLabel choosen, JLabel target, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(target.getX(), target.getY());
		
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return false;
		
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 3)	return false;
		
		// 判断中点处是不是有棋子
		int row_mid = (curPos[0] + targetPos[0]) / 2, col_mid = (curPos[1] + targetPos[1]) / 2;
		for(int i=0; i<32; i++) {
			int[] everyPos = Util.getPosition(play[i].getX(), play[i].getY());
			if(row_mid == everyPos[0] && col_mid == everyPos[1])
				return false;
		}
		
		target.setVisible(false);
		choosen.setBounds(target.getX(), target.getY(), 55, 55);
		target.setBounds(-1, -1, 0, 0);
		return true;
	}


	public int[] elephantRule(int type, JLabel choosen, JLabel[] play, MouseEvent e) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(e.getX(), e.getY());
		
		// 水平移动就return false
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return null;
		
		// 走田字
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 4)	return null;
		
		// 不能过河
		if(type == 0) {
			// 红旗
			if(targetPos[1] <= 4)	return null;
		} else {
			// 黑旗		
			if(targetPos[1] > 4)	return null;
		}
		
		// 判断中点处是不是有棋子
		int row_mid = (curPos[0] + targetPos[0]) / 2, col_mid = (curPos[1] + targetPos[1]) / 2;
		for(int i=0; i<32; i++) {
			int[] everyPos = Util.getPosition(play[i].getX(), play[i].getY());
			if(row_mid == everyPos[0] && col_mid == everyPos[1])
				return null;
		}
		
		// 根据行标，列表确定放的位置
		int[] targetCordinate = Util.getCordinate(targetPos[0], targetPos[1]);
		choosen.setBounds(targetCordinate[0], targetCordinate[1], 55, 55);
		
		return targetCordinate;
	}


	public boolean elephantRuleEat(int type, JLabel choosen, JLabel target, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(target.getX(), target.getY());
		
		// 水平移动就return false
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return false;
		
		// 走田字
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 4)	return false;
		
		// 不能过河
		if(type == 0) {
			// 红旗
			if(targetPos[1] <= 4)	return false;
		} else {
			// 黑旗		
			if(targetPos[1] > 4)	return false;
		}
		
		// 判断中点处是不是有棋子
		int row_mid = (curPos[0] + targetPos[0]) / 2, col_mid = (curPos[1] + targetPos[1]) / 2;
		for(int i=0; i<32; i++) {
			int[] everyPos = Util.getPosition(play[i].getX(), play[i].getY());
			if(row_mid == everyPos[0] && col_mid == everyPos[1])
				return false;
		}
		
		target.setVisible(false);
		choosen.setBounds(target.getX(), target.getY(), 55, 55);
		target.setBounds(-1, -1, 0, 0);
		return true;
	}


	public int[] chapRule(int type, JLabel choosen, MouseEvent e, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(e.getX(), e.getY());
		
		// 水平移动就return false
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return null;
		
		// 走斜的
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 2)	return null;
		
		// 限制在某个区域
		if(type == 0) {
			// 红旗
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] < 7)	return null;
		} else {
			// 黑旗		
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] > 2)	return null;
		}
		
		// 根据行标，列表确定放的位置
		int[] targetCordinate = Util.getCordinate(targetPos[0], targetPos[1]);
		choosen.setBounds(targetCordinate[0], targetCordinate[1], 55, 55);
		
		return targetCordinate;
	}

	public boolean chapRuleEat(int type, JLabel choosen, JLabel target, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(target.getX(), target.getY());
		
		// 水平移动就return false
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return false;
		
		// 走斜的
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 2)	return false;
		
		// 限制在某个区域
		if(type == 0) {
			// 红旗
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] < 7)	return false;
		} else {
			// 黑旗		
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] > 2)	return false;
		}
		
		target.setVisible(false);
		choosen.setBounds(target.getX(), target.getY(), 55, 55);
		target.setBounds(-1, -1, 0, 0);
		return true;
	}
	
	
	
	// 将帅的移动
	public int[] willRule(int type, JLabel choosen, MouseEvent e, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(e.getX(), e.getY());
		
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 1)	return null;
		
		// 限制在某个区域
		if(type == 0) {
			// 红旗
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] < 7)	return null;
		} else {
			// 黑旗		
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] > 2)	return null;
		}
		
		// 根据行标，列表确定放的位置
		int[] targetCordinate = Util.getCordinate(targetPos[0], targetPos[1]);
		choosen.setBounds(targetCordinate[0], targetCordinate[1], 55, 55);
		
		return targetCordinate;
	}


	public boolean willRuleEat(int type, JLabel choosen, JLabel target, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(target.getX(), target.getY());
		
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 1)	return false;
		
		// 限制在某个区域
		if(type == 0) {
			// 红旗
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] < 7)	return false;
		} else {
			// 黑旗		
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] > 2)	return false;
		}
		
		target.setVisible(false);
		choosen.setBounds(target.getX(), target.getY(), 55, 55);
		target.setBounds(-1, -1, 0, 0);
		return true;
	}


}
