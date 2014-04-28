package org.das.labo9.utils;

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
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class CumplePeticiones extends AsyncTask<Void, Void, Boolean> {

	private static CumplePeticiones myClass;
	private ArrayList<NameValuePair> parametros;
	
	private HttpParams httpParameters;
	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpResponse response;
	private HttpEntity entity;
	
	private CumplePeticiones() {
		parametros = new ArrayList<NameValuePair>();
		httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
		HttpConnectionParams.setSoTimeout(httpParameters, 15000);
		
		httpClient = new DefaultHttpClient(httpParameters);
		httpPost = new HttpPost("http://localhost/Labo9/login.php");
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(parametros));
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			
			String result = EntityUtils.toString(entity);
			JSONObject jsonObject = new JSONObject(result);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static CumplePeticiones getCumplePeticiones() {
		if(myClass == null) {
			myClass = new CumplePeticiones();
		}
		
		return myClass;
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected void onCancelled(Boolean result) {
		super.onCancelled(result);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		parametros.add(new BasicNameValuePair("NombreABuscar", "test"));
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		return null;
	}

}
