package com.koo_proto.v0;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

public class ReserveFragment extends Fragment {
	
	private static final String DIALOG_TIME_PICKER = "time_picker";
	private static final int REQUEST_TIME = 0;
	
	private static final String DIALOG_DATE_PICKER = "date_picker";
	private static final int REQUEST_DATE = 1;
	
	private NumberPicker mNumberPicker;
	private Button mTimeButton;
	private Date mTime;
	private SimpleDateFormat mSimpleTimeFormat;
	
	private Button mDateButton;
	private Date mDate;
	private SimpleDateFormat mSimpleDateFormat;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.reserve_table);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_reserve_restaurant, parent, false);
		
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
				TimePickerFragment dialog = TimePickerFragment.newInstance(mTime);
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
				DatePickerFragment dialog = DatePickerFragment.newInstance(mTime);
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
			mTime = (Date)intent.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
			mTimeButton.setText(mSimpleTimeFormat.format(mTime));
		} else if (requestCode == REQUEST_DATE) {
			mDate = (Date)intent.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mDateButton.setText(mSimpleDateFormat.format(mDate));
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_restaurant_reserve_list, menu);
	}
}
