package me.jiangmin.jimmy.UI;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * �ٰ�һ���˳�
 * 
 * @author Jimmy Chiang
 *         <p>
 *         ������һ�ε��͵�ʹ�ô���
 * 
 *         <pre>
 * public boolean onKeyDown(int keyCode, KeyEvent event) {
 * 
 * 	if (keyCode == KeyEvent.KEYCODE_BACK) {
 * 		if (exitHelper.isExit()) {
 * 			finish();
 * 			return true;
 * 		} else {
 * 			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
 * 					Toast.LENGTH_SHORT).show();
 * 			exitHelper.doExitInOneSecond();
 * 			return true;
 * 		}
 * 	}
 * 	return super.onKeyDown(keyCode, event);
 * }
 * </pre>
 */
public class ExitOneMoreTouch {
	private boolean isExit = false;
	private Runnable task = new Runnable() {
		@Override
		public void run() {
			isExit = false;
		}
	};

	/**
	 * �����һ�������ٴε������ؼ�����ά��isExit��ֵΪtrue�������Ϊfalse
	 */
	public void doExitInOneSecond() {
		this.doExitInTimeDelay(1000);
	}

	/**
	 * ������ӳ�ʱ�����ٴε������ؼ�����ά��isExit��ֵΪtrue�������Ϊfalse
	 * 
	 * @param delayTime
	 *            : �ӳ�ʱ��
	 */
	public void doExitInTimeDelay(int delayTime) {
		isExit = true;
		HandlerThread thread = new HandlerThread("doTask");
		thread.start();
		new Handler(thread.getLooper()).postDelayed(task, 1000);
	}

	/**
	 * ȷ���Ƿ����Ҫ�˳�
	 * 
	 * @return
	 */
	public boolean isExit() {
		return isExit;
	}
}
