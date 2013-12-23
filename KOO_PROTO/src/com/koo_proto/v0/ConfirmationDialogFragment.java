package com.koo_proto.v0;

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
	private Restaurant mRestaurant;
	private Date mTime;
	private int mPartySize;
	
	public static ConfirmationDialogFragment newInstance(String intent, UUID restaurantID, Date time, int partySize) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_INTENT, intent);
		args.putSerializable(EXTRA_RESTAURANT_ID, restaurantID);
		args.putSerializable(EXTRA_TIME, time);
		args.putInt(EXTRA_PARTY_SIZE, partySize);
		
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
		UUID restaurantID = (UUID)getArguments().getSerializable(EXTRA_RESTAURANT_ID);
		mRestaurant = RestaurantStore.get(getActivity()).getRestaurant(restaurantID);
		mTime = (Date)getArguments().getSerializable(EXTRA_TIME);
		mPartySize = getArguments().getInt(EXTRA_PARTY_SIZE);
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.confirmation, null);
		TextView confirmationTextView = (TextView)v.findViewById(R.id.confirmation_text);
		confirmationTextView.setText(String.format("Reserving resaurant %s for %d at %s", mRestaurant.getName(), mPartySize, mTime.toString()));		
				
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
