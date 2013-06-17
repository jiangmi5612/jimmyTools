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
 * ��ȡGPS�����綨λ��Ϣ���첽����
 * TODO �����ȡ��λ��Ϣ��ȡ�������첽����һֱ������Ҫ������ɿ���ʹ�õ���������
 * ��������ٶ�SDK
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
	 * �ɹ���ȡ��GPS��Ϣʱ
	 * 
	 * @author Jimmy Chiang
	 * 
	 */
	public interface OnLocationListener {
		public void onLocationGot(Location location);
	}

	/**
	 * ע�������������ʽ����
	 * 
	 * @param listeners
	 * @return ������
	 */
	public GetLocationTask setOnLocationListener(
			OnLocationListener... listeners) {
		mGpsListeners = listeners;
		return this;
	}

	@Override
	/**
	 * ��ȡGPS�����綨λ��Ϣ
	 */
	protected Location doInBackground(Context... params) {

		// ���Ի�ȡGPS��Ϣ

		LocationManager locationManager = (LocationManager) params[0]
				.getSystemService(Context.LOCATION_SERVICE);

		String provider = LocationManager.NETWORK_PROVIDER;
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// GPS����
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
	 * ��ȡ��GPS�����綨λ��Ϣ��ִ������ע��Ľӿ�ʵ��
	 */
	protected void onPostExecute(Location location) {

		for (OnLocationListener listener : mGpsListeners) {
			listener.onLocationGot(location);
		}
	}
}
