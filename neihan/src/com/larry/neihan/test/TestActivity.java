package com.larry.neihan.test;


import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.larry.neihan.R;
import com.larry.neihan.bean.CommentList;
import com.larry.neihan.bean.EntityList;
import com.larry.neihan.bean.TextEntity;
import com.larry.neihan.client.ClientAPI;

public class TestActivity extends Activity implements Response.Listener<String> {
	/**
	 * 分类ID,1 代表文本
	 */
	public static final int CATEGORY_TEXT = 1;
	/**
	 * 分类ID,2 代表图片
	 */
	public static final int CATEGORY_IMAGE = 2;
	private RequestQueue queue;
	private long lastTime = 0L;
	private Button button;
	private int itemCount;
	
	private long groupID;
	private int offset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		itemCount = 30;
		queue = Volley.newRequestQueue(this);
		groupID = 3550036269L;
		offset = 0;
		

		// button=(Button) findViewById(R.id.button);
		// button.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ClientAPI.getList(queue, CATEGORY_TEXT, itemCount, lastTime,
		// TestActivity.this);
		// }
		// });
		button=(Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ClientAPI.getComments(queue,groupID,offset,TestActivity.this);
			};
		});
	}



	/**
	 * 列表网络获取回调部分
	 * 
	 * @param arg0
	 * 列表json数据字符串
	 */
	public void ListonResponse(String arg0) {

		try {
			JSONObject json = new JSONObject(arg0);
			arg0 = json.toString(4);
			Log.i("..............", arg0);
			// 获取根节点下的data
			JSONObject obj = json.getJSONObject("data");
			EntityList entityList = new EntityList();
			// 这个方法是解析JSON的方法，其中包含支持图片文本广告的解析
			entityList.parseJson(obj);
			Toast.makeText(TestActivity.this, entityList.isHasMore() + "", 1)
					.show();
			if (entityList.isHasMore()) {
				lastTime = entityList.getMinTime();// 获取更新时间标识
			} else {
				Toast.makeText(TestActivity.this, "休息一下，暂时无数据更新", 1).show();
			}
			// 获取段子内容列表
			List<TextEntity> entities = entityList.getEntities();
			// TODO 把entities数据集合体 传递给ListView大adapter更新UI
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onResponse(String arg0) {
		try {
			JSONObject json=new JSONObject(arg0);		
			arg0 = json.toString(4);
			//解析获取到的评论列表
			CommentList commentList =new CommentList();
			Log.i("TestActivity","---CommentList--->>" +arg0);
			
			//判断是否有的评论可以加载
			if(!json.getBoolean("has_more")){						
				Toast.makeText(TestActivity.this, "无更多评论可加载!!!", 1).show();				
			}
			
			//评论列表包含两组数据，一个是热门评论列表，一个是新鲜评论
			//两个都可能为空
			//热门评论列表（可能为空，第一次 offset为0时可能有数据）
			//新鲜评论，可能有数据
			commentList.parseJson(json);
			
			Log.i("TestActivity", "--group_id:"+commentList.getGroupId()+"has_more:"+commentList.isHasMore()+"count:"+commentList.getTotalNumber());
			//TODO 直接把 CommentEntityList提交给adapter这样可以进行是否还有内容的判断 利用adapter更新数据
			
			//分页标识 要求服务器每次返回20条 通过hasMore来进行判断是否还需要分页
			offset=offset+20;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
