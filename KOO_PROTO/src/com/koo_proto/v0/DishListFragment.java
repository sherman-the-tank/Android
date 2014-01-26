package com.koo_proto.v0;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
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
	public static final String INTENT = "intent";
	public static final String TAG = "DishListFragment";
	
	private static final String DIALOG_CONFIRMATION = "confirmation";
	private static final int REQUEST_CONFIRMATION = 1;
	
	private static final String DIALOG_THANKYOU = "thank_you";
	private static final int REQUEST_THANKYOU = 2;
	
	private ArrayList<Dish> mDishes;
	private Restaurant mRestaurant;
	private String mIntent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.menu_list);
		Bundle extras = getActivity().getIntent().getExtras();
		UUID restaurantID = (UUID)extras.get(RESTAURANT_ID);
		mRestaurant = RestaurantStore.get(getActivity()).getRestaurant(restaurantID);
		mDishes = mRestaurant.getDishes();
		
		mIntent = extras.getString(INTENT);
		
		DishAdapter adapter = 
				new DishAdapter(mDishes);
		setListAdapter(adapter);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		if (mIntent.equals(UserIntent.ORDER_RESTAURANT)) {
			inflater.inflate(R.menu.fragment_dish_list, menu);
		}
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
			priceTextView.setText(d.getPriceString());
			CheckBox orderCheckView = (CheckBox)convertView.findViewById(R.id.dish_list_item_orderCheckBox);
			orderCheckView.setChecked(mRestaurant.orderContainsDish(d.getId()));
			orderCheckView.setFocusable(false);
			orderCheckView.setClickable(false);
			return convertView;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_dish_list_order:
				FragmentManager fm = getActivity().getSupportFragmentManager();
				ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstanceForOrderDishes(mRestaurant.getId());
				dialog.setTargetFragment(DishListFragment.this, REQUEST_CONFIRMATION);
				dialog.show(fm, DIALOG_CONFIRMATION);
				return true;
			case android.R.id.home:
				if (NavUtils.getParentActivityName(getActivity()) != null) {
					NavUtils.navigateUpFromSameTask(getActivity());
				}
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		
		if (requestCode == REQUEST_CONFIRMATION) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			ThankYouDialogFragment dialog = ThankYouDialogFragment.newInstance(mIntent);
			dialog.setTargetFragment(DishListFragment.this, REQUEST_THANKYOU);
			dialog.show(fm, DIALOG_THANKYOU);
		} else if (requestCode == REQUEST_THANKYOU) {
			Intent mainActivityIntent = new Intent(getActivity(), MainActivity.class);
			mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(mainActivityIntent);
		}
	}
}
