package com.example.mangafinder;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	private ActionBar _actionBar;
	private ViewPager _pager;
	private ViewPagerAdapter _pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_actionBar = getActionBar();
		
		_actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab sucheTab = _actionBar.newTab();
		
		sucheTab.setText("Suche");
		
		Tab biblioTab = _actionBar.newTab();
		
		biblioTab.setText("Bibliothek");
		
		Tab optionenTab = _actionBar.newTab();
		
		optionenTab.setText("Optionen");
		
		//_actionBar.addTab();
		_pager = (ViewPager) findViewById(R.id.pager);
		
		if (_pager == null) {
			Log.d("fail", "muh");
			
			return;
		}
		
		_pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		
		_pager.setAdapter(_pagerAdapter);
		
		/*_pager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.d("pager ", Integer.toString(arg0));
			}
		});*/
		
		sucheTab.setTabListener(this);
		biblioTab.setTabListener(this);
		optionenTab.setTabListener(this);
		
		_actionBar.addTab(sucheTab);
		_actionBar.addTab(biblioTab);
		_actionBar.addTab(optionenTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		Log.d("tab selected ", tab.toString());
		
		int index = tab.getPosition();
		
		Log.d("tab ", Integer.toString(index));
		
		Log.d("pager", _pager.toString());
		
		_pager.setCurrentItem(index);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
