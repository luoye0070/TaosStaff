package com.lj.taosstaff;

import com.lj.taosstaff.common.ActivityManager;
import com.lj.taosstaff.model.InternetEnv;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends Activity {
	
	ActivityManager am=null;//activity������
	
	AutoCompleteTextView settingAy_restaurantIP;
	AutoCompleteTextView settingAy_baseUrl;
	Button settingAy_settingBt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ȥ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*����ǰactivity��ӵ�activitymanager��*/
        am=ActivityManager.getInstance();
        am.addActivity(this);
		//���ý��沼���ļ�
		setContentView(R.layout.activity_setting);		
		
		settingAy_restaurantIP=(AutoCompleteTextView) findViewById(R.id.settingAy_restaurantIdAt);
		settingAy_baseUrl=(AutoCompleteTextView) findViewById(R.id.settingAy_userNameAt);
		
		//���ó�ֵ
		InternetEnv internetEnv=new InternetEnv();
		internetEnv.readFromSharedPreferences(getApplicationContext());
		settingAy_restaurantIP.setText(internetEnv.hostIP);
		settingAy_baseUrl.setText(internetEnv.baseUrl);
		
		settingAy_settingBt=(Button) findViewById(R.id.settingAy_loginBt);
		
		settingAy_settingBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String restaurantIP=settingAy_restaurantIP.getText().toString().trim();
				String baseUrl=settingAy_baseUrl.getText().toString().trim();
				if(restaurantIP.equals("")||baseUrl.equals("")){
					Toast.makeText(SettingActivity.this, "����Ϊ��Ŷ��", Toast.LENGTH_SHORT).show();
					return;
				}
				InternetEnv internetEnv=new InternetEnv(baseUrl,restaurantIP);
				internetEnv.writeToSharedPreferences(getApplicationContext());
				Toast.makeText(SettingActivity.this, "���óɹ���", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
	}
	
}
