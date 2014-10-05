package com.larry.neihan.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class UserEntity {
	/**
	 * 头像网址
	 */
	private String avatarUrl;
	/**
	 * 用户Id
	 */
	private long userId;
	/**
	 * 用户名称（昵称）
	 */
	private String name;
	/**
	 * 用户是否验证，用户是否加"V"了。
	 */
	private boolean userVerified;

	public void parseJson(JSONObject json) throws JSONException {
		if (json != null) {
			this.avatarUrl = json.getString("avatar_url");
			this.userId = json.getLong("user_id");
			this.name = json.getString("name");
			this.userVerified = json.getBoolean("user_verified");
		}
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public boolean isUserVerified() {
		return userVerified;
	}

}
