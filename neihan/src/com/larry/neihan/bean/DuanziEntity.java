package com.larry.neihan.bean;

public class DuanziEntity {
	
	private int type;
	
	private long createTime;
	
	private long onlineTime;
	
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

	
}
