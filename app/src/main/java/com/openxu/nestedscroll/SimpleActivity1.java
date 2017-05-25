package com.openxu.nestedscroll;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.openxu.nestedscroll.adapter.CommonAdapter;
import com.openxu.nestedscroll.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class SimpleActivity1 extends FragmentActivity{
	private SwipeRefreshLayout refresh_layout;
	private RecyclerView id_recycler;
	private List<String> mDatas = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple1);

		initViews();
		initDatas();
		initEvents();
	}
	private void initViews(){
		refresh_layout = (SwipeRefreshLayout) findViewById(R.id.id_refresh_layout);
		id_recycler = (RecyclerView) findViewById(R.id.id_recycler);
		id_recycler.setLayoutManager(new LinearLayoutManager(this));
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


}
