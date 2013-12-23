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

public class ThankYouDialogFragment extends DialogFragment {
	public static final String EXTRA_INTENT = "com.koo_proto.v0.thankyoudialog.intent";
	
	private String mIntent;

	public static ThankYouDialogFragment newInstance(String intent) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_INTENT, intent);

		ThankYouDialogFragment fragment = new ThankYouDialogFragment();
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
		confirmationTextView.setText(R.string.thank_you);		
				
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.confirm_title)
			.setPositiveButton(R.string.thank_you_main_menu, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Send request to server
					sendResult(Activity.RESULT_OK);
				}
			})
			.create();
	}
}
