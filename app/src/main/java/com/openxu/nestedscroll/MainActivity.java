package com.openxu.nestedscroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.openxu.nestedscroll.adapter.CommonAdapter;
import com.openxu.nestedscroll.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	public void simple1(View v){
		startActivity(new Intent(this, SimpleActivity1.class));
	}
	public void simple2(View v){
		startActivity(new Intent(this, SimpleActivity2.class));
	}
	public void simple3(View v){
		startActivity(new Intent(this, SimpleActivity3.class));
	}

}
