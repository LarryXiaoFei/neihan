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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/**
	 * 与服务器接口对接的项目，接口应该如何开发
	 * a、有没有登录的接口、注册接口
	 * b、有没有前提条件接口，例如获取配置信息的接口
	 * c、有没有鉴权接口，例如现在 常见的第三方开放的API接口，如新浪微博子类的OAuth授权机制的接口
	 * d、有没有关键列数据的接口：内涵段子的列表，以及内涵段子的评论，
	 * 
	 */
}
