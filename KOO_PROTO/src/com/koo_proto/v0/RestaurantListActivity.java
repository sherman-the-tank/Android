package com.koo_proto.v0;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class RestaurantListActivity extends SingleFragmentActivity {

	protected Fragment createFragment() {
		return new RestaurantListFragment();
	}
}
