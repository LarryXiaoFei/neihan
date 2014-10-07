package com.larry.neihan.activities;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.larry.neihan.R;
import com.larry.neihan.adapters.DetailPagerAdapter;
import com.larry.neihan.bean.DataStore;
import com.larry.neihan.bean.TextEntity;

public class EssayDetailActivity extends FragmentActivity {

	private ViewPager pager;
	private DetailPagerAdapter adapter;
	private FragmentManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_essay_detail);

		pager = (ViewPager) findViewById(R.id.detail_pager_content);
		manager = getSupportFragmentManager();

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		int category = 1;

		int currentEssayPosition = 0;

		if (extras != null) {
			category = extras.getInt("category", 1);
			currentEssayPosition = extras.getInt("currentEssayPosition", 0);
		}

		DataStore dataStore = DataStore.getInstance();
		List<TextEntity> entities = null;
		if (category == 1) {
			entities = dataStore.getTextEntities();
		} else if (category == 2) {
			entities = dataStore.getImageEntities();
		}

		adapter = new DetailPagerAdapter(manager, entities);
		// 设置FragmentPagerAdapter
		pager.setAdapter(adapter);

		if (currentEssayPosition > 0) {
			pager.setCurrentItem(currentEssayPosition);
		}
		
		
	}
}
