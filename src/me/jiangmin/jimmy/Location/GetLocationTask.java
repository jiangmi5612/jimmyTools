/**
 * 
 */
package me.jiangmin.jimmy.Location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;

/**
 * 获取GPS或网络定位信息的异步任务
 * TODO 网络获取定位信息获取不到，异步任务一直挂起，需要解决，可考虑使用第三方服务
 * 例如引入百度SDK
 * 
 * @author Jimmy Chiang
 * @version 1.0.0
 * 
 */
public class GetLocationTask extends AsyncTask<Context, Void, Location> {

	private OnLocationListener[] mGpsListeners;
	private Location location;

	private LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location lo) {
			location = lo;
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * 成功获取到GPS信息时
	 * 
	 * @author Jimmy Chiang
	 * 
	 */
	public interface OnLocationListener {
		public void onLocationGot(Location location);
	}

	/**
	 * 注册监听器，可链式引用
	 * 
	 * @param listeners
	 * @return 对象本身
	 */
	public GetLocationTask setOnLocationListener(
			OnLocationListener... listeners) {
		mGpsListeners = listeners;
		return this;
	}

	@Override
	/**
	 * 获取GPS或网络定位信息
	 */
	protected Location doInBackground(Context... params) {

		// 尝试获取GPS信息

		LocationManager locationManager = (LocationManager) params[0]
				.getSystemService(Context.LOCATION_SERVICE);

		String provider = LocationManager.NETWORK_PROVIDER;
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// GPS可用
			provider = LocationManager.GPS_PROVIDER;

		}
		location = locationManager.getLastKnownLocation(provider);

		while (location == null) {
			locationManager.requestLocationUpdates(provider, 1000, 1,locationListener,Looper.getMainLooper());
		}
		return location;
	}

	@Override
	/**
	 * 获取到GPS或网络定位信息后，执行所有注册的接口实现
	 */
	protected void onPostExecute(Location location) {

		for (OnLocationListener listener : mGpsListeners) {
			listener.onLocationGot(location);
		}
	}
}
