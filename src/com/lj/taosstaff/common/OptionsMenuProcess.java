package com.lj.taosstaff.common;

import com.lj.taosstaff.LoginActivity;
import com.lj.taosstaff.constant.AppConstant;
import com.lj.taosstaff.R;
import android.app.Activity;
import android.content.Intent;

public class OptionsMenuProcess {
	private Activity a=null;

	public OptionsMenuProcess(Activity a) {
		super();
		this.a = a;
	}
	
	public void mainOptionsDosome(int menuId,ActivityCallBackInterface acbi)
	{
		switch(menuId)
    	{
    	case R.id.menu_login:
			if(a instanceof ActivityCallBackInterface)
				AcvivityLoginGoto.setAcbi((ActivityCallBackInterface)a);
    		Intent loginIntent=new Intent();
			loginIntent.setClass(a, LoginActivity.class);
			loginIntent.putExtra(AppConstant.IntentExtraName.IN_LOGIN_FROM_BT, true);
			a.startActivity(loginIntent);
    		break;
    	case R.id.menu_refresh:
    		acbi.loginSuccessCallBack();
    		break;
    	case R.id.menu_exit:
    		ActivityManager.exitAppDlgShow(a);
    		break;
    	}
	}
}
