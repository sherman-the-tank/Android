package com.koo_proto.v0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainFragment extends Fragment {

	private Button mOrderFoodButton;
	private Button mBookRestaurantButton;
	private Button mHaircutButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main, parent, false);
		
		mOrderFoodButton = (Button)v.findViewById(R.id.order_food_button);
		mBookRestaurantButton = (Button)v.findViewById(R.id.book_restaurant_button);
		mHaircutButton = (Button)v.findViewById(R.id.haircut_button);
		
		mOrderFoodButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), RestaurantListActivity.class);
				i.putExtra(RestaurantListFragment.IS_ORDER, true);
				startActivity(i);
			}
		});
		
		mBookRestaurantButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), RestaurantListActivity.class);
				i.putExtra(RestaurantListFragment.IS_ORDER, false);
				startActivity(i);
			}
		});
		
		mHaircutButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), R.string.coming_soon, Toast.LENGTH_SHORT).show();
			}
		});
		
		return v;
	}
}
