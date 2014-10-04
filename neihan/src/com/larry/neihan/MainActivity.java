package com.larry.neihan;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/**
	 * 需要使用到的第三方的类库：
	 * 1、PullToRefresh
	 * 2、Gif动画的播放：（去确定到底使用什么库）
	 * 	Movie类 Android自带的API。它可以把gif文件的每一帧解码出来。
	 *  使用第三方的库:GifView或者其他gif文件的播放控件
	 *  使用WebView进行gif的播放
	 *  
	 * 3、文件的上传：要使用的是HttpClient+HttpMime
	 * 
	 * 4、Volley的类库。
	 * 5、第三方分享类库：shareSDK(待解决)
	 * 
	 */
}
