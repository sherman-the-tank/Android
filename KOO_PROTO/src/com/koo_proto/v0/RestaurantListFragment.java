package com.koo_proto.v0;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RestaurantListFragment extends ListFragment {

	public static final String IS_ORDER = "IS_ORDER";
	
	private ArrayList<Restaurant> mRestaurants;
	private boolean mIsOrder;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.nearby_restaurant);
		mRestaurants = RestaurantStore.get(getActivity()).getRestaurants();
		
		mIsOrder = getActivity().getIntent().getExtras().getBoolean(IS_ORDER);
		
		ArrayAdapter<Restaurant> adapter = 
				new ArrayAdapter<Restaurant>(getActivity(), android.R.layout.simple_list_item_1, mRestaurants);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Restaurant r = (Restaurant)getListAdapter().getItem(position);
		
		if (mIsOrder) {
			Intent i = new Intent(getActivity(), DishListActivity.class);
			i.putExtra(DishListFragment.RESTAURANT_ID, r.getId());
			startActivity(i);
		} else {
			Intent i = new Intent(getActivity(), ReserveActivity.class);
			startActivity(i);
		}
	}
}
