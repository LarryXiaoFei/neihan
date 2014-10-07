package com.larry.neihan.fragments;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.larry.neihan.R;
import com.larry.neihan.adapters.CommentAdapter;
import com.larry.neihan.bean.Comment;
import com.larry.neihan.bean.CommentList;
import com.larry.neihan.bean.TextEntity;
import com.larry.neihan.bean.UserEntity;
import com.larry.neihan.client.ClientAPI;
import com.larry.neihan.test.TestActivity;

import android.R.integer;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailContentFragment extends Fragment implements OnTouchListener,
		Listener<String> {

	private TextEntity entity;
	private ScrollView scrollView;
	private TextView txtHotCommentCount;
	private TextView txtRecentCommentCount;
	private int offset;

	public DetailContentFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (entity == null) {
			Bundle arguments = getArguments();
			Serializable serializable = arguments.getSerializable("entity");

			if (serializable instanceof TextEntity) {
				entity = (TextEntity) serializable;
			}
		}

		queue = Volley.newRequestQueue(getActivity());
	}

	private CommentAdapter hotAdapter;

	private CommentAdapter recentAdapter;

	private List<Comment> hotComments;

	private List<Comment> recentComments;

	private RequestQueue queue;
	private boolean hasMore;
	private long groupId;

	private LinearLayout scrollContent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO
		View ret = inflater.inflate(R.layout.fragment_detail_content,
				container, false);

		scrollView = (ScrollView) ret.findViewById(R.id.detail_scroll);

		scrollView.setOnTouchListener(this);

		scrollContent = (LinearLayout) ret.findViewById(R.id.scroll_content);
		// if(entity!=null)
		// {
		// UserEntity user = entity.getUser();
		// if(user!=null)
		// {
		// String avatarUrl = user.getAvatarUrl();
		// String nick = user.getName();
		//
		// TextView txtNick = (TextView)
		// ret.findViewById(R.id.comment_profile_nick);
		//
		// }
		// }

		setEssayContent(ret);

		// 2、设置图片的数据

		// TODO 需要加载各种图片数据

		txtHotCommentCount = (TextView) ret
				.findViewById(R.id.txt_hot_comments_count);

		ListView hotCommentListView = (ListView) ret
				.findViewById(R.id.hot_comments_list);
		hotComments = new LinkedList<Comment>();
		hotAdapter = new CommentAdapter(getActivity(), hotComments);
		hotCommentListView.setAdapter(hotAdapter);

		txtRecentCommentCount = (TextView) ret
				.findViewById(R.id.txt_recent_comments_count);
		ListView recentCommentListView = (ListView) ret
				.findViewById(R.id.recent_comments_list);
		recentComments = new LinkedList<Comment>();
		recentAdapter = new CommentAdapter(getActivity(), recentComments);
		recentCommentListView.setAdapter(recentAdapter);

		groupId = entity.getGroupId();

		ClientAPI.getComments(queue, groupId, 0, this);

		return ret;
	}

	private void setEssayContent(View ret) {

		TextView btnGifPlay = (TextView) ret.findViewById(R.id.btnGifPlay);
		ImageButton btnShare = (ImageButton) ret.findViewById(R.id.item_share);

		CheckBox chbBuryCount = (CheckBox) ret
				.findViewById(R.id.item_bury_count);
		CheckBox chbDiggCount = (CheckBox) ret
				.findViewById(R.id.item_favorite_count);

		GifImageView gifImageView = (GifImageView) ret
				.findViewById(R.id.gifImageView1);

		ImageView imgProfileImage = (ImageView) ret
				.findViewById(R.id.item_profile_image);

		ProgressBar pbDownloadProgress = (ProgressBar) ret
				.findViewById(R.id.item_image_downlode_progress);

		TextView txtCommentCount = (TextView) ret
				.findViewById(R.id.item_comment_count);

		TextView txtContent = (TextView) ret.findViewById(R.id.item_content);
		TextView txtProfileNick = (TextView) ret
				.findViewById(R.id.item_profile_nick);

		// 1、先设置文本内容的数据
		UserEntity user = entity.getUser();
		String nick = "";
		if (user != null) {
			nick = user.getName();
		}
		txtProfileNick.setText(nick);

		String content = entity.getContent();
		txtContent.setText(content);

		int diggCount = entity.getDiggCount();
		chbDiggCount.setText(Integer.toString(diggCount));
		/**
		 * userDigg不等于1的时候，表示用户
		 */
		int userDigg = entity.getUserDigg();
		// chbDiggCount.setEnabled(userDigg==1？false:true);
		/**
		 * 如果digg是1的话，代表用户已经赞，那么chbDiggCount必须禁用
		 */
		chbDiggCount.setEnabled(userDigg != 1);

		int buryCount = entity.getBuryCount();
		chbBuryCount.setText(Integer.toString(buryCount));
		/**
		 * 如果userBury是1的话，代表用户已经赞，那么chbBuryCount必须禁用
		 */
		int userBury = entity.getUserBury();
		chbBuryCount.setEnabled(userBury != 1);

		int commentCount = entity.getCommentCount();
		txtCommentCount.setText(Integer.toString(commentCount));
	}

	private boolean hasMove = false;

	/**
	 * 处理滚动ScrollView的触摸事件，用于在ScrollView滚动到最下面的时候，自动加载数据
	 */
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		boolean bret = false;
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			bret = true;
			hasMove = false;
		} else if (action == MotionEvent.ACTION_MOVE) {
			hasMove = true;
		} else if (action == MotionEvent.ACTION_UP) {
			if (hasMove) {

				int sy = scrollView.getScrollY();

				int mh = scrollView.getMeasuredHeight();

				int ch = scrollContent.getHeight();
				if (sy + mh >= ch) {
					// TODO 进行评论的分页加载

					ClientAPI.getComments(queue, groupId, 0, this);
				}
			}
		}
		return bret;
	}

	/**
	 * 
	 */
	@Override
	public void onResponse(String arg0) {
		try {
			JSONObject json = new JSONObject(arg0);
			arg0 = json.toString(4);
			// 解析获取到的评论列表
			CommentList commentList = new CommentList();
			Log.i("TestActivity", "---CommentList--->>" + arg0);
			commentList.parseJson(json);

			hasMore = json.getBoolean("has_more");

			List<Comment> topComments = commentList.getTopComments();

			List<Comment> retComments = commentList.getRecentComments();

			if (topComments != null) {
				hotComments.addAll(topComments);
				hotAdapter.notifyDataSetChanged();
			}

			if (retComments != null) {
				recentComments.addAll(retComments);
				recentAdapter.notifyDataSetChanged();
			}

			// 判断是否有的评论可以加载
			if (!hasMore) {
				// Toast.makeText(TestActivity.this, "无更多评论可加载!!!", 1).show();
			}

			// 评论列表包含两组数据，一个是热门评论列表，一个是新鲜评论
			// 两个都可能为空
			// 热门评论列表（可能为空，第一次 offset为0时可能有数据）
			// 新鲜评论，可能有数据

			// Log.i("TestActivity",
			// "--group_id:"+commentList.getGroupId()+"has_more:"+commentList.isHasMore()+"count:"+commentList.getTotalNumber());
			// TODO 直接把 CommentEntityList提交给adapter这样可以进行是否还有内容的判断 利用adapter更新数据

			// 分页标识 要求服务器每次返回20条 通过hasMore来进行判断是否还需要分页
			offset = offset + 20;

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
