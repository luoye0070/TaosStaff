package com.lj.taosstaff.service;

import java.util.ArrayList;

import com.lj.taosstaff.LoginActivity;
import com.lj.taosstaff.MessageActivity;
import com.lj.taosstaff.MessageListActivity;
import com.lj.taosstaff.broadcast.TimeBroadcastReceiver;
import com.lj.taosstaff.common.ActivityCallBackInterface;
import com.lj.taosstaff.common.AcvivityLoginGoto;
import com.lj.taosstaff.common.NotifyId;
import com.lj.taosstaff.common.StaticData;
import com.lj.taosstaff.constant.AppConstant;
import com.lj.taosstaff.constant.MessageType;
import com.lj.taosstaff.database.DatabaseHelper;
import com.lj.taosstaff.internet.HttpClientHelper;
import com.lj.taosstaff.internet.ParamCollect;
import com.lj.taosstaff.internet.ParamsCollect;
import com.lj.taosstaff.model.MessageInfo;
import com.lj.taosstaff.model.UserInfo;
import com.lj.taosstaff.string_analysis.DataOfMessageAnalyzeHelper;
import lj.mina.client.AnalyzeHelper;
import lj.mina.client.MinaClient;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class MessageService extends Service {
	static final String action_service="com.lj.taosstaff.service.BroadcastService";
	private boolean isActive=false;
	private MessageInfo currentMsg=null;
	Handler handler=null;
	Thread thread=null;
	MinaClient minaClient;
	Context context; //������
	
//	AlarmManager mAlarmManager = null;
//	PendingIntent mPendingIntent = null;
	//static final String action_service="com.lj.taosstaff.service.MessageService";
//	TimeBroadcastReceiver receiver =null;
	public void onCreate() {
		
//		Intent intent = new Intent(getApplicationContext(), MessageService.class);        
//		mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//		mPendingIntent = PendingIntent.getService(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//		long now = System.currentTimeMillis();
//		mAlarmManager.setInexactRepeating(AlarmManager.RTC, now, 60000, mPendingIntent);
		
		// TODO Auto-generated method stub
//		//IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK); 
//		IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_DATA_CLEARED);  
//		receiver = new TimeBroadcastReceiver();   
//		registerReceiver(receiver, filter); 
		
		super.onCreate();
		context=this;
		if(minaClient!=null){
			minaClient.disconnect();
			minaClient=null;
		}
		startThread();
		System.out.println("onCreate-->");
		
//		//����һ���߳�ȥ���service�Ƿ�kill,��֤ʵû��
//		Thread thread=new Thread(new Runnable() {
//			
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
//						service.setClass(getApplicationContext(), BroadcastService.class);
//						startService(service);
//			    	}
//			    	//����500����
//			    	try {
//						Thread.sleep(500);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println("MessageService-->Runing");
//				}
//			}
//		});
//		thread.start();
		
		
//		//����ע��㲥�ķ���
//		Intent service=new Intent();
//		service.setClass(getApplicationContext(), BroadcastService.class);
//		startService(service);
	}
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		System.out.println("onStart");
		//context = getApplicationContext(); //������
//		context=this;
//		if(minaClient!=null){
//			minaClient.disconnect();
//			minaClient=null;
//		}
//		startThread();
	}
//	@Override
//    public int onStartCommand(Intent intent, int flags, int startid) {
//		System.out.println("onStartCommand");
//        //return START_STICKY;
//		return START_REDELIVER_INTENT;
//    }
//  @Override
//   public int onStartCommand(Intent intent, int flags, int startId) {
//                // TODO Auto-generated method stub
//                flags =  START_STICKY;
//                return super.onStartCommand(intent, flags, startId);
////                return  START_REDELIVER_INTENT;
//        }
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(minaClient!=null){
			minaClient.disconnect();
			minaClient=null;
		}
		if(thread!=null&&!thread.isInterrupted())
		{
			thread.interrupt();
		}
		
//		Intent service=new Intent();
//		service.setClass(getApplicationContext(), MessageService.class);
//		startService(service);
		
