package me.jiangmin.jimmy.UI;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * 再按一次退出
 * 
 * @author Jimmy Chiang
 *         <p>
 *         下面是一段典型的使用代码
 * 
 *         <pre>
 * public boolean onKeyDown(int keyCode, KeyEvent event) {
 * 
 * 	if (keyCode == KeyEvent.KEYCODE_BACK) {
 * 		if (exitHelper.isExit()) {
 * 			finish();
 * 			return true;
 * 		} else {
 * 			Toast.makeText(getApplicationContext(), "再按一次退出程序",
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
	 * 如果在一分钟内再次单击返回键，则维持isExit的值为true，否则改为false
	 */
	public void doExitInOneSecond() {
		this.doExitInTimeDelay(1000);
	}

	/**
	 * 如果在延迟时间内再次单击返回键，则维持isExit的值为true，否则改为false
	 * 
	 * @param delayTime
	 *            : 延迟时间
	 */
	public void doExitInTimeDelay(int delayTime) {
		isExit = true;
		HandlerThread thread = new HandlerThread("doTask");
		thread.start();
		new Handler(thread.getLooper()).postDelayed(task, 1000);
	}

	/**
	 * 确定是否真的要退出
	 * 
	 * @return
	 */
	public boolean isExit() {
		return isExit;
	}
}
