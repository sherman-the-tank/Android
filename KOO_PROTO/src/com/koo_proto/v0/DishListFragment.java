package com.koo_proto.v0;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class DishListFragment extends ListFragment {

	public static final String RESTAURANT_ID = "restaurant_id";
	public static final String TAG = "DishListFragment";
	
	private ArrayList<Dish> mDishes;
	private Restaurant mRestaurant;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.menu_list);
		Bundle extras = getActivity().getIntent().getExtras();
		UUID restaurantID = (UUID)extras.get(RESTAURANT_ID);
		mRestaurant = RestaurantStore.get(getActivity()).getRestaurant(restaurantID);
		mDishes = mRestaurant.getDishes();
		
		DishAdapter adapter = 
				new DishAdapter(mDishes);
		setListAdapter(adapter);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_dish_list, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_dish_list_order:
			Log.d(TAG, "order");
			return true;
		}
		return false;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		DishAdapter dishAdapter = (DishAdapter)getListAdapter();
		Dish d = dishAdapter.getItem(position);
		//Log.d(TAG, d.getName() + " was clicked");
		if (!mRestaurant.orderContainsDish(d.getId())) {
			mRestaurant.addDishToOrder(d.getId());
		} else {
			mRestaurant.removeDishFromOrder(d.getId());
		}
		dishAdapter.notifyDataSetChanged();
	}
	
	private class DishAdapter extends ArrayAdapter<Dish> {
		public DishAdapter(ArrayList<Dish> dishes) {
			super(getActivity(), 0, dishes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_dish, null);
			}
			Dish d = getItem(position);
			
			TextView nameTextView = (TextView)convertView.findViewById(R.id.dish_list_item_title);
			nameTextView.setText(d.getName());
			TextView priceTextView = (TextView)convertView.findViewById(R.id.dish_list_item_price);
			priceTextView.setText(d.getPrice());
			CheckBox orderCheckView = (CheckBox)convertView.findViewById(R.id.dish_list_item_orderCheckBox);
			orderCheckView.setChecked(mRestaurant.orderContainsDish(d.getId()));
			orderCheckView.setFocusable(false);
			orderCheckView.setClickable(false);
			return convertView;
		}
	}
}
