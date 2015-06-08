package com.lj.taosstaff;

import java.util.ArrayList;
import java.util.HashMap;

import com.lj.taosstaff.R;
import com.lj.taosstaff.include.MainNavbarMenu;
import com.lj.taosstaff.adapter.ITBCListAdapter;
import com.lj.taosstaff.broadcast.BroadcastReceiverCustom;
import com.lj.taosstaff.common.ActivityManager;
import com.lj.taosstaff.constant.AppConstant;
import com.lj.taosstaff.model.MessageInfo;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MessageActivity extends Activity {
	ActivityManager am=null;//activity������
	//MainSearchBar msb=null;//������
	//MainNavbarMenu mnm=null;//�����˵�������
	
	TextView msgAy_content=null;//��Ϣ����
	String content=null;
	BroadcastReceiverCustom brc=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ȥ��������
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*����ǰactivity��ӵ�activitymanager��*/
        //am=ActivityManager.getInstance();
        //am.addActivity(this);
        //���ò���
		setContentView(R.layout.activity_message);
		
		//��ʼ��һ��������
        //msb=new MainSearchBar(this);
        //��ʼ��һ�������˵�
        //mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.OrderListActivity);
		msgAy_content=(TextView) findViewById(R.id.msgAy_content);
		Intent preIntent=getIntent();
		content=preIntent.getStringExtra(AppConstant.IntentExtraName.MESSAGE_CONTENT);
		if(content==null)
			content="�����Ѷ�ʧ";
		msgAy_content.setText(content);
        
        
      //ע��㲥������
		brc=new BroadcastReceiverCustom(this);
		registerReceiver(brc, new IntentFilter(AppConstant.BroadcastActions.UPDATE_MSG_VIEW));
	}
	public void refreshData(String content){
		this.content=content;
		if(this.content==null)
			this.content="�����Ѷ�ʧ";
		msgAy_content.setText(this.content);
	}
	@Override
	protected void onDestroy() {
		//am.removeActivity(this);
		
		unregisterReceiver(brc);
		super.onDestroy();
	}
}
