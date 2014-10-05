package com.larry.neihan.client;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.larry.neihan.test.TestActivity;

/**
 * 所有和服务器接口对接的方法全部在这个类中
 * @author aaa
 * 
 */
public class ClientAPI {

	/**
	 * @param categoryType
	 *            获取的参数类型
	 * @param itemCount
	 *            服务器一次传回来的条目数
	 * @param responseListener
	 *            用于获取段子列表的回调接口，任何调用getList()的方法的时候，此参数用于更新段子列表
	 * @param RequesetQueue Volley请求的队列
	 * @see TestActivity#CATEGORY_TEXT
	 * @see TestActivity#CATEGORY_IMAGE
	 */
	public static void getList(RequestQueue queue,int categoryType, int itemCount,
			Response.Listener<String> responseListener) {
		// TODO 测试内涵段子列表接口，获取文本列表
		
	
		String CATEGORY_LIST_API = "http://ic.snssdk.com/2/essay/zone/category/data/";
	
		// 根据类型获取不同的数据
		String categoryParam = "category_id=" + categoryType;
	
		String countParam = "count=" + itemCount;
	
		String deviceType = "device_type=KFTT";
		String openUDID = "openudid=b90ca6a3a19a78d6";
	
		// Http的请求参数跟顺序是没有关系的。
		String url = CATEGORY_LIST_API
				+ "?"
				+ categoryParam
				+ "&"
				+ countParam
				+ "&"
				+ deviceType
				+ "&"
				+ openUDID
				+ "&level=6&iid=2337593504&device_id=2757969807&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&os_api=15&os_version=4.0.3";
	
		queue.add(new StringRequest(Request.Method.GET, url, responseListener,
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.i("TestActivity",
								"--VolleyError->>" + arg0.toString());
					}
				}));
	
		// queue.start();
	}

}
