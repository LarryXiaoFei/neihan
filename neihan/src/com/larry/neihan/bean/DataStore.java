package com.larry.neihan.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * 段子列表数据存储区
 * 
 * @author aaa
 * 
 */

public class DataStore {

	private static DataStore outInstance;

	private List<TextEntity> textEntities;
	private List<TextEntity> imageEntities;

	private DataStore() {
		textEntities = new LinkedList<TextEntity>();

		imageEntities = new LinkedList<TextEntity>();
	}

	public static DataStore getInstance() {
		if (outInstance == null) {
			outInstance = new DataStore();
		}
		return outInstance;
	}

	/**
	 * 把获取到的文本段子列表放到最前面 这个方法针对的是下拉刷新的操作
	 * 
	 * @param entities
	 */
	public void addTextEntities(List<TextEntity> entities) {
		if (entities != null) {
			textEntities.addAll(0, entities);
		}
	}

	/**
	 * 把获取到的段子列表放到最后面 这个方法针对的是上拉查看旧数据的操作
	 * 
	 * @param entities
	 */
	public void appendTextEntities(List<TextEntity> entities) {
		if (entities != null) {
			textEntities.addAll(entities);
		}
	}

	/**
	 * 把获取到的短片段子列表放到最前面 这个方法针对的是下拉刷新的操作
	 * 
	 * @param entities
	 */
	public void addImageEntities(List<TextEntity> entities) {
		if (entities != null) {
			imageEntities.addAll(0, entities);
		}
	}

	/**
	 * 把获取到的图片段子列表放到最后面 这个方法针对的是上拉查看旧数据的操作
	 * 
	 * @param entities
	 */
	public void appendImageEntities(List<TextEntity> entities) {
		if (entities != null) {
			imageEntities.addAll(entities);
		}
	}

	/**
	 * 获取文本段子的列表
	 * 
	 * @return
	 */
	public List<TextEntity> getTextEntities() {
		return textEntities;
	}

	/**
	 * 获取图片段子的列表
	 * 
	 * @return
	 */
	public List<TextEntity> getImageEntities() {
		return imageEntities;
	}

}
