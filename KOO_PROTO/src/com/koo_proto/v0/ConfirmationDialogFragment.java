package com.koo_proto.v0;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

public class ConfirmationDialogFragment extends DialogFragment {
	public static final String EXTRA_INTENT = "com.koo_proto.v0.confirmationdialog.intent";
	public static final String EXTRA_RESTAURANT_ID = "com.koo_proto.v0.confirmationdialog.restaurantid";
	public static final String EXTRA_TIME = "com.koo_proto.v0.confirmationdialog.time";
	public static final String EXTRA_PARTY_SIZE = "com.koo_proto.v0.confirmationdialog.partysize";
	
	private String mIntent;
	
	public static ConfirmationDialogFragment newInstanceForReserveRestauarnt(UUID restaurantID, Date time, int partySize) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_INTENT, UserIntent.RESERVE_RESTAURANT);
		args.putSerializable(EXTRA_RESTAURANT_ID, restaurantID);
		args.putSerializable(EXTRA_TIME, time);
		args.putInt(EXTRA_PARTY_SIZE, partySize);
		
		ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	public static ConfirmationDialogFragment newInstanceForOrderDishes(UUID restaurantID) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_INTENT, UserIntent.ORDER_RESTAURANT);
		args.putSerializable(EXTRA_RESTAURANT_ID, restaurantID);
		
		ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null) {
			return;
		}
		Intent i = new Intent();
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mIntent = (String)getArguments().getSerializable(EXTRA_INTENT);
		View v = getActivity().getLayoutInflater().inflate(R.layout.confirmation, null);
		TextView confirmationTextView = (TextView)v.findViewById(R.id.confirmation_text);
		
		
		if (mIntent.equals(UserIntent.RESERVE_RESTAURANT)) {
			
			UUID restaurantID = (UUID)getArguments().getSerializable(EXTRA_RESTAURANT_ID);
			Restaurant restaurant = RestaurantStore.get(getActivity()).getRestaurant(restaurantID);
			Date time = (Date)getArguments().getSerializable(EXTRA_TIME);
			int partySize = getArguments().getInt(EXTRA_PARTY_SIZE);
		
			confirmationTextView.setText(String.format("Reserving resaurant %s for %d at %s", restaurant.getName(), partySize, time.toString()));		
		} else if (mIntent.equals(UserIntent.ORDER_RESTAURANT)) {
			UUID restaurantID = (UUID)getArguments().getSerializable(EXTRA_RESTAURANT_ID);
			Restaurant restaurant = RestaurantStore.get(getActivity()).getRestaurant(restaurantID);
			ArrayList<UUID> orderDishList = restaurant.getOrder().getOrderedDishIDs();
			StringBuilder dishNamesBuilder = new StringBuilder();
			int index = 0;
			float total = 0; 
			for (UUID dishID : orderDishList) {
				Dish dish = DishStore.get(getActivity()).getDish(dishID);
				dishNamesBuilder.append(dish.getName());
				total += dish.getPrice();
				if (index < orderDishList.size() - 2) {
					dishNamesBuilder.append(", ");
				} else {
					dishNamesBuilder.append(" and ");
				}
				index++;
			}
					
			confirmationTextView.setText(String.format("Order %s at restaurant %s. Your total will be $%.2f", dishNamesBuilder.toString(), restaurant.getName(), total));		
		}
 		
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.confirm_title)
			.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Send request to server
					sendResult(Activity.RESULT_OK);
				}
			})
			.setNegativeButton(R.string.confirm_edit, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendResult(Activity.RESULT_CANCELED);
				}
			})
			.create();
	}
}
