package com.lj.taosstaff;

import com.lj.taosstaff.broadcast.BroadcastReceiverCustom;
import com.lj.taosstaff.common.OptionsMenuProcess;
import com.lj.taosstaff.LoginActivity;
import com.lj.taosstaff.common.ActivityCallBackInterface;
import com.lj.taosstaff.common.AcvivityLoginGoto;
import com.lj.taosstaff.common.StaticData;
import com.lj.taosstaff.dataload.AfterAction;
import com.lj.taosstaff.dataload.DataLoadHelper;
import com.lj.taosstaff.internet.ParamCollect;
import com.lj.taosstaff.model.InternetEnv;
import com.lj.taosstaff.model.UserInfo;
import com.lj.taosstaff.string_analysis.AnalyzeHelper;
import com.lj.taosstaff.constant.AppConstant;
import com.lj.taosstaff.include.MainNavbarMenu;
import com.lj.taosstaff.common.ActivityManager;
import com.lj.taosstaff.R;
import com.lj.taosstaff.R.layout;
import com.lj.taosstaff.R.menu;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("JavascriptInterface")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DishListActivity extends Activity implements ActivityCallBackInterface {
	ActivityManager am=null;//activity管理类
	//MainSearchBar msb=null;//搜索栏
	MainNavbarMenu mnm=null;//导航菜单管理类
	
	WebView mainAy_gsWv = null;
	LinearLayout webLoadingLL = null;
	ProgressBar loadProgress = null;
	ProgressBar loadProgressH = null;
	TextView mainAy_title = null;
	static String baseUrlStr = "/staff/dishList";
	static String urlStr = "/staff/dishList";
	
	BroadcastReceiverCustom brc=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*将当前activity添加到activitymanager中*/
        //am=ActivityManager.getInstance();
        //am.addActivity(this);
        //设置布局
		setContentView(R.layout.activity_main);
		
		baseUrlStr = new InternetEnv().getBaseUrl(getApplicationContext())+"/staff/dishList";
		urlStr = new InternetEnv().getBaseUrl(getApplicationContext())+"/staff/dishList";
		
		//初始化一个搜索栏
        //msb=new MainSearchBar(this);
        //初始化一个导航菜单
        mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.DishListActivity);
		initInstance();
		
		//注册广播监听器
		brc=new BroadcastReceiverCustom(this);
		registerReceiver(brc, new IntentFilter(AppConstant.BroadcastActions.UPDATE_DISH_LIST));
	}
	
	/***************************************
	 * 初始化操作
	 ****************************************/
	private void initInstance() {
		mainAy_gsWv = (WebView) findViewById(R.id.mainAy_gsWv);
		webLoadingLL = (LinearLayout) findViewById(R.id.webLoadingLL);
		loadProgress = (ProgressBar) findViewById(R.id.loadProgress);
		loadProgressH = (ProgressBar) findViewById(R.id.loadProgressH);
		mainAy_title = (TextView) findViewById(R.id.mainAy_title);
		mainAy_title.setText("餐萌点菜");

		// 设置能缩放
		mainAy_gsWv.getSettings().setSupportZoom(true);
		mainAy_gsWv.getSettings().setBuiltInZoomControls(true);
		mainAy_gsWv.getSettings().setDisplayZoomControls(false);
		//mainAy_gsWv.getSettings().setLoadWithOverviewMode(true);

		// 设置能执行js
		mainAy_gsWv.getSettings().setJavaScriptEnabled(true);

		// 设置加载过程
		final Activity activity = this;
		mainAy_gsWv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different
				// scales.
				// The progress meter will automatically disappear when we reach
				// 100%
				loadProgress.setProgress(progress);
				loadProgressH.setProgress(progress);
				System.out.println("progress:" + progress);
				if (progress >= 100) {
					loadProgress.setVisibility(View.GONE);
					webLoadingLL.setVisibility(View.GONE);
					mainAy_gsWv.setVisibility(View.VISIBLE);
				}
			}
		});
		mainAy_gsWv.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(activity, "Oh no! " + description,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				urlStr = url;
				System.out.println("onPageFinished-->" + urlStr);
				// if(view.getTitle()!=null)
				// mainAy_title.setText("饭店预订--"+view.getTitle());
			}

		});

		// 加载网页
		System.out.println(urlStr);
		mainAy_gsWv.loadUrl(urlStr);
		
		UserInfo userInfo=new UserInfo();
		userInfo.readFromSharedPreferences(getApplicationContext());
		mainAy_gsWv.addJavascriptInterface(new DataHelper(mainAy_gsWv,userInfo),"dataHelper");
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
    	int menuId=item.getItemId();
    	OptionsMenuProcess omp=new OptionsMenuProcess(this);
    	omp.mainOptionsDosome(menuId, this);
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void loginSuccessCallBack() {//登录成功后所做的事情
		// TODO Auto-generated method stub
		if(mnm!=null)
			mnm.refreshView();
		if(mainAy_gsWv!=null){
			mainAy_gsWv.loadUrl(urlStr);
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(AcvivityLoginGoto.getAcbi()==this){
			AcvivityLoginGoto.setAcbi(null);
		}
		super.onDestroy();
	}

	public void refreshData() {
		// TODO Auto-generated method stub
		if(mainAy_gsWv!=null){
			mainAy_gsWv.loadUrl(urlStr);
		}
	}
}
