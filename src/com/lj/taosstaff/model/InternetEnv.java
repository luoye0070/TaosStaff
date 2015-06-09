package com.lj.taosstaff.model;

import java.io.Serializable;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class InternetEnv implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String baseUrl=null;
	public String hostIP=null;
	
	public String getBaseUrl(Context a){
		readFromSharedPreferences(a);
		return this.baseUrl;
	}
	public String getHostIP(Context a){
		readFromSharedPreferences(a);
		return this.hostIP;
	}
	
	public InternetEnv(){
		
	}
	public InternetEnv(String baseUrl,String hostIP){
		this.baseUrl=baseUrl;
		this.hostIP=hostIP;
	}
	
	/***********************
	 *���ֶ�д��SharedPreferences 
	 *************************/
	public void writeToSharedPreferences(Context a)
	{
		SharedPreferences preferences = a.getSharedPreferences("internetEnv", Context.MODE_PRIVATE);  
		Editor editor=preferences.edit();
		editor.putString("baseUrl", this.baseUrl);
		editor.putString("hostIP", hostIP);
		editor.commit();
	}
	/*******************
	 *��SharedPreferences�ж�ȡ�ֶ�
	 *********************/
	public void readFromSharedPreferences(Context a)
	{
		SharedPreferences preferences = a.getSharedPreferences("internetEnv", Context.MODE_PRIVATE);  
		this.baseUrl=preferences.getString("baseUrl", "");
		this.hostIP=preferences.getString("hostIP", "");
	}
	/*****************************
	 *����û���Ϣ�Ĺ������� 
	 *******************************/
	public void clearFromSharedPreferences(Context a)
	{
		SharedPreferences preferences = a.getSharedPreferences("internetEnv", Context.MODE_PRIVATE);  
		Editor editor=preferences.edit();
		editor.clear();
		editor.commit();
	}
}
