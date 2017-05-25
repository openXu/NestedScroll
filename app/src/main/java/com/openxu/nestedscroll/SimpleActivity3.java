package com.openxu.nestedscroll;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.openxu.nestedscroll.adapter.CommonAdapter;
import com.openxu.nestedscroll.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class SimpleActivity3 extends Activity {

	private ViewPager viewpager;

	private SwipeRefreshLayout refresh_layout;
	private RecyclerView id_recycler;
	private List<String> mDatas = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple3);

		initViews();
		initDatas();
		initEvents();
	}
	private void initViews(){
		refresh_layout = (SwipeRefreshLayout) findViewById(R.id.id_refresh_layout);
		id_recycler = (RecyclerView) findViewById(R.id.id_recycler);
		id_recycler.setLayoutManager(new LinearLayoutManager(this));

		viewpager = (ViewPager) findViewById(R.id.viewpager);
		viewpager.setAdapter(new MyPagerAdapter(this));
	}

	private void initEvents(){
		refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				//刷新
				initDatas();
			}
		});
	}

	private void initDatas(){
		mDatas.clear();
		for (int i = 0; i < 60; i++){
			mDatas.add("item -> " + i);
		}
		refresh_layout.setRefreshing(false);
		id_recycler.setAdapter(new CommonAdapter<String>(this, R.layout.item, mDatas){
			@Override
			public void convert(ViewHolder holder, String o){
				holder.setText(R.id.id_info, o);
			}
		});
	}




	class MyPagerAdapter extends PagerAdapter {
		private List<ImageView> list;
		public MyPagerAdapter(Context context){
			list = new ArrayList<>();
			for(int i =0;i<3;i++){
				ImageView iv = new ImageView(context);
				iv.setImageResource(R.mipmap.ic_launcher);
				list.add(iv);
			}
		}
		@Override
		public int getCount(){
			return list.size();
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));
		}
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}




}
