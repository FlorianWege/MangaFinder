package com.example.mangafinder;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> _frags = new ArrayList<Fragment>();
	
	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
		
		Fragment sucheFrag = new SucheFragment();
		Fragment biblioFrag = new BiblioFragment();
		Fragment optionenFrag = new OptionenFragment();
		
		_frags.add(sucheFrag);
		_frags.add(biblioFrag);
		_frags.add(optionenFrag);
	}

	@Override
	public Fragment getItem(int index) {
		Fragment frag = _frags.get(index);
		
		return frag;
	}

	@Override
	public int getCount() {
		return _frags.size();
	}
}
