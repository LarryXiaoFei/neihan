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
	 * ��Ҫʹ�õ��ĵ���������⣺
	 * 1��PullToRefresh
	 * 2��Gif�����Ĳ��ţ���ȥȷ������ʹ��ʲô�⣩
	 * 	Movie�� Android�Դ���API�������԰�gif�ļ���ÿһ֡���������
	 *  ʹ�õ������Ŀ�:GifView��������gif�ļ��Ĳ��ſؼ�
	 *  ʹ��WebView����gif�Ĳ���
	 *  
	 * 3���ļ����ϴ���Ҫʹ�õ���HttpClient+HttpMime
	 * 
	 * 4��Volley����⡣
	 * 5��������������⣺shareSDK(�����)
	 * 
	 */
}
