package com.larry.neihan;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.larry.neihan.fragments.HuodongFragment;
import com.larry.neihan.fragments.ImageListFragment;
import com.larry.neihan.fragments.MineFragment;
import com.larry.neihan.fragments.ReviewFragment;
import com.larry.neihan.fragments.TextListFragment;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener {

	private List<Fragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RadioGroup group = (RadioGroup) findViewById(R.id.main_tab_bar);
		group.setOnCheckedChangeListener(this);
		
		fragments = new LinkedList<Fragment>();
		fragments.add(new TextListFragment());
		fragments.add(new ImageListFragment());
		fragments.add(new ReviewFragment());
		fragments.add(new HuodongFragment());
		fragments.add(new MineFragment());
		
		Fragment fragment = fragments.get(0);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.main_fragment_container, fragment);
		transaction.commit();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 循环遍历子元素
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		int childCount = group.getChildCount();
		int checkedIndex = 0;
		RadioButton btn = null;
		for (int i = 0; i < childCount; i++) {
			btn = (RadioButton) group.getChildAt(i);
			if (btn.isChecked()) {
				checkedIndex = i;
				break;
			}
		}
		
		Fragment fragment = fragments.get(checkedIndex);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.main_fragment_container, fragment);
		transaction.commit();
		
		//
		// switch (checkedIndex) {
		// case 0:
		//
		// break;
		// case 1:
		//
		// break;
		// case 2:
		//
		// break;
		// case 3:
		//
		// break;
		// case 4:
		//
		// break;
		//
		// default:
		// break;
		// }
	}
}
