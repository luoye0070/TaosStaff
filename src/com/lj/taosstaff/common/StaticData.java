package com.lj.taosstaff.common;

import java.util.ArrayList;

import com.lj.taosstaff.model.UserInfo;


/************静态数据*************/
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
	/***************************用户数据***********************************/
	/*****************
	 * 获取用户信息 
	 **********************/
	public UserInfo getUi() {
		return ui;
	}
	/********************
	 * 设置用户信息
	 * ***********************/
	public void setUi(UserInfo ui) {
		this.ui = ui;
	}
	private UserInfo ui=null; 	
	
}
