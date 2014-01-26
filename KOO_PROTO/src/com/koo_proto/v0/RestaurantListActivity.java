package com.koo_proto.v0;

import android.support.v4.app.Fragment;

public class RestaurantListActivity extends SingleFragmentActivity {

	protected Fragment createFragment() {
		return new RestaurantListFragment();
	}
}
