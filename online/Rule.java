package merge2;

import java.awt.event.MouseEvent;

import javax.swing.JLabel;

/*
 * һ����57����λ
 * �ƶ��Ĺ���ͳ��ӵĹ���ֿ�
 * 
 * ��Щ���ӹ��������Ƶģ����糵����
 * ���ߺ����к�����������3��ʿ��2������4
 */
public class Rule {
	
	// ���ӵ��ƶ�����
	public int[] armsRule(int Man, JLabel play, MouseEvent e) {
		// ��������
		if(Man < 21) {
			// �����ƶ�����һ���ķ�Χ�ڶ�����Ϊ�����£����Ը���27��86��ֵ
			if((e.getY() - play.getY() > 27) && (e.getY() - play.getY() < 86) &&
					(e.getX()-play.getX() < 55) && (e.getX()-play.getX() > 0)) {
				// ����
				play.setBounds(play.getX(), play.getY()+57, 55, 55);
				return new int[]{play.getX(), play.getY()+57};
			}
			
			// �����ƶ���������Ҫ����
			else if(play.getY() > 284 && (e.getX() - play.getX() >= 57) && (e.getX() - play.getX() <= 112)) {
				play.setBounds(play.getX()+57, play.getY(), 55, 55);
				return new int[]{play.getX()+57, play.getY()};
			}
			
			// �����ƶ���������Ҫ����
			else if(play.getY() > 284 && (play.getX() - e.getX() >= 2) && (play.getX() - e.getX() <= 58)) {
				play.setBounds(play.getX()-57, play.getY(), 55, 55);
				return new int[]{play.getX()-57, play.getY()};
			}
		}
		
		// ��������
		else {
			//�����ƶ����õ��յ������ģ���ɺϷ�������
			if ((e.getX()-play.getX()) >= 0 && (e.getX()-play.getX()) <= 55 && 
					(play.getY()-e.getY()) >27 && play.getY()-e.getY() < 86){
				play.setBounds(play.getX(),play.getY()-57,55,55);
				return new int[]{play.getX(),play.getY()-57};
			}
			
			//�����ƶ����õ��յ������ģ���ɺϷ������ꡢ�������
			else if (play.getY() <= 341 && (e.getX() - play.getX()) >= 57 && (e.getX() - play.getX()) <= 112){
				play.setBounds(play.getX()+57,play.getY(),55,55);
				return new int[]{play.getX()+57,play.getY()};
			}				
			
			//�����ƶ����õ��յ������ģ���ɺϷ������ꡢ�������
			else if (play.getY() <= 341 && (play.getX() - e.getX()) >= 3 && (play.getX() - e.getX()) <=58){
				play.setBounds(play.getX()-57,play.getY(),55,55);
				return new int[]{play.getX()-57,play.getY()};
			}
		}
		
		// ���͹���
		return null;
	}
	
	
	/**��������:ͨ������setVisible(false)�Ͱ����ɾ���ˣ������ٵ�Ͳ����з�Ӧ��
	 * ����ÿһ�ֿ��� �� ���ӵĿ���
	 * */
	public boolean armsRuleEat(int Man, JLabel play1, JLabel play2){
		boolean canEat = false;
		
		// ��������
		if(Man < 21) {
			// �����ƶ�����һ���ķ�Χ�ڶ�����Ϊ�����£����Ը���27��86��ֵ
			if((play2.getY() - play1.getY() > 27) && (play2.getY() - play1.getY() < 86) &&
					(play2.getX()-play1.getX() < 55) && (play2.getX()-play1.getX() > 0)) {
				canEat = true;
			}
			
			// �����ƶ���������Ҫ����
			else if(play1.getY() > 284 && (play2.getX() - play1.getX() >= 57) && (play2.getX() - play1.getX() <= 112)) {
				canEat = true;
			}
			
			// �����ƶ���������Ҫ����
			else if(play1.getY() > 284 && (play1.getX() - play2.getX() >= 2) && (play1.getX() - play2.getX() <= 58)) {
				canEat = true;
			}
		}
		
		// ��������
		else {
			//�����ƶ����õ��յ������ģ���ɺϷ�������
			if ((play2.getX()-play1.getX()) >= 0 && (play2.getX()-play1.getX()) <= 55 && 
					(play1.getY()-play2.getY()) >27 && play1.getY()-play2.getY() < 86){
				canEat = true;
			}
			
			//�����ƶ����õ��յ������ģ���ɺϷ������ꡢ�������
			else if (play1.getY() <= 341 && (play2.getX() - play1.getX()) >= 57 && (play2.getX() - play1.getX()) <= 112){
				canEat = true;
			}				
			
			//�����ƶ����õ��յ������ģ���ɺϷ������ꡢ�������
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

	// �ڵ��ƶ�����
	public int[] cannonRule(JLabel choosen, JLabel[] play, MouseEvent e) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(e.getX(), e.getY());
		
		if(curPos[0] != targetPos[0] && curPos[1] != targetPos[1])		return null;
		if(curPos[0] == targetPos[0] && curPos[1] == targetPos[1])		return null;
		
		// ��ͬһ�л���ͬһ�У����ж��м��Ƿ�Ҳ������
		int cnt = Util.countBetween(curPos, targetPos, play);
		
		if(cnt != 0)	return null;
		
		// �����б꣬�б�ȷ���ŵ�λ��
		int[] targetCordinate = Util.getCordinate(targetPos[0], targetPos[1]);
		choosen.setBounds(targetCordinate[0], targetCordinate[1], 55, 55);
		
		return targetCordinate;
	}

	// �ڳ���
	public boolean cannonRuleEat(JLabel choosen, JLabel target, JLabel[] play) {
		
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(target.getX(), target.getY());
		
		if(curPos[0] != targetPos[0] && curPos[1] != targetPos[1])		return false;
		if(curPos[0] == targetPos[0] && curPos[1] == targetPos[1])		return false;
		
		// ��ͬһ�л���ͬһ�У����ж��м��Ƿ��һ������
		int cnt = Util.countBetween(curPos, targetPos, play);
		
		if(cnt != 1)	return false;
		
		target.setVisible(false);
		choosen.setBounds(target.getX(), target.getY(), 55, 55);
		target.setBounds(-1, -1, 0, 0);
		return true;
	}

	// ������
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

	// ���ƶ�
	public int[] horseRule(JLabel choosen, JLabel[] play, MouseEvent e) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(e.getX(), e.getY());
		
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return null;
		
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 3)	return null;
		
