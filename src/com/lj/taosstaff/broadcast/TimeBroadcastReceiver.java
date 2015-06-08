package com.lj.taosstaff.broadcast;

import java.util.ArrayList;

import com.lj.taosstaff.service.MessageService;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeBroadcastReceiver extends BroadcastReceiver {
	//static final String action_time="android.intent.action.TIME_TICK";
	static final String action_time=Intent.ACTION_PACKAGE_DATA_CLEARED;
	static final String action_service="com.lj.taosstaff.service.MessageService";
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		//String dd=arg1.ACTION_TIME_TICK;
		if(arg1.getAction().equals(action_time)){
			System.out.println(action_time);
			//先判断服务是否存在，不存在则启动
			if(!serviceIsWorked(arg0)){//没有运行，则运行
				Intent service=new Intent();
				service.setClass(arg0.getApplicationContext(), MessageService.class);
				arg0.startService(service);
			}
		}
	}
	 //判断服务是否已经在运行
    public boolean serviceIsWorked(Context arg0) {  
    	ActivityManager myManager=(ActivityManager)arg0.getSystemService(Context.ACTIVITY_SERVICE);  
    	ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30); 
    	for(int i = 0 ; i<runningService.size();i++)  { 
    		if(runningService.get(i).service.getClassName().toString().equals(action_service))
    		{
    			return true;   
    		}  
    	}
    	return false; 
    }
}
