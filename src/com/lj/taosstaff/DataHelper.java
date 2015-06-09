package com.lj.taosstaff;

import com.lj.taosstaff.common.DESUtil;
import com.lj.taosstaff.constant.AppConstant;
import com.lj.taosstaff.model.UserInfo;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public final class DataHelper {
	private WebView dwAy_gsWv;
	private UserInfo userInfo;
	public DataHelper(WebView dwAy_gsWv,UserInfo userInfo){
		this.dwAy_gsWv=dwAy_gsWv;
		this.userInfo=userInfo;
	}
	@JavascriptInterface
	public void autoLogin() {
		System.out.println("autoLogin");
		String userName=userInfo.username;
		String passWord="";
		try {
			passWord = DESUtil.decryptDES(userInfo.getPassword(),AppConstant.AppSafety.USER_SECRET_KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//userInfo.password;
		dwAy_gsWv.loadUrl("javascript:autoLogin('"+userName+"','"+passWord+"')");
	}
}