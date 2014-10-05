package com.larry.neihan.test;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.larry.neihan.R;
import com.larry.neihan.bean.ImageEntity;
import com.larry.neihan.bean.ImageUrlList;
import com.larry.neihan.client.ClientAPI;

/**
 * 
 * @author aaa 这个文件是一个测试入口，所有的测试内容，都可以放在这里
 */
public class TestActivity extends Activity implements Response.Listener<String> {

	// 分类id 1代表文本
	public static final int CATEGORY_TEXT = 1;
	// 分类id 2代表图片
	public static final int CATEGORY_IMAGE = 2;
	private RequestQueue queue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		int itemCount = 30;
		
		queue = Volley.newRequestQueue(this);
		
		ClientAPI.getList(queue,CATEGORY_IMAGE, itemCount, this);
	}

	@Override
	public void onResponse(String arg0) {
		try {
			JSONObject json = new JSONObject(arg0);
			arg0 =  json.toString(4);
			System.out.println("List" +arg0);
			
			//获取根节点下的data对象，
			JSONObject obj = json.getJSONObject("data");
			
			//从data对象中获取名称为data的数组，它代表的是段子列表的数据
			JSONArray array = obj.getJSONArray("data");
			
			
			if(array.length()>0)
			{
				//遍历数组中的每一条图片段子的信息
				for(int i=0;i<array.length();i++)
				{
					JSONObject item = array.getJSONObject(i);
					ImageEntity imageEntity = new ImageEntity();
					imageEntity.parseJson(item);
					
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
