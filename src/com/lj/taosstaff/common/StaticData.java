package com.lj.taosstaff.common;

import java.util.ArrayList;

import com.lj.taosstaff.model.UserInfo;


/************��̬����*************/
public class StaticData {
	
	private static StaticData sd=null;
	public static StaticData getInstance()
	{
		if(sd==null)
		{
			sd=new StaticData();
		}
		return sd;
	}
	/***************************�û�����***********************************/
	/*****************
	 * ��ȡ�û���Ϣ 
	 **********************/
	public UserInfo getUi() {
		return ui;
	}
	/********************
	 * �����û���Ϣ
	 * ***********************/
	public void setUi(UserInfo ui) {
		this.ui = ui;
	}
	private UserInfo ui=null; 	
	
}
