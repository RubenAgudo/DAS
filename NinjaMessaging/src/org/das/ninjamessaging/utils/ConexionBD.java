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
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ConexionBD extends AsyncTask<String, String, JSONObject> {
	
//ATRIBUTOS
	private static ConexionBD miConexionBD;
	private ArrayList<NameValuePair> parametros;
	private HttpParams httpParameters;
	private HttpResponse response;
	private HttpClient httpclient;
	private HttpPost httppost;
	private HttpEntity entity;
	
//CONSTRUCTORA	Y GET
	private ConexionBD() throws UnsupportedEncodingException {
		super();
		parametros = new ArrayList<NameValuePair>();
		httpParameters = new BasicHttpParams();
		httppost = new HttpPost("http://10.0.2.2/NinjaMessaging/ninjamessaging.php");
		httppost.setEntity(new UrlEncodedFormEntity(parametros));
		httpclient = new DefaultHttpClient(httpParameters);
		HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
		HttpConnectionParams.setSoTimeout(httpParameters, 15000);
	}
	
	public static ConexionBD getMiConexionBD(Context context) throws UnsupportedEncodingException {
		if(miConexionBD == null) {
			miConexionBD = new ConexionBD();
		}
		return miConexionBD;
	}

//METODOS
	/*public String buscarLinea(String pValor) throws ClientProtocolException, IOException {
		ArrayList<String> lista = new ArrayList<String>();
		parametros.clear();
		parametros.add(new BasicNameValuePair("NombreABuscar", pValor));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		
		String result = EntityUtils.toString(entity);
		try {
			JSONObject jsonObject = new JSONObject(result);
			String nom = jsonObject.getString("Nombre");
			lista.add(nom);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;	
	}*/
	
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	protected void onCancelled(Boolean result) {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		JSONObject jo = new JSONObject();
		
			try {
				httppost.setEntity(new UrlEncodedFormEntity(parametros));
				response = httpclient.execute(httppost);
				entity = response.getEntity();

				String result = EntityUtils.toString(entity);
				Log.i("hola", result);
				jo = new JSONObject(result);
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
			return jo;
	}
	
	public void onPreExecute() {
		super.onPreExecute();
		parametros.clear();
		parametros.add(new BasicNameValuePair("NombreABuscar", "0"));
	}
}