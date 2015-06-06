package com.mtxc.volley;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {
	
	public static final String TAG = "Volley";
	private String url = "http://112.124.3.197:8005/method/app_bound_yp.php";
	private JSONObject request = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		mQueue.add(new JsonObjectRequest(Method.POST, url, request, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.e(TAG, response.toString());
				try {
					Entity entity = parseJson(response);
					Log.e(TAG, entity.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, error.toString());
			}
		}));
		mQueue.start();
	}

	protected Entity parseJson(JSONObject response) throws JSONException {
		Entity entity = new Entity();
		entity.setRole(response.getString("role"));
		entity.setSex(response.getString("sex"));
		entity.setAge(response.getInt("age"));
		entity.setName(response.getString("name"));
		ArrayList<Info> gfs = new ArrayList<Info>();
		Info info = new Info();
		info.setName(response.getJSONArray("gfs").getJSONObject(0).getString("name"));
		info.setSex(response.getJSONArray("gfs").getJSONObject(0).getString("sex"));
		gfs.add(info);
		info = new Info();
		info.setName(response.getJSONArray("gfs").getJSONObject(1).getString("name"));
		info.setSex(response.getJSONArray("gfs").getJSONObject(1).getString("sex"));
		gfs.add(info);
		entity.setGfs(gfs);
		return entity;
	}
	
}
class Entity{
	private String role;
	private String sex;
	private int age;
	private String name;
	private ArrayList<Info> gfs;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Info> getGfs() {
		return gfs;
	}
	public void setGfs(ArrayList<Info> gfs) {
		this.gfs = gfs;
	}
	@Override
	public String toString() {
		return "Entity [role=" + role + ", sex=" + sex + ", age=" + age
				+ ", name=" + name + ", gfs=" + gfs + "]";
	}
	
}
class Info{
	private String sex;
	private String name;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Info [sex=" + sex + ", name=" + name + "]";
	}
}
