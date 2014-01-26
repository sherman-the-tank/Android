package com.koo_proto.v0;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerDialogFragment extends DialogFragment {
	public static final String EXTRA_DATE = "com.koo_proto.v0.DatePIckerFragment.date";
	private Date mDate;
	
	public static DatePickerDialogFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		
		DatePickerDialogFragment fragment = new DatePickerDialogFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null) {
			return;
		}
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);

		DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_picker);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year, int month, int day) {
				mDate = new GregorianCalendar(year, month, day).getTime();
				getArguments().putSerializable(EXTRA_DATE, mDate);
			}
		});
		if (datePicker.getMinDate() < mDate.getTime()) {
			datePicker.setMinDate(mDate.getTime());
		}
		// Can not book on date which is 2 days from now.
		// datePicker.setMaxDate(new Date(mDate.getTime() + 2 * 24 * 3600 * 1000).getTime());
			
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.select_date_title)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendResult(Activity.RESULT_OK);
				}
			})
			.create();
	}
}
