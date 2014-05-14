package org.das.ninjamessaging.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

public class ConexionBD {
	
//ATRIBUTOS
	private static ConexionBD miConexionBD;
	private ArrayList<NameValuePair> parametros;
	private HttpParams httpParameters;
	private HttpResponse response;
	private HttpClient httpclient;
	private HttpPost httppost;
	private HttpEntity entity;
	private Context context;
	
//CONSTRUCTORA	Y GET
	private ConexionBD(Context context) throws UnsupportedEncodingException {
		super();
		parametros = new ArrayList<NameValuePair>();
		httpParameters = new BasicHttpParams();
		httppost = new HttpPost("http://galan.ehu.es/ragudo001/DAS/register.php");
		httppost.setEntity(new UrlEncodedFormEntity(parametros));
		httpclient = new DefaultHttpClient(httpParameters);
		HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
		HttpConnectionParams.setSoTimeout(httpParameters, 15000);
		this.context = context; 
	}
	
	public static ConexionBD getMiConexionBD(Context context) throws UnsupportedEncodingException {
		if(miConexionBD == null) {
			miConexionBD = new ConexionBD(context);
		}
		return miConexionBD;
	}

	public void registrar(String msg) {

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		String user = sharedPref.getString("IMEI", "");
		new AsyncTask<String, Void, Void>() {

			@Override
			protected Void doInBackground(String... params) {
				try {
					parametros.clear();
					parametros.add(new BasicNameValuePair("regid", params[0]));
					parametros.add(new BasicNameValuePair("codigo", params[1]));
					parametros.add(new BasicNameValuePair("IMEI", params[2]));
					
					httppost.setEntity(new UrlEncodedFormEntity(parametros));
					response = httpclient.execute(httppost);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			
		}.execute(msg, "1", user);
		
	}

	public void enviarMensaje(String data, String user) {
		
		String mensaje = data;
		String toUser = user;
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		toUser = sharedPref.getString("IMEI", "");
		new AsyncTask<String, Void, Void>(){

			@Override
			protected Void doInBackground(String... params) {
				
				try {
					parametros.clear();
					parametros.add(new BasicNameValuePair("my_message", params[0]));
					parametros.add(new BasicNameValuePair("codigo", params[1]));
					parametros.add(new BasicNameValuePair("toUser", params[2]));
					httppost.setEntity(new UrlEncodedFormEntity(parametros));
					response = httpclient.execute(httppost);
					entity = response.getEntity();
					String error = EntityUtils.toString(entity);
					Log.e("Error", error);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return null;
			}
			
		}.execute(mensaje, "2", toUser);
	}
}