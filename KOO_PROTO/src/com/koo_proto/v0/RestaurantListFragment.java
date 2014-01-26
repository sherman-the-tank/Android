package com.koo_proto.v0;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RestaurantListFragment extends ListFragment {

	public static final String TAG = "RestaurantListFragment";
	
	public static final String INTENT = "INTENT";
	
	private ArrayList<Restaurant> mRestaurants;
	private String mIntent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.nearby_restaurant);
		mRestaurants = RestaurantStore.get(getActivity()).getRestaurants();
		
		mIntent = getActivity().getIntent().getExtras().getString(INTENT);
		
		ArrayAdapter<Restaurant> adapter = 
				new ArrayAdapter<Restaurant>(getActivity(), android.R.layout.simple_list_item_1, mRestaurants);
		setListAdapter(adapter);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		return super.onCreateView(inflater, parent, savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Restaurant r = (Restaurant)getListAdapter().getItem(position);
		
		if (mIntent.equals(UserIntent.ORDER_RESTAURANT)) {
			Intent i = new Intent(getActivity(), DishListActivity.class);
			i.putExtra(DishListFragment.RESTAURANT_ID, r.getId());
			i.putExtra(DishListFragment.INTENT, mIntent);
			startActivity(i);
		} else if (mIntent.equals(UserIntent.RESERVE_RESTAURANT)) {
			Intent i = new Intent(getActivity(), ReserveActivity.class);
			i.putExtra(ReserveFragment.RESTAURANT_ID, r.getId());
			i.putExtra(ReserveFragment.INTENT, mIntent);
			startActivity(i);
		} else {
			Log.d(TAG, "Unrecognized Intent:" + mIntent);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (NavUtils.getParentActivityName(getActivity()) != null) {
					NavUtils.navigateUpFromSameTask(getActivity());
				}
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
