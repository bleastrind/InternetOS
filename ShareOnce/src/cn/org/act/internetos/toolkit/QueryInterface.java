package cn.org.act.internetos.toolkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.org.act.internetos.activities.Activity;

public class QueryInterface {
	private String queryUrl;
	public QueryInterface(String baseUrl){
		this.queryUrl = baseUrl + "/QueryActivities";
	}
	
	public Collection<Activity> queryAllActivities(String usertoken){
		HttpClient client = new DefaultHttpClient();
		String queryString = queryUrl;
		HttpGet request = new HttpGet(queryString);
		request.addHeader("token", usertoken);
		try {
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream is = entity.getContent();
				
				return getActivities(readStream(is,"utf-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private Collection<Activity> getActivities(String dataString) {
		ArrayList<Activity> res = new ArrayList<Activity>();

		try {
			JSONArray list = new JSONArray(dataString);

			for (int i = 0; i < list.length(); i++) {
				JSONObject activityJSON = list.getJSONObject(i);
				String name = activityJSON.optString("name");
				String type = activityJSON.optString("type");
				String state = activityJSON.optString("state");
				
				res.add( new Activity(name,type,state) );
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private static String readStream(InputStream stream,String charset) throws IOException
	{
		if(stream == null)
			return null;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				stream, charset));
		String line = "";
		line +=reader.readLine();
		StringBuffer sb = new StringBuffer(line);
		while ((line = reader.readLine()) != null) {
			sb.append("\n").append(line);
		}
		if (reader != null) {
			reader.close();
		}
		return sb.toString();
	}
}
