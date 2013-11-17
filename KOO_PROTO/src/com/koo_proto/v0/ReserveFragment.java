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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

public class ReserveFragment extends Fragment {
	
	private static final String DIALOG_TIME_PICKER = "time_picker";
	private static final int REQUEST_TIME = 0;
	
	private NumberPicker mNumberPicker;
	private Button mTimeButton;
	private Date mTime;
	private SimpleDateFormat mSimpleDateFormat;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_reserve_restaurant, parent, false);
		
		mNumberPicker = (NumberPicker)v.findViewById(R.id.reserve_number_people_picker);
		mNumberPicker.setMaxValue(10);
		mNumberPicker.setMinValue(1);
		
		mTimeButton = (Button)v.findViewById(R.id.reserve_time_button);
		Calendar cal = Calendar.getInstance();
		mTime = cal.getTime();
		mSimpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
		mTimeButton.setText(mSimpleDateFormat.format(mTime));
		mTimeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				TimePickerFragment dialog = TimePickerFragment.newInstance(mTime);
				dialog.setTargetFragment(ReserveFragment.this, REQUEST_TIME);
				dialog.show(fm, DIALOG_TIME_PICKER);
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
			mTimeButton.setText(mSimpleDateFormat.format(mTime));
		}
	}
}
