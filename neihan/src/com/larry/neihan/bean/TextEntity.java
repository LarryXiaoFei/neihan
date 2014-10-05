package com.larry.neihan.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 文本段子实体
 * 
 * @author aaa
 * 
 */
public class TextEntity{

	
	private int type;
	
	private long createTime;
	/**
	 * 上线时间
	 */
	private long onlineTime;
	/**
	 * 显示时间
	 */
	private long displayTime;
	
	/**
	 * 评论的个数
	 */
	private int commentCount;
	
	/**
	 * d
	 */
	private int diggCount;
	
	/**
	 * 评论的状态，其中的可选值3需要分析是什么类型
	 */
	private int status;
	
	/**
	 * TODO 需要去了解digg到底是什么含义
	 */
	private int userDigg;
	
	/**
	 * 段子的Id，访问详情和评论的时候用这个作为接口的参数
	 */
	private long groupId;
	
	/**
	 * 内容分类类型，1表示文本，2表示图片
	 */
	private int categoryId;
	
	/**
	 * 代表踩的个数
	 */
	private int buryCount;
	
	/**
	 * 代表用户是否repin了
	 */
	private int userRepin;
	
	/**
	 * 代表当前用户是否踩了，0代表没有，1代表踩了
	 */
	private int userBury;
	
	/**
	 * TODO 这个需要分析一下是什么含义，现在有两处地方出现 1、获取列表接口里面有一个 level=6 2、文本段子实体中，level=4
	 */
	private int level;
	
	/**
	 * TODO 需要分析这个字段的含义，
	 */
	private int goDetailCount;
	
	/**
	 * 当前用户是否评论了：0代表没有
	 */
	private int hasComments;


	/**
	 * 用于第三方分享，提交的网址参数
	 */
	private String shareUrl;
	
	/**
	 * 赞的个数
	 */
	private int favoriteCount;

	/**
	 * 代表当前用户是否赞了，0代表没有
	 */
	private int userFavorite;

	// TODO 分析这个字段的含义
	private int label;
	/**
	 * 文本段子的内容部分（完整的内容）
	 */
	private String content;
	
	
	/**
	 * 状态的描述信息<br/>
	 * 可选值<br/>
	 * <ul>
	 * <li>"已发表到热门列表"</li>
	 * </ul>
	 */
	private String statusDesc;

	/**
	 * TODO 分析含义
	 */
	private int repinCount;

	/**
	 * 是否含有热门评论
	 */
	private int hasHotComments;


	/**
	 * TODO 需要去分析 comments 这个Json数组中的内容是什么？
	 */

	/**
	 * 上线时间
	 */

	

	private UserEntity user;

	public void parseJson(JSONObject json) throws JSONException {
		if (json != null) {
			this.onlineTime = json.getLong("online_time");
			this.displayTime = json.getLong("display_time");
			JSONObject group = json.getJSONObject("group");

			this.createTime = group.getLong("create_time");
			this.favoriteCount = group.getInt("favorite_count");
			this.userBury = group.getInt("user_bury");
			this.userFavorite = group.getInt("user_favorite");
			this.buryCount = group.getInt("bury_count");
			this.shareUrl = group.getString("share_url");
			this.label = group.optInt("label",0);
			this.content = group.getString("content");
			this.commentCount = group.getInt("comment_count");
			this.status = group.getInt("status");
			this.hasComments = group.getInt("has_comments");
			this.goDetailCount = group.getInt("go_detail_count");
			this.statusDesc = group.getString("status_desc");

			JSONObject uobj = group.getJSONObject("user");
			user = new UserEntity();
			user.parseJson(uobj);

			this.userDigg = group.getInt("user_digg");
			this.groupId = group.getLong("group_id");
			this.level = group.getInt("level");
			this.repinCount = group.getInt("repin_count");
			this.diggCount = group.getInt("digg_count");
			this.hasHotComments = group.optInt("has_hot_comments",0);
			this.userRepin = group.getInt("user_repin");
			this.categoryId = group.getInt("category_id");
			
			
			this.type = json.getInt("type");
			
		}
	}

	public int getType() {
		return type;
	}

	public long getCreateTime() {
		return createTime;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public int getUserBury() {
		return userBury;
	}

	public int getUserFavorite() {
		return userFavorite;
	}

	public int getBuryCount() {
		return buryCount;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public int getLabel() {
		return label;
	}

	public String getContent() {
		return content;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public int getHasComments() {
		return hasComments;
	}

	public int getGoDetailCount() {
		return goDetailCount;
	}

	public int getUserDigg() {
		return userDigg;
	}

	public int getDiggCount() {
		return diggCount;
	}

	public long getGroupId() {
		return groupId;
	}

	public int getLevel() {
		return level;
	}

	public int getRepinCount() {
		return repinCount;
	}

	public int getUserRepin() {
		return userRepin;
	}

	public int getHasHotComments() {
		return hasHotComments;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public long getOnlineTime() {
		return onlineTime;
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public UserEntity getUser() {
		return user;
	}

}
