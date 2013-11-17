package com.koo_proto.v0;

import android.support.v4.app.Fragment;

public class DishListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new DishListFragment();
	}

}
