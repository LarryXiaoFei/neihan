package com.larry.neihan.fragments;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.larry.neihan.R;
import com.larry.neihan.activities.EssayDetailActivity;
import com.larry.neihan.adapters.EssayAdapter;
import com.larry.neihan.bean.DataStore;
import com.larry.neihan.bean.EntityList;
import com.larry.neihan.bean.TextEntity;
import com.larry.neihan.client.ClientAPI;

/**
 * 1、列表界面，第一次启动数据为空的时候，自动加载数据 2、只要列表没有数据，进入这个界面的时候，就尝试加载数据 3、只要有数据就不进行数据的加载
 * 4、进入这个界面，并且有数据的情况下，尝试检查新信息的个数
 * 
 * @author aaa 如果我们对成员变量不进行数据清空的话，它不会被清空
 */
public class TextListFragment extends Fragment implements OnClickListener,
		OnScrollListener, OnRefreshListener2<ListView>, OnItemClickListener {

	private View quickTools;
	private TextView textNotify;
	private View header;
	private EssayAdapter adapter;
	// private List<TextEntity> entities;

	/**
	 * 分类ID,1 代表文本
	 */
	public static final int CATEGORY_TEXT = 1;
	/**
	 * 分类ID,2 代表图片
	 */
	public static final int CATEGORY_IMAGE = 2;
	/**
	 * Volley的请求队列
	 */
	private RequestQueue queue;
	/**
	 * 最后请求的时间，用于进行段子列表的分页使用的。
	 */
	private long lastTime = 0L;

	/**
	 * 请求分类的id
	 */
	private int requestCategory = CATEGORY_TEXT;

	public TextListFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 创建请求队列
		if (queue == null) {
			queue = Volley.newRequestQueue(getActivity());
		}
		// ClientAPI.getList(queue, categoryType, itemCount, minTime,
		// responseListener);

		if (savedInstanceState != null) {

			lastTime = savedInstanceState.getLong("lastTime");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_textlist, container,
				false);

		// 获取标题控件，增加点击，进行新消息悬浮框显示功能
		View titleView = view.findViewById(R.id.textlist_title);
		titleView.setOnClickListener(this);

		// TODO 获取listview并且设置数据
		PullToRefreshListView refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.textlist_listview);

		// 设置上拉与下拉的事件监听
		// OnRefreshListener2<ListView>
		refreshListView.setOnRefreshListener(this);
		// 设置下拉和上拉的模式
		refreshListView
				.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
		ListView listView = refreshListView.getRefreshableView();

		header = inflater.inflate(R.layout.textlist_header_tools, listView,
				false);
		// 将工具条添加到ListView的Header部分。
		listView.addHeaderView(header);

		// 判断entities是否为空，为空的时候再去创建adapter的数据List<TextEntity>

		List<TextEntity> entities = DataStore.getInstance().getTextEntities();

		/*
		 * if (entities == null) { entities = new LinkedList<TextEntity>(); }
		 */

		// 创建适配器
		adapter = new EssayAdapter(getActivity(), entities);

		// 给listView设置适配器
		listView.setAdapter(adapter);
		adapter.setListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v instanceof TextView) {
					String string = (String) v.getTag();
					int position = Integer.parseInt(string);

					if (string != null) {
						Intent intent = new Intent(getActivity(),
								EssayDetailActivity.class);
						intent.putExtra("currentEssayPostion", position);
						intent.putExtra("category", requestCategory);
						startActivity(intent);
					}
				}
			}
		});

		// 给listView添加滚动事件的监听
		listView.setOnScrollListener(this);
		listView.setOnItemClickListener(this);
		// 获取发布的View对象
		View quickPublish = header.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);
		// 获取审核的View对象
		View quickReview = header.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);

		// 获取工具条(发布和审核)
		quickTools = view.findViewById(R.id.textlist_quick_tools);
		quickTools.setVisibility(View.INVISIBLE);

		// 设置悬浮的工具条，两个命令的事件
		quickPublish = quickTools.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);
		// 给工具条设置监听事件
		quickReview = quickTools.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);

		// 获取 新条目通知控件，每次有新数据要显示，过一段时间隐藏掉。
		textNotify = (TextView) view.findViewById(R.id.textlist_new_notify);
		textNotify.setVisibility(View.INVISIBLE);

		return view;
	}

	//
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			if (what == 1) {
				// TODO 1、表示有新消息提醒,3秒之后隐藏
				textNotify.setVisibility(View.INVISIBLE);
			}
		}
	};

	@Override
	public void onClick(View view) {
		// TODO 当点击左上角的段子的时候，就显示提示信息，3秒之后，自动隐藏。这个隐藏的处理是发送一个Message的消息
		// 在handler的handMessage的方法中进行处理
		int id = view.getId();
		switch (id) {
		case R.id.textlist_title:
			textNotify.setVisibility(View.VISIBLE);
			handler.sendEmptyMessageDelayed(1, 3000);
			break;
		default:
			break;
		}

	}

	// -------------------------------------------------------------------
	// 列表滚动显示工具条

	private int lastIndex = 0;

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO
		int offset = lastIndex - firstVisibleItem;

		if (offset < 0 || firstVisibleItem == 0) {
			// 证明现在的移动方向是向上移动，向上移动的时候，把工具条隐藏掉
			if (quickTools != null) {
				quickTools.setVisibility(View.INVISIBLE);
			}
			// 判断listView下拉的时候，就将工具条显示出来
		} else if (offset > 0) {
			if (quickTools != null) {
				quickTools.setVisibility(View.VISIBLE);
			}
		}
		// 每次滚动的时候，对索引进行判断
		lastIndex = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO

	}

	// ------------------------------------------------------------------

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putLong("lastTime", lastTime);
	}

	@Override
	public void onPause() {
		// TODO
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		// TODO
		super.onDestroyView();

		this.adapter = null;

		this.header = null;

		this.quickTools = null;

		this.textNotify = null;
	}

	// ------------------------------------------------------------
	// 列表下拉刷新上拉加载

	/**
	 * 列表网络获取回调部分,这个方法是在Volley联网响应返回的时候调用，它在主线程中被调用，他是在主线程 中执行的，因此可以直接更新UI，
	 * 
	 * @param arg0
	 *            列表json数据字符串
	 */
	public void ListonResponse(String arg0) {
		try {
			JSONObject json = new JSONObject(arg0);
			arg0 = json.toString(4);
			Log.d("MainActivity","--json--"+arg0);
			
			// 获取根节点下的data
			JSONObject obj = json.getJSONObject("data");
			// 创建EntityList对象，用于保存，网络段子的数据
			EntityList entityList = new EntityList();
			// 这个方法是解析JSON的方法，其中包含支持图片文本广告的解析，（解析之后，就对EntityList进行了赋值）
			entityList.parseJson(obj);
			// 判断是否有新的段子
			if (entityList.isHasMore()) {
				lastTime = entityList.getMinTime();// 获取更新时间标识
			} else {
				// 如果没有新的段子，就获取tip的提醒信息，给用户一个提示，
				String tip = entityList.getTip();
			}
			// 获取段子内容列表
			// TODO 把entities数据集合体 传递给ListView大adapter更新UI
			// 创建一个临时的TextEntity的集合，将解析下来的网络数据保存在它里
			List<TextEntity> ets = entityList.getEntities();

			// 判断是否获取的网络数据
			if (ets != null) {
				// 判断网络数据是否为空
				if (!ets.isEmpty()) {

					/**
					 * 把ets中内容按照迭代器的顺序进行添加，这个需要验证一下
					 */
					DataStore.getInstance().addTextEntities(ets);

					// entities.addAll(0, ets);
					// 手动添加
					// 把object添加到指定的location位置，
					// 原有的location以及以后的内容向后移动
					/**
					 * int len = entities.size(); for (int index = len - 1;
					 * index > 0; index--) { entities.add(0, ets.get(index)); }
					 */
					// （提示）重新填充数据
					adapter.notifyDataSetChanged();
				} else {
					// TODO 没有更多的数据了，需要提示一下，
				}
			} else {
				// TODO 没有获取到网络数据，可能是数据解析错误、或者网络错误，需要提示一下
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从上向下拉动列表，那么就要进行加载新的数据操作
	 */
	@Override
	public void onPullDownToRefresh(
			final PullToRefreshBase<ListView> refreshView) {
		// TODO 加载新数据
		ClientAPI.getList(queue, requestCategory, 30, lastTime,
				new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						refreshView.onRefreshComplete();
						ListonResponse(arg0);
					}
				});
	}

	/**
	 * 从下往上拉，这个就需要考虑是否加载旧的数据
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO

	}

	// ======================ListView的setOnItemClickListener的接口事件============================
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		arg2--;
		//
		// Intent intent = new Intent(getActivity(), EssayDetailActivity.class);
		// intent.putExtra("currentEssayPostion", arg2);
		// intent.putExtra("category", requestCategory);
	}
}
