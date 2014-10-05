package com.larry.neihan.bean;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * 评论接口返回的 data：{}数据部分实体定义， 包含了 top_comments 和 recent_comments两个数组 JSON格式如下
 * 
 * <pre>
 * {
 * 	  "data":{
 * 		"top_comments":	[]
 * 		"recent_comments":[]	
 * 	}
 * }
 * </pre>
 * 
 * @author aaa
 * 
 */
public class CommentList {

	private List<Comment> topComments;

	private List<Comment> recentComments;
	
	//段子的id
	private long groupId;
	//总的评论数
	private int totalNumber;
	//是否还有评论
	private boolean hasMore;

	public void parseJson(JSONObject json) throws JSONException {

		if (json != null) {

			groupId = json.getLong("group_id");
			totalNumber = json.getInt("total_number");
			hasMore = json.getBoolean("has_more");

			JSONObject data = json.getJSONObject("data");
			
			//热门评论
			JSONArray tArray = data.optJSONArray("top_comments");

			if (tArray != null) {
				topComments = new LinkedList<Comment>();

				int lenth = tArray.length();
				if (lenth > 0) {
					for (int index = 0; index < lenth; index++) {
						JSONObject obj = tArray.getJSONObject(index);
						Comment comment = new Comment();
						comment.parseJson(obj);
						topComments.add(comment);
					}
				}
			}
			
			//最新评论
			JSONArray rArray = data.optJSONArray("recent_comments");
			if (rArray != null) {
				recentComments = new LinkedList<Comment>();
				int len = rArray.length();
				if (len > 0) {
					for (int index = 0; index < len; index++) {
						JSONObject obj = rArray.getJSONObject(index);
						Comment comment = new Comment();
						comment.parseJson(obj);
						recentComments.add(comment);
					}
				}
			}
		}
	}

	public List<Comment> getTopComments() {
		return topComments;
	}

	public List<Comment> getRecentComments() {
		return recentComments;
	}

	public long getGroupId() {
		return groupId;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public boolean isHasMore() {
		return hasMore;
	}

}
