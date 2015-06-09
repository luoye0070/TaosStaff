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
	
	ActivityManager am=null;//activity管理类
	
	AutoCompleteTextView settingAy_restaurantIP;
	AutoCompleteTextView settingAy_baseUrl;
	Button settingAy_settingBt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*将当前activity添加到activitymanager中*/
        am=ActivityManager.getInstance();
        am.addActivity(this);
		//设置界面布局文件
		setContentView(R.layout.activity_setting);		
		
		settingAy_restaurantIP=(AutoCompleteTextView) findViewById(R.id.settingAy_restaurantIdAt);
		settingAy_baseUrl=(AutoCompleteTextView) findViewById(R.id.settingAy_userNameAt);
		
		//设置初值
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
					Toast.makeText(SettingActivity.this, "不能为空哦！", Toast.LENGTH_SHORT).show();
					return;
				}
				InternetEnv internetEnv=new InternetEnv(baseUrl,restaurantIP);
				internetEnv.writeToSharedPreferences(getApplicationContext());
				Toast.makeText(SettingActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
	}
	
}
