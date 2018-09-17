package com.br.glossario.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipInputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

@SuppressLint("DefaultLocale")
public class Utils {

	private Context context;

	private static final String TAG = "Utils";

	public Utils(Context context) {
		this.context = context;
	}

	public static boolean closeVirtualKeyboard(Context context, View view) {

		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (imm != null) {
			return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}

		return false;
	}

	public static String durationInSecondsToString(int sec) {
		int hours = sec / 3600;
		int minutes = (sec / 60) - (hours * 60);
		int seconds = sec - (hours * 3600) - (minutes * 60);
		String formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
		return formatted;
	}

	public static String toString(InputStream in, String charset) throws IOException {

		byte[] bytes = toBytes(in);
		String texto = new String(bytes, charset);
		return texto;
	}

	public static byte[] toBytes(InputStream in) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {

			byte[] buffer = new byte[1024];
			int len;

			while ((len = in.read(buffer)) > 0) {
				bos.write(buffer, 0, len);
			}

			byte[] bytes = bos.toByteArray();
			return bytes;

		} catch (Exception e) {

			Log.e(TAG, e.getMessage(), e);
			return null;

		} finally {

			try {

				bos.close();
				in.close();

			} catch (IOException e) {
				Log.e(TAG, e.getMessage(), e);
			}
		}
	}

	public float distancia(Double lat1, Double lon1, Double lat2, Double lon2) {
		double raio = 3958.75;

		double dlat = Math.toRadians(lat2 - lat1);
		double dlon = Math.toRadians(lon2 - lon1);

		double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dlon / 2) * Math.sin(dlon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double d = raio * c;

		double meterConversion = 1609.00;

		return Math.round(d * meterConversion);
	}

	public static boolean isNetworkAvailable(Context contx) {

		ConnectivityManager cm = (ConnectivityManager) contx.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null) {
			return false;

		} else {

			NetworkInfo[] infos = cm.getAllNetworkInfo();

			for (int i = 0; i < infos.length; i++) {

				if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}

		return false;
	}

	public void configProxy(String proxy) {
		Settings.System.putString(context.getContentResolver(), Settings.System.HTTP_PROXY, proxy);
	}

	public ZipInputStream UnzipURLConnection(String URL) throws IOException {

		URLConnection urlConnection;
		ZipInputStream zipInputStream = null;

		try {
			URL finalUrl = new URL(URL);
			urlConnection = finalUrl.openConnection();
			urlConnection.setDoOutput(true);

			zipInputStream = new ZipInputStream(urlConnection.getInputStream());
			return zipInputStream;

		} catch (IOException e) {
		}
		return null;
	}

	public InputStream StreamConnection(String URL) throws IOException {

		URLConnection urlConnection;

		try {
			URL finalUrl = new URL(URL);
			urlConnection = finalUrl.openConnection();
			urlConnection.setDoOutput(true);

			return urlConnection.getInputStream();

		} catch (IOException e) {
		}
		return null;
	}

	public abstract static class WakeLocker {
		private static PowerManager.WakeLock wakeLock;

		public static void acquire(Context context) {

			if (wakeLock != null)
				wakeLock.release();

			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP
					| PowerManager.ON_AFTER_RELEASE, "WakeLock");
			wakeLock.acquire();
		}

		public static void release() {
			if (wakeLock != null)
				wakeLock.release();
			wakeLock = null;
		}
	}

	/**
	 * Checking for all possible internet providers
	 * **/
	public static boolean isConnectingToInternet(Context _context) {

		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity != null) {

			NetworkInfo[] info = connectivity.getAllNetworkInfo();

			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}

		}

		return false;
	}

	public static String replaceAllString(String s) {

		s = s.replaceAll("[èéêë]", "e");
		s = s.replaceAll("[ûù]", "u");
		s = s.replaceAll("[ïîíì]", "i");
		s = s.replaceAll("[àâã]", "a");
		s = s.replaceAll("[óô]", "o");

		s = s.replaceAll("[ÈÉÊË]", "E");
		s = s.replaceAll("[ÛÙ]", "U");
		s = s.replaceAll("[ÏÎ]", "I");
		s = s.replaceAll("[ÀÂÃ]", "A");
		s = s.replaceAll("[ÓÔ]", "O");
		s = s.replaceAll("ç", "c");

		s = s.replace(" ", "%20");

		return s;
	}
}
