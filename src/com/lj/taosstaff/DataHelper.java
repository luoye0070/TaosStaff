package com.lj.taosstaff;

import com.lj.taosstaff.common.DESUtil;
import com.lj.taosstaff.constant.AppConstant;
import com.lj.taosstaff.model.UserInfo;

import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public final class DataHelper {
	protected WebView dwAy_gsWv;
	protected UserInfo userInfo;
	protected Handler handler=null;
	public DataHelper(WebView dwAy_gsWv,UserInfo userInfo){
		this.dwAy_gsWv=dwAy_gsWv;
		this.userInfo=userInfo;
		handler=new Handler() {  
	          public void handleMessage(Message msg) {   
	               if(msg.what==0) {
	            	   String userName=DataHelper.this.userInfo.username;
		           		String passWord="";
		           		try {
		           			passWord = DESUtil.decryptDES(DataHelper.this.userInfo.getPassword(),AppConstant.AppSafety.USER_SECRET_KEY);
		           		} catch (Exception e) {
		           			// TODO Auto-generated catch block
		           			e.printStackTrace();
		           		}//userInfo.password;
		           		DataHelper.this.dwAy_gsWv.loadUrl("javascript:autoLogin('"+userName+"','"+passWord+"')");
	               }   
	               super.handleMessage(msg);   
	          }   
	     };
	}
	@JavascriptInterface
	public void autoLogin() {
		System.out.println("autoLogin");
		handler.sendEmptyMessage(0);
		//dwAy_gsWv.loadUrl("javascript:autoLogin('"+userName+"','"+passWord+"')");
	}
}