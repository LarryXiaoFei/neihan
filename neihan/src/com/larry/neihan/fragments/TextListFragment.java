package com.larry.neihan.fragments;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.larry.neihan.R;

public class TextListFragment extends Fragment implements OnClickListener,
		OnScrollListener, OnRefreshListener2<ListView> {

	private View quickTools;
	private TextView textNotify;
	private View header;

	public TextListFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
		refreshListView
				.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
		ListView listView = refreshListView.getRefreshableView();

		List<String> strings = new LinkedList<String>();

		for (int i = 0; i < 15; i++) {
			strings.add("java" + i);
		}

		header = inflater.inflate(R.layout.textlist_header_tools, listView,
				false);
		listView.addHeaderView(header);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, strings);

		listView.setAdapter(adapter);

		listView.setOnScrollListener(this);

		//
		View quickPublish = header.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);

		View quickReview = header.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);

		// 获取工具条(发布和审核)
		quickTools = view.findViewById(R.id.textlist_quick_tools);
		quickTools.setVisibility(View.INVISIBLE);

		// 设置悬浮的工具条，两个命令的事件
		quickPublish = quickTools.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);

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
		// TODO
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
			// 证明现在的移动方向是向上移动
			if (quickTools != null) {
				quickTools.setVisibility(View.INVISIBLE);
			}
		} else if (offset > 0) {
			if (quickTools != null) {
				quickTools.setVisibility(View.VISIBLE);
			}
		}
		lastIndex = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	// ------------------------------------------------------------------
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	// ------------------------------------------------------------
	// 列表下拉刷新上拉加载
	/**
	 * 从上向下拉动列表，那么就要进行加载新的数据操作
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}

	/**
	 * 从下往上拉，这个就需要考虑是否加载旧的数据
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}
}
