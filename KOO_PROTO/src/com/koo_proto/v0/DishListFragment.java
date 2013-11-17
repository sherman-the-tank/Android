package com.koo_proto.v0;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class DishListFragment extends ListFragment {

	public static final String RESTAURANT_ID = "restaurant_id";
	
	private ArrayList<Dish> mDishes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.menu_list);
		Bundle extras = getActivity().getIntent().getExtras();
		UUID r_id = (UUID)extras.get(RESTAURANT_ID);
		mDishes = RestaurantStore.get(getActivity()).getRestaurant(r_id).getDishes();
		
		ArrayAdapter<Dish> adapter = 
				new ArrayAdapter<Dish>(getActivity(), android.R.layout.simple_list_item_1, mDishes);
		setListAdapter(adapter);
	}
}
