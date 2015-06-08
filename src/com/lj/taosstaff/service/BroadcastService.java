package com.lj.taosstaff.service;

import java.util.ArrayList;

import com.lj.taosstaff.broadcast.TimeBroadcastReceiver;
import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class BroadcastService extends Service {
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(receiver!=null){
			unregisterReceiver(receiver);
		}
		super.onDestroy();
	}

	static final String action_service="com.lj.taosstaff.service.MessageService";
	TimeBroadcastReceiver receiver =null;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK); 
		IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_DATA_CLEARED);  
		receiver = new TimeBroadcastReceiver();   
		registerReceiver(receiver, filter); 
		System.out.println("BroadcastService-->onCreate");
		super.onCreate();
		
//		//启动一个线程去检查service是否被kill,被证实没用
//		Thread thread=new Thread(new Runnable() {			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(true){
//					boolean isRuning=false;
//					ActivityManager myManager=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);  
//			    	ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30); 
//			    	for(int i = 0 ; i<runningService.size();i++)  { 
//			    		if(runningService.get(i).service.getClassName().toString().equals(action_service))
//			    		{
//			    			isRuning=true; 
//			    			break;
//			    		}  
//			    	}
//			    	if(!isRuning){
//			    		Intent service=new Intent();
//						service.setClass(getApplicationContext(), MessageService.class);
//						startService(service);
//			    	}
//			    	//休眠500毫秒
//			    	try {
//						Thread.sleep(500);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println("BroadcastService-->Runing");
//				}
//			}
//		});
//		thread.start();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