		// �ж��е㴦�ǲ���������
		int row_mid = (curPos[0] + targetPos[0]) / 2, col_mid = (curPos[1] + targetPos[1]) / 2;
		for(int i=0; i<32; i++) {
			int[] everyPos = Util.getPosition(play[i].getX(), play[i].getY());
			if(row_mid == everyPos[0] && col_mid == everyPos[1])
				return null;
		}
		
		// �����б꣬�б�ȷ���ŵ�λ��
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
		
		// �ж��е㴦�ǲ���������
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
		
		// ˮƽ�ƶ���return false
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return null;
		
		// ������
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 4)	return null;
		
		// ���ܹ���
		if(type == 0) {
			// ����
			if(targetPos[1] <= 4)	return null;
		} else {
			// ����		
			if(targetPos[1] > 4)	return null;
		}
		
		// �ж��е㴦�ǲ���������
		int row_mid = (curPos[0] + targetPos[0]) / 2, col_mid = (curPos[1] + targetPos[1]) / 2;
		for(int i=0; i<32; i++) {
			int[] everyPos = Util.getPosition(play[i].getX(), play[i].getY());
			if(row_mid == everyPos[0] && col_mid == everyPos[1])
				return null;
		}
		
		// �����б꣬�б�ȷ���ŵ�λ��
		int[] targetCordinate = Util.getCordinate(targetPos[0], targetPos[1]);
		choosen.setBounds(targetCordinate[0], targetCordinate[1], 55, 55);
		
		return targetCordinate;
	}


	public boolean elephantRuleEat(int type, JLabel choosen, JLabel target, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(target.getX(), target.getY());
		
		// ˮƽ�ƶ���return false
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return false;
		
		// ������
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 4)	return false;
		
		// ���ܹ���
		if(type == 0) {
			// ����
			if(targetPos[1] <= 4)	return false;
		} else {
			// ����		
			if(targetPos[1] > 4)	return false;
		}
		
		// �ж��е㴦�ǲ���������
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
		
		// ˮƽ�ƶ���return false
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return null;
		
		// ��б��
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 2)	return null;
		
		// ������ĳ������
		if(type == 0) {
			// ����
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] < 7)	return null;
		} else {
			// ����		
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] > 2)	return null;
		}
		
		// �����б꣬�б�ȷ���ŵ�λ��
		int[] targetCordinate = Util.getCordinate(targetPos[0], targetPos[1]);
		choosen.setBounds(targetCordinate[0], targetCordinate[1], 55, 55);
		
		return targetCordinate;
	}

	public boolean chapRuleEat(int type, JLabel choosen, JLabel target, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(target.getX(), target.getY());
		
		// ˮƽ�ƶ���return false
		if(curPos[0] == targetPos[0] || curPos[1] == targetPos[1])		return false;
		
		// ��б��
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 2)	return false;
		
		// ������ĳ������
		if(type == 0) {
			// ����
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] < 7)	return false;
		} else {
			// ����		
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] > 2)	return false;
		}
		
		target.setVisible(false);
		choosen.setBounds(target.getX(), target.getY(), 55, 55);
		target.setBounds(-1, -1, 0, 0);
		return true;
	}
	
	
	
	// ��˧���ƶ�
	public int[] willRule(int type, JLabel choosen, MouseEvent e, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(e.getX(), e.getY());
		
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 1)	return null;
		
		// ������ĳ������
		if(type == 0) {
			// ����
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] < 7)	return null;
		} else {
			// ����		
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] > 2)	return null;
		}
		
		// �����б꣬�б�ȷ���ŵ�λ��
		int[] targetCordinate = Util.getCordinate(targetPos[0], targetPos[1]);
		choosen.setBounds(targetCordinate[0], targetCordinate[1], 55, 55);
		
		return targetCordinate;
	}


	public boolean willRuleEat(int type, JLabel choosen, JLabel target, JLabel[] play) {
		int[] curPos 	= Util.getPosition(choosen.getX(), choosen.getY());
		int[] targetPos	= Util.getPosition(target.getX(), target.getY());
		
		int diff = Math.abs(curPos[0] - targetPos[0]) + Math.abs(curPos[1] - targetPos[1]);
		if(diff != 1)	return false;
		
		// ������ĳ������
		if(type == 0) {
			// ����
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] < 7)	return false;
		} else {
			// ����		
			if(targetPos[0] < 3 || targetPos[0] > 5 || targetPos[1] > 2)	return false;
		}
		
		target.setVisible(false);
		choosen.setBounds(target.getX(), target.getY(), 55, 55);
		target.setBounds(-1, -1, 0, 0);
		return true;
	}


}