//		if(receiver!=null){
//			unregisterReceiver(receiver);
//		}
		
		super.onDestroy();
	}
	private void startThread(){
		isActive=true;
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "���ȵ�¼�Ѿ�ʧЧ�������µ�¼", Toast.LENGTH_LONG).show();
				super.handleMessage(msg);
			}
		};
		thread=new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				recMsg();
			}
		});
		thread.start();
	}
	private void stopThread(){
		
	}
	
	
	private void recMsg(){
		if(isActive){
			//ͨ�����ؼ���û��Ƿ��¼
			UserInfo ui=new UserInfo();
			ui.readFromSharedPreferences(getApplicationContext());
			if(ui.getUserId()==0)
			{//û�е�¼��ת����¼����
				//handler.sendEmptyMessage(0);
				String content="���ȵ�¼�Ѿ�ʧЧ�������µ�¼"; //֪ͨ������
				Intent notificationIntent = new Intent(context,LoginActivity.class); //�����֪ͨ��Ҫ��ת��Activity
				notifyMsg(content,notificationIntent,0,false);
				return;
			}
			String serverIp=AppConstant.UrlStrs.IP_MESSAGE_SERVER;
			int serverPort=AppConstant.UrlStrs.PORT_MESSAGE_SERVER;
			int userType=0;//����ͷ����Э����0��ʾ������Ա��1��ʾ�˿�
			minaClient=new MinaClient(serverIp, serverPort, 30000, "UTF-8", "\n", "\n", "phone123","sn123",true,ui.userId, userType, new AnalyzeHelper() {
				
				@Override
				public void analyze(String dataStr) {
					// TODO Auto-generated method stub
					//System.out.println(dataStr);
					DataOfMessageAnalyzeHelper analyzeHelper=new DataOfMessageAnalyzeHelper();
					analyzeHelper.analyze(dataStr);
					if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){
						currentMsg=analyzeHelper.messageInfo;
						System.out.println("currentMsg-->"+currentMsg);
						System.out.println("analyzeHelper.messageInfo-->"+analyzeHelper.messageInfo);
						if(currentMsg.type==MessageType.UPDATE_DISH_LIST.code){//Ҫ��ˢ�µ���б�����Ϣ
							Intent intent=new Intent(AppConstant.BroadcastActions.UPDATE_DISH_LIST);
							sendBroadcast(intent);
						}
						else if(currentMsg.type!=MessageType.ORDER_HANDLE_TYPE.code){//�Ƕ�����������Ϣ
							String content=currentMsg.content; //֪ͨ������
							Intent notificationIntent = new Intent(context,MessageActivity.class); //�����֪ͨ��Ҫ��ת��Activity
							notificationIntent.putExtra(AppConstant.IntentExtraName.MESSAGE_CONTENT, content);
							notifyMsg(content,notificationIntent,1,true);
							Intent intent=new Intent(AppConstant.BroadcastActions.UPDATE_MSG_VIEW);
							intent.putExtra(AppConstant.IntentExtraName.MESSAGE_CONTENT, content);
							sendBroadcast(intent);
						}else{
							//����Ϣд������,�������㲥
							DatabaseHelper dh=new DatabaseHelper(getApplicationContext());
							if(dh.insert(currentMsg)){
								Intent intent=new Intent(AppConstant.BroadcastActions.UPDATE_MSG_VIEW);
								sendBroadcast(intent);
								//��֪ͨ��֪ͨ
								notifyMsg();
							}
						}
					}
				}
			});
			minaClient.connect();
			System.out.println("recMsg done");
//			try {
//				Thread.sleep(20000);//����20�����½�����Ϣ
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//ͨ�����ؼ���û��Ƿ��¼
//			StaticData sd=StaticData.getInstance();
//			UserInfo ui=sd.getUi();
//			if(ui==null||ui.getUserId()==0)
//			{//û�е�¼��ת����¼����
//				Toast.makeText(getApplicationContext(), "��û�е�¼�����ȵ�¼", Toast.LENGTH_LONG).show();
//				continue;
//			}
//			
//			String url_s=AppConstant.UrlStrs.URL_GET_MESSAGE;
//			ParamCollect pc=new ParamCollect();
//			if(currentMsg!=null){
//				pc.addOrSetParam("msgId", currentMsg.id+"");
//			}
//			
//			HttpClientHelper httpConnHelper=HttpClientHelper.getInstance();
//			String responseStr="";
//			if(!isActive)
//				return;
//			responseStr=httpConnHelper.getResponseStr(url_s, pc.paramList);//getString(R.string.urlOrderQuery)
//			System.out.println(responseStr);
//			if(httpConnHelper.getResultCode()==AppConstant.VisitServerResultCode.RESULT_CODE_OK)
//			{
//				//�����ӷ��������ص��ַ���
//				if(!isActive)
//					return;
//				DataOfMessageAnalyzeHelper analyzeHelper=new DataOfMessageAnalyzeHelper();
//				analyzeHelper.analyze(responseStr);
//				if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){
//					currentMsg=analyzeHelper.messageInfo;
//					notifyMsg();
//				}
//			}
			
			
		}
	}
	private void notifyMsg(){
		String content=currentMsg.content; //֪ͨ������
		//Intent notificationIntent = new Intent(context,OrderDetailActivity.class); //�����֪ͨ��Ҫ��ת��Activity
		Intent notificationIntent = new Intent(context,MessageListActivity.class); //�����֪ͨ��Ҫ��ת��Activity
		notificationIntent.putExtra(AppConstant.IntentExtraName.ORDER_ID, currentMsg.orderId);
		//notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		notifyMsg(content,notificationIntent,NotifyId.getNId(currentMsg.orderId,currentMsg.type),false);
		
	}
	private void notifyMsg(String content,Intent intent,int nId,boolean canclear){
		//��ʾһ��֪ͨ
		//����һ��NotificationManager������

		String ns = Context.NOTIFICATION_SERVICE;

		NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);

		//����Notification�ĸ�������

		int icon = android.R.drawable.stat_notify_chat; //֪ͨͼ��

		CharSequence tickerText = "������Ϣ"; //״̬����ʾ��֪ͨ�ı���ʾ

		long when = System.currentTimeMillis(); //֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ

		//����������Գ�ʼ��Nofification

		Notification notification = new Notification(icon,tickerText,when);
		notification.defaults |=Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		if(!canclear)
			notification.flags |= Notification.FLAG_NO_CLEAR; //�����ڵ����֪ͨ���е�"���֪ͨ"�󣬴�֪ͨ�����
		/*

		* ��������

		* notification.defaults |=Notification.DEFAULT_SOUND;

		* ����ʹ�����¼��ַ�ʽ

		* notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");

		* notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");

		* �����Ҫ�����������ظ�ֱ���û���֪ͨ������Ӧ���������notification��flags�ֶ�����"FLAG_INSISTENT"

		* ���notification��defaults�ֶΰ�����"DEFAULT_SOUND"���ԣ���������Խ�����sound�ֶ��ж��������

		*/

		/*

		* ������

		* notification.defaults |= Notification.DEFAULT_VIBRATE;

		* ���߿��Զ����Լ�����ģʽ��

		* long[] vibrate = {0,100,200,300}; //0�����ʼ�񶯣���100�����ֹͣ���ٹ�200������ٴ���300����

		* notification.vibrate = vibrate;

		* long������Զ������Ҫ���κγ���

		* ���notification��defaults�ֶΰ�����"DEFAULT_VIBRATE",��������Խ�����vibrate�ֶ��ж������

		*/

		/*

		* ����LED������

		* notification.defaults |= Notification.DEFAULT_LIGHTS;

		* ���߿����Լ���LED����ģʽ:

		* notification.ledARGB = 0xff00ff00;

		* notification.ledOnMS = 300; //����ʱ��

		* notification.ledOffMS = 1000; //���ʱ��

		* notification.flags |= Notification.FLAG_SHOW_LIGHTS;

		*/

		/*

		* �������������

		* notification.flags |= FLAG_AUTO_CANCEL; //��֪ͨ���ϵ����֪ͨ���Զ������֪ͨ

		* notification.flags |= FLAG_INSISTENT; //�ظ�����������ֱ���û���Ӧ��֪ͨ

		* notification.flags |= FLAG_ONGOING_EVENT; //����֪ͨ�ŵ�֪ͨ����"Ongoing"��"��������"����

		* notification.flags |= FLAG_NO_CLEAR; //�����ڵ����֪ͨ���е�"���֪ͨ"�󣬴�֪ͨ�������

		* //������FLAG_ONGOING_EVENTһ��ʹ��

		* notification.number = 1; //number�ֶα�ʾ��֪ͨ�����ĵ�ǰ�¼�����������������״̬��ͼ��Ķ���

		* //���Ҫʹ�ô��ֶΣ������1��ʼ

		* notification.iconLevel = ; //

		*/
		System.out.println("currentMsg-->"+currentMsg);

		//����֪ͨ���¼���Ϣ

		//Context context = getApplicationContext(); //������

		CharSequence contentTitle = "������Ϣ"; //֪ͨ������

		//CharSequence contentText = currentMsg.content; //֪ͨ������
		CharSequence contentText = content; //֪ͨ������
//		if(currentMsg.orderId!=0){
//
//			Intent notificationIntent = new Intent(this,OrderDetailActivity.class); //�����֪ͨ��Ҫ��ת��Activity
//			notificationIntent.putExtra(AppConstant.IntentExtraName.ORDER_ID, currentMsg.orderId);
//			
//			PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//	
//			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
//		}
			PendingIntent contentIntent = PendingIntent.getActivity(context,nId,intent,PendingIntent.FLAG_ONE_SHOT);
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			
		//��Notification���ݸ�NotificationManager

		mNotificationManager.notify(nId,notification);
	}
}