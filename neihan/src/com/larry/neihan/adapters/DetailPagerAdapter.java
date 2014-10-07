package com.larry.neihan.adapters;

import java.util.List;

import com.larry.neihan.bean.TextEntity;
import com.larry.neihan.fragments.DetailContentFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DetailPagerAdapter extends FragmentPagerAdapter {
	
	
	private List<TextEntity> entities;
	
	
	public DetailPagerAdapter(FragmentManager fm,List<TextEntity> entities) {
		super(fm);
		this.entities = entities;
	}

	@Override
	public Fragment getItem(int arg0) {
		DetailContentFragment fragment = new DetailContentFragment();
		Bundle arguments = new Bundle();
		TextEntity value = entities.get(arg0);
		
		arguments.putSerializable("entity",value);
		
		fragment.setArguments(arguments);
		return fragment;
	}

	@Override
	public int getCount() {
		return entities.size();
	}
}
