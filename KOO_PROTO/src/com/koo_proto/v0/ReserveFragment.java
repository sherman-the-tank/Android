package com.koo_proto.v0;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

public class ReserveFragment extends Fragment {
	
	public static final String RESTAURANT_ID = "restaurant_id";
	public static final String INTENT = "intent";
	
	private static final String DIALOG_TIME_PICKER = "time_picker";
	private static final int REQUEST_TIME = 0;
	
	private static final String DIALOG_DATE_PICKER = "date_picker";
	private static final int REQUEST_DATE = 1;
	
	private static final String DIALOG_CONFIRMATION = "confirmation";
	private static final int REQUEST_CONFIRMATION = 2;
	
	private static final String DIALOG_THANKYOU = "thank_you";
	private static final int REQUEST_THANKYOU = 3;

	
	private NumberPicker mNumberPicker;
	private int mPartySize;
	private Button mTimeButton;
	private Date mTime;
	private SimpleDateFormat mSimpleTimeFormat;
	
	private Button mDateButton;
	private Date mDate;
	private SimpleDateFormat mSimpleDateFormat;
	
	private Restaurant mRestaurant;
    private String mIntent;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		Bundle extras = getActivity().getIntent().getExtras();
		UUID restaurantID = (UUID)extras.get(RESTAURANT_ID);
		mRestaurant = RestaurantStore.get(getActivity()).getRestaurant(restaurantID);
		mIntent = extras.getString(INTENT);
		getActivity().setTitle(R.string.reserve_table);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_reserve_restaurant, parent, false);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		mNumberPicker = (NumberPicker)v.findViewById(R.id.reserve_number_people_picker);
		mNumberPicker.setMaxValue(10);
		mNumberPicker.setMinValue(1);
		
		mTimeButton = (Button)v.findViewById(R.id.reserve_time_button);
		Calendar cal = Calendar.getInstance();
		mTime = cal.getTime();
		mSimpleTimeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
		mTimeButton.setText(mSimpleTimeFormat.format(mTime));
		mTimeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				TimePickerDialogFragment dialog = TimePickerDialogFragment.newInstance(mTime);
				dialog.setTargetFragment(ReserveFragment.this, REQUEST_TIME);
				dialog.show(fm, DIALOG_TIME_PICKER);
			}
		});
		
		mDateButton = (Button)v.findViewById(R.id.reserve_date_button);
		mDate = cal.getTime();
		mSimpleDateFormat = new SimpleDateFormat("MMM d, EEE", Locale.US);
		mDateButton.setText(mSimpleDateFormat.format(mDate));
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerDialogFragment dialog = DatePickerDialogFragment.newInstance(mTime);
				dialog.setTargetFragment(ReserveFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE_PICKER);
			}
		});
		
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == REQUEST_TIME) {
			mTime = (Date)intent.getSerializableExtra(TimePickerDialogFragment.EXTRA_TIME);
			mTimeButton.setText(mSimpleTimeFormat.format(mTime));
		} else if (requestCode == REQUEST_DATE) {
			mDate = (Date)intent.getSerializableExtra(DatePickerDialogFragment.EXTRA_DATE);
			mDateButton.setText(mSimpleDateFormat.format(mDate));
		} else if (requestCode == REQUEST_CONFIRMATION) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			ThankYouDialogFragment dialog = ThankYouDialogFragment.newInstance(mIntent);
			dialog.setTargetFragment(ReserveFragment.this, REQUEST_THANKYOU);
			dialog.show(fm, DIALOG_THANKYOU);
		} else if (requestCode == REQUEST_THANKYOU) {
			Intent mainActivityIntent = new Intent(getActivity(), MainActivity.class);
			mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(mainActivityIntent);
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_restaurant_reserve_list, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_restaurant_list_reserve:
				mPartySize = mNumberPicker.getValue();
				FragmentManager fm = getActivity().getSupportFragmentManager();
				Calendar reserveDate = Calendar.getInstance();
				reserveDate.setTime(mDate);
				Calendar reserveTime = Calendar.getInstance();
				reserveTime.setTime(mTime);
				Date reserveDateTime = new GregorianCalendar(
						reserveDate.get(Calendar.YEAR), 
						reserveDate.get(Calendar.MONTH), 
						reserveDate.get(Calendar.DAY_OF_MONTH),
						reserveTime.get(Calendar.HOUR),
						reserveTime.get(Calendar.MINUTE),
						reserveTime.get(Calendar.SECOND)
				).getTime();
				
				ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstanceForReserveRestauarnt(mRestaurant.getId(), reserveDateTime, mPartySize);
				dialog.setTargetFragment(ReserveFragment.this, REQUEST_CONFIRMATION);
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
}
